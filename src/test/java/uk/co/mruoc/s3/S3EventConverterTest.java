package uk.co.mruoc.s3;

import com.amazonaws.services.lambda.runtime.events.S3Event;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class S3EventConverterTest {

    private final FakeS3EventBuilder s3EventBuilder = new FakeS3EventBuilder();
    private final S3Event s3Event = s3EventBuilder.build();

    private final S3EventConverter converter = new S3EventConverter();

    @Test
    public void shouldConvertBucketName() {
        ObjectDefinition objectDefinition = converter.toObjectDefinition(s3Event);

        assertThat(objectDefinition.getBucketName()).isEqualTo("test-bucket");
    }

    @Test
    public void shouldConvertObjectKey() {
        ObjectDefinition objectDefinition = converter.toObjectDefinition(s3Event);

        assertThat(objectDefinition.getKey()).isEqualTo("test-file.txt");
    }

}
