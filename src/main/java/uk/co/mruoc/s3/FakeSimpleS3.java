package uk.co.mruoc.s3;

import java.io.InputStream;

public class FakeSimpleS3 implements SimpleS3 {

    private String content;
    private InputStream stream;

    private String writtenBucketName;
    private String writtenObjectKey;
    private String writtenContent;

    private String deletedBucketName;
    private String deletedObjectKey;

    @Override
    public String getObjectAsString(ObjectDefinition definition) {
        return getObjectAsString(definition.getBucketName(), definition.getKey());
    }

    @Override
    public String getObjectAsString(String bucketName, String objectKey) {
        return content;
    }

    @Override
    public InputStream getObjectAsStream(ObjectDefinition definition) {
        return stream;
    }

    @Override
    public InputStream getObjectAsStream(String bucketName, String key) {
        return stream;
    }

    @Override
    public void putObject(ObjectDefinition definition, String content) {
        putObject(definition.getBucketName(), definition.getKey(), content);
    }

    @Override
    public void putObject(String bucketName, String objectKey, String content) {
        this.writtenBucketName = bucketName;
        this.writtenObjectKey = objectKey;
        this.writtenContent = content;
    }

    @Override
    public void deleteObject(ObjectDefinition definition) {
        deleteObject(definition.getBucketName(), definition.getKey());
    }

    @Override
    public void deleteObject(String bucketName, String objectKey) {
        this.deletedBucketName = bucketName;
        this.deletedObjectKey = objectKey;
    }

    public void setObjectAsString(String content) {
        this.content = content;
    }

    public void setObjectAsStream(InputStream stream) {
        this.stream = stream;
    }

    public ObjectDefinition getLastWrittenObjectDefinition() {
        return new ObjectDefinition(writtenBucketName, writtenObjectKey);
    }

    public String getLastWrittenBucketName() {
        return writtenBucketName;
    }

    public String getLastWrittenObjectKey() {
        return writtenObjectKey;
    }

    public String getLastWrittenContent() {
        return writtenContent;
    }

    public ObjectDefinition getLastDeletedObjectDefinition() {
        return new ObjectDefinition(deletedBucketName, deletedObjectKey);
    }

    public String getLastDeletedBucketName() {
        return deletedBucketName;
    }

    public String getLastDeletedObjectKey() {
        return deletedObjectKey;
    }

}
