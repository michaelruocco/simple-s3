package uk.co.mruoc.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;

import java.io.InputStream;

public class DefaultSimpleS3 implements SimpleS3 {

    private final AmazonS3 s3;
    private final S3ObjectConverter s3ObjectConverter = new S3ObjectConverter();

    public DefaultSimpleS3(AmazonS3 s3) {
        this.s3 = s3;
    }

    @Override
    public String getObjectAsString(ObjectDefinition objectDefinition) {
        return getObjectAsString(objectDefinition.getBucketName(), objectDefinition.getKey());
    }

    @Override
    public String getObjectAsString(String bucketName, String key) {
        S3Object s3object = s3.getObject(bucketName, key);
        return s3ObjectConverter.toContent(s3object);
    }

    @Override
    public InputStream getObjectAsStream(ObjectDefinition objectDefinition) {
        return getObjectAsStream(objectDefinition.getBucketName(), objectDefinition.getKey());
    }

    @Override
    public InputStream getObjectAsStream(String bucketName, String key) {
        S3Object s3object = s3.getObject(bucketName, key);
        return s3object.getObjectContent();
    }

    @Override
    public void putObject(ObjectDefinition objectDefinition, String content) {
        putObject(objectDefinition.getBucketName(), objectDefinition.getKey(), content);
    }

    @Override
    public void putObject(String bucketName, String key, String content) {
        s3.putObject(bucketName, key, content);
    }

    @Override
    public void deleteObject(ObjectDefinition objectDefinition) {
        deleteObject(objectDefinition.getBucketName(), objectDefinition.getKey());
    }

    @Override
    public void deleteObject(String bucketName, String key) {
        s3.deleteObject(bucketName, key);
    }

}
