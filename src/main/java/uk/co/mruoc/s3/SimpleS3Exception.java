package uk.co.mruoc.s3;

public class SimpleS3Exception extends RuntimeException {

    public SimpleS3Exception(Throwable cause) {
        super(cause);
    }

    public SimpleS3Exception(String message) {
        super(message);
    }

}
