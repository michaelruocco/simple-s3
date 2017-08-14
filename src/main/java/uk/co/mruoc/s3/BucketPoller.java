package uk.co.mruoc.s3;

import com.amazonaws.services.s3.model.AmazonS3Exception;
import uk.co.mruoc.retry.DefaultRetryConfig.DefaultRetryConfigBuilder;
import uk.co.mruoc.retry.RetryConfig;
import uk.co.mruoc.retry.Sleeper;

public class BucketPoller {

    private final Sleeper sleeper = new Sleeper();
    private final SimpleS3 simpleS3;
    private final int maxAttempts;
    private final int delay;

    private int attempts;

    public BucketPoller(SimpleS3 simpleS3) {
        this(simpleS3, new DefaultRetryConfigBuilder().build());
    }

    public BucketPoller(SimpleS3 simpleS3, RetryConfig retryConfig) {
        this.simpleS3 = simpleS3;
        this.maxAttempts = retryConfig.getMaxAttempts();
        this.delay = retryConfig.getDelay();
    }

    public String pollBucketForContent(ObjectDefinition definition) {
        String output = null;
        attempts = 0;
        while (!isComplete(output, attempts)) {
            output = getOutput(definition);
            attempts++;
        }
        if (output == null)
            throw new ObjectNotFoundException(definition);
        return output;
    }

    private String getOutput(ObjectDefinition definition) {
        try {
            return simpleS3.getObjectAsString(definition);
        } catch (AmazonS3Exception e) {
            if (!isNoSuchKeyException(e))
                throw e;
            sleeper.sleep(delay);
            return null;
        }
    }

    private boolean isComplete(String output, int attempts) {
        return output != null || attempts > maxAttempts;
    }

    private boolean isNoSuchKeyException(AmazonS3Exception e) {
        return e.getErrorCode().equals("NoSuchKey");
    }

}
