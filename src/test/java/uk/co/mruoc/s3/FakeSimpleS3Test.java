package uk.co.mruoc.s3;

import com.amazonaws.util.StringInputStream;
import org.junit.Test;

import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class FakeSimpleS3Test {

    private static final String BUCKET_NAME = "any-bucket";
    private static final String OBJECT_KEY = "any-key";
    private static final String CONTENT = "content";

    private final ObjectDefinition objectDefinition = new ObjectDefinition(BUCKET_NAME, OBJECT_KEY);

    private final FakeSimpleS3 s3 = new FakeSimpleS3();

    @Test
    public void shouldReturnObjectAsStringForAnyBucketAndKey() {
        s3.setObjectAsString(CONTENT);

        assertThat(s3.getObjectAsString(BUCKET_NAME, OBJECT_KEY)).isEqualTo(CONTENT);
    }

    @Test
    public void shouldReturnObjectAsStringForAnyBucketAndKeyUsingDefinition() {
        s3.setObjectAsString(CONTENT);

        assertThat(s3.getObjectAsString(objectDefinition)).isEqualTo(CONTENT);
    }

    @Test
    public void shouldReturnObjectAsStreamForAnyBucketAndKey() throws Throwable {
        InputStream stream = new StringInputStream(CONTENT);

        s3.setObjectAsStream(stream);

        assertThat(s3.getObjectAsStream(BUCKET_NAME, OBJECT_KEY)).isEqualTo(stream);
    }

    @Test
    public void shouldReturnObjectAsStreamForAnyBucketAndKeyUsingDefinition() throws Throwable {
        InputStream stream = new StringInputStream(CONTENT);

        s3.setObjectAsStream(stream);

        assertThat(s3.getObjectAsStream(objectDefinition)).isEqualTo(stream);
    }

    @Test
    public void shouldReturnLastWrittenContentForPut() {
        s3.putObject(BUCKET_NAME, OBJECT_KEY, CONTENT);

        assertThat(s3.getLastWrittenContent()).isEqualTo(CONTENT);
    }

    @Test
    public void shouldReturnLastWrittenObjectDefinitionForPut() {
        s3.putObject(BUCKET_NAME, OBJECT_KEY, CONTENT);

        assertThat(s3.getLastWrittenObjectDefinition()).isEqualToComparingFieldByField(objectDefinition);
    }

    @Test
    public void shouldReturnLastWrittenBucketNameForPut() {
        s3.putObject(BUCKET_NAME, OBJECT_KEY, CONTENT);

        assertThat(s3.getLastWrittenBucketName()).isEqualTo(BUCKET_NAME);
    }

    @Test
    public void shouldReturnLastWrittenObjectKeyForPut() {
        s3.putObject(BUCKET_NAME, OBJECT_KEY, CONTENT);

        assertThat(s3.getLastWrittenObjectKey()).isEqualTo(OBJECT_KEY);
    }

    @Test
    public void shouldReturnLastWrittenContentForPutUsingDefinition() {
        s3.putObject(objectDefinition, CONTENT);

        assertThat(s3.getLastWrittenContent()).isEqualTo(CONTENT);
    }

    @Test
    public void shouldReturnLastWrittenObjectDefinitionForPutUsingDefinition() {
        s3.putObject(objectDefinition, CONTENT);

        assertThat(s3.getLastWrittenObjectDefinition()).isEqualToComparingFieldByField(objectDefinition);
    }

    @Test
    public void shouldReturnLastWrittenBucketNameForPutUsingDefinition() {
        s3.putObject(objectDefinition, CONTENT);

        assertThat(s3.getLastWrittenBucketName()).isEqualTo(BUCKET_NAME);
    }

    @Test
    public void shouldReturnLastWrittenObjectKeyForPutUsingDefinition() {
        s3.putObject(objectDefinition, CONTENT);

        assertThat(s3.getLastWrittenObjectKey()).isEqualTo(OBJECT_KEY);
    }

    @Test
    public void shouldReturnLastDeletedDefinitionForDelete() {
        s3.deleteObject(BUCKET_NAME, OBJECT_KEY);

        assertThat(s3.getLastDeletedObjectDefinition()).isEqualToComparingFieldByField(objectDefinition);
    }

    @Test
    public void shouldReturnLastDeletedBucketNameForDelete() {
        s3.deleteObject(BUCKET_NAME, OBJECT_KEY);

        assertThat(s3.getLastDeletedBucketName()).isEqualTo(BUCKET_NAME);
    }

    @Test
    public void shouldReturnLastDeletedObjectKeyForDelete() {
        s3.deleteObject(BUCKET_NAME, OBJECT_KEY);

        assertThat(s3.getLastDeletedObjectKey()).isEqualTo(OBJECT_KEY);
    }

    @Test
    public void shouldReturnLastDeletedDefinitionForDeleteUsingDefinition() {
        s3.deleteObject(objectDefinition);

        assertThat(s3.getLastDeletedObjectDefinition()).isEqualToComparingFieldByField(objectDefinition);
    }

    @Test
    public void shouldReturnLastDeletedBucketNameForDeleteUsingDefinition() {
        s3.deleteObject(objectDefinition);

        assertThat(s3.getLastDeletedBucketName()).isEqualTo(BUCKET_NAME);
    }

    @Test
    public void shouldReturnLastDeletedObjectKeyForDeleteUsingDefinition() {
        s3.deleteObject(objectDefinition);

        assertThat(s3.getLastDeletedObjectKey()).isEqualTo(OBJECT_KEY);
    }

}
