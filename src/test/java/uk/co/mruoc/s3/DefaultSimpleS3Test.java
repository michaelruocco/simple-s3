package uk.co.mruoc.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class DefaultSimpleS3Test {

    private static final String NEW_LINE = System.lineSeparator();
    private static final String BUCKET_NAME = "bucket-name";
    private static final String OBJECT_KEY = "object-key";
    private static final String CONTENT = "Fake content line 1" + NEW_LINE + "Fake content line 2";

    private final ObjectDefinition objectDefinition = new ObjectDefinition(BUCKET_NAME, OBJECT_KEY);
    private final AmazonS3 s3 = mock(AmazonS3.class);
    private final S3Object s3Object = mock(S3Object.class);
    private final FakeS3ObjectInputStreamBuilder inputStreamBuilder = new FakeS3ObjectInputStreamBuilder();

    private final SimpleS3 simpleS3 = new DefaultSimpleS3(s3);

    @Before
    public void setUp() throws IOException {
        given(s3.getObject(BUCKET_NAME, OBJECT_KEY)).willReturn(s3Object);
    }

    @Test
    public void shouldReturnContentAsString() {
        given(s3Object.getObjectContent()).willReturn(inputStreamBuilder.build(CONTENT));

        String actualContent = simpleS3.getObjectAsString(objectDefinition);

        assertThat(actualContent).isEqualTo(CONTENT);
    }

    @Test
    public void shouldReturnContentAsStream() {
        S3ObjectInputStream expectedStream = inputStreamBuilder.build(CONTENT);
        given(s3Object.getObjectContent()).willReturn(expectedStream);

        InputStream actualStream = simpleS3.getObjectAsStream(objectDefinition);

        assertThat(actualStream).isEqualTo(expectedStream);
    }

    @Test(expected = SimpleS3Exception.class)
    public void shouldThrowFileSystemExceptionIfStreamIsInvalid() {
        S3ObjectInputStream inputStream = mock(S3ObjectInputStream.class);
        given(s3Object.getObjectContent()).willReturn(inputStream);

        simpleS3.getObjectAsString(objectDefinition);
    }

    @Test
    public void shouldWriteContentToBucket() {
        String content = "this is some content";

        simpleS3.putObject(objectDefinition, content);

        verify(s3).putObject(BUCKET_NAME, OBJECT_KEY, content);
    }

    @Test
    public void shouldDeleteFile() {
        simpleS3.deleteObject(objectDefinition);

        verify(s3).deleteObject(BUCKET_NAME, OBJECT_KEY);
    }

}
