package uk.co.mruoc.s3;

import java.io.InputStream;

public interface SimpleS3 {

    String getObjectAsString(ObjectDefinition objectDefinition);

    String getObjectAsString(String bucketName, String key);

    InputStream getObjectAsStream(ObjectDefinition objectDefinition);

    InputStream getObjectAsStream(String bucketName, String key);

    void putObject(ObjectDefinition objectDefinition, String content);

    void putObject(String bucketName, String key, String content);

    void deleteObject(ObjectDefinition objectDefinition);

    void deleteObject(String bucketName, String key);

}
