package uk.co.mruoc.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.savoirtech.logging.slf4j.json.logger.JsonLogger;
import uk.co.mruoc.log.Logger;

import java.io.InputStream;

public class JsonLogSimpleS3 extends DefaultSimpleS3 {

    private final Logger logger;

    public JsonLogSimpleS3(AmazonS3 s3, Logger logger) {
        super(s3);
        this.logger = logger;
    }

    @Override
    public String getObjectAsString(ObjectDefinition objectDefinition) {
        return getObjectAsString(objectDefinition.getBucketName(), objectDefinition.getKey());
    }

    @Override
    public String getObjectAsString(String bucketName, String key) {
        logReadingContent(bucketName, key);
        String content = super.getObjectAsString(bucketName, key);
        logReadContent(bucketName, key, content);
        return content;
    }

    @Override
    public InputStream getObjectAsStream(ObjectDefinition objectDefinition) {
        return getObjectAsStream(objectDefinition.getBucketName(), objectDefinition.getKey());
    }

    @Override
    public InputStream getObjectAsStream(String bucketName, String key) {
        logReadStream(bucketName, key);
        return super.getObjectAsStream(bucketName, key);
    }

    @Override
    public void putObject(ObjectDefinition objectDefinition, String content) {
        putObject(objectDefinition.getBucketName(), objectDefinition.getKey(), content);
    }

    @Override
    public void putObject(String bucketName, String key, String content) {
        logWritingContent(bucketName, key, content);
        super.putObject(bucketName, key, content);
    }

    @Override
    public void deleteObject(ObjectDefinition objectDefinition) {
        deleteObject(objectDefinition.getBucketName(), objectDefinition.getKey());
    }

    @Override
    public void deleteObject(String bucketName, String key) {
        logDeleteObject(bucketName, key);
        super.deleteObject(bucketName, key);
    }

    private void logReadStream(String bucketName, String key) {
        logInfo(bucketName, key)
                .message("reading stream")
                .log();
    }

    private void logReadingContent(String bucketName, String key) {
        logInfo(bucketName, key)
                .message("reading content")
                .log();
    }

    private void logDeleteObject(String bucketName, String key) {
        logInfo(bucketName, key)
                .message("deleting object")
                .log();
    }

    private void logReadContent(String bucketName, String key, String content) {
        logInfo(bucketName, key)
                .message("read content")
                .field("content", content)
                .log();
    }

    private void logWritingContent(String bucketName, String key, String content) {
        logInfo(bucketName, key)
                .message("writing content")
                .field("content", content)
                .log();
    }

    private JsonLogger logInfo(String bucketName, String key) {
        return logger.info().field("bucketName", bucketName).field("key", key);
    }

}
