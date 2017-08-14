package uk.co.mruoc.s3;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ObjectDefinitionTest {

    private static final String BUCKET_NAME = "bucket-name";
    private static final String OBJECT_KEY = "object-key";

    private final ObjectDefinition objectDefinition = new ObjectDefinition(BUCKET_NAME, OBJECT_KEY);

    @Test
    public void shouldReturnBucketName() {
        assertThat(objectDefinition.getBucketName()).isEqualTo(BUCKET_NAME);
    }

    @Test
    public void shouldReturnObjectKey() {
        assertThat(objectDefinition.getKey()).isEqualTo(OBJECT_KEY);
    }

}
