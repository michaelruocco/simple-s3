package uk.co.mruoc.s3;

public class ObjectDefinition {

    private final String bucketName;
    private final String key;

    public ObjectDefinition(String bucketName, String key) {
        this.bucketName = bucketName;
        this.key = key;
    }

    public String getBucketName() {
        return bucketName;
    }

    public String getKey() {
        return key;
    }

}
