package uk.co.mruoc.s3;

import com.amazonaws.services.s3.model.AmazonS3Exception;
import org.junit.Test;
import uk.co.mruoc.retry.DefaultRetryConfig.DefaultRetryConfigBuilder;
import uk.co.mruoc.retry.RetryConfig;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class BucketPollerTest {

    private final ObjectDefinition definition = new ObjectDefinition("my-bucket", "my-key");
    private final SimpleS3 simpleS3 = mock(SimpleS3.class);
    private final RetryConfig retryConfig = new DefaultRetryConfigBuilder().setDelay(50).build();
    private final BucketPoller bucketPoller = new BucketPoller(simpleS3, retryConfig);

    @Test(expected = AmazonS3Exception.class)
    public void shouldThrowAmazonS3ExceptionIfKeyNotFoundException() {
        given(simpleS3.getObjectAsString(definition)).willThrow(s3Exception(""));

        bucketPoller.pollBucketForContent(definition);
    }

    @Test(expected = ObjectNotFoundException.class)
    public void shouldThrowObjectNotFoundExceptionIfMaxRetriesExceeded() {
        given(simpleS3.getObjectAsString(definition)).willThrow(s3Exception("NoSuchKey"));

        bucketPoller.pollBucketForContent(definition);

        verify(simpleS3.getObjectAsString(definition), times(5));
    }

    @Test
    public void shouldReturnBucketContent() {
        String content = "content";
        given(simpleS3.getObjectAsString(definition)).willReturn(content);

        String result = bucketPoller.pollBucketForContent(definition);

        assertThat(result).isEqualTo(content);
    }

    private Exception s3Exception(String errorCode) {
        AmazonS3Exception exception = new AmazonS3Exception("error");
        exception.setErrorCode(errorCode);
        return exception;
    }

}
