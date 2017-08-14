package uk.co.mruoc.s3;

import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.event.S3EventNotification.S3BucketEntity;
import com.amazonaws.services.s3.event.S3EventNotification.S3Entity;
import com.amazonaws.services.s3.event.S3EventNotification.S3EventNotificationRecord;
import com.amazonaws.services.s3.event.S3EventNotification.S3ObjectEntity;

import java.util.List;

public class S3EventConverter {

    public ObjectDefinition toObjectDefinition(S3Event s3Event) {
        S3BucketEntity bucket = extractBucket(s3Event);
        S3ObjectEntity object = extractObject(s3Event);
        return new ObjectDefinition(bucket.getName(), object.getKey());
    }

    private static S3BucketEntity extractBucket(S3Event s3Event) {
        S3Entity s3 = extractS3(s3Event);
        return s3.getBucket();
    }

    private static S3ObjectEntity extractObject(S3Event s3Event) {
        S3Entity s3 = extractS3(s3Event);
        return s3.getObject();
    }

    private static S3Entity extractS3(S3Event s3Event) {
        List<S3EventNotificationRecord> records = s3Event.getRecords();
        S3EventNotificationRecord record = records.get(0);
        return record.getS3();
    }

}
