package uk.co.mruoc.s3;

public class ObjectNotFoundException extends SimpleS3Exception {

    public ObjectNotFoundException(ObjectDefinition definition) {
        super("object " + definition.getKey() + " not found in bucket " + definition.getBucketName());
    }

}
