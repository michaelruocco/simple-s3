package uk.co.mruoc.s3;

import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.event.S3EventNotification.S3EventNotificationRecord;
import com.fasterxml.jackson.databind.ObjectMapper;
import uk.co.mruoc.properties.ClasspathFileContentLoader;
import uk.co.mruoc.properties.FileContentLoader;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Collections;
import java.util.List;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;

public class FakeS3EventBuilder {

    private static final String DEFAULT_TEST_EVENT_PATH = "/s3-event-notification-record.json";

    private final ObjectMapper mapper = new ObjectMapper();
    private final FileContentLoader contentLoader = new ClasspathFileContentLoader();

    public FakeS3EventBuilder() {
        mapper.configure(FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public S3Event build() {
        return build(DEFAULT_TEST_EVENT_PATH);
    }

    public S3Event build(String path) {
        try {
            String json = contentLoader.loadContent(path);
            S3EventNotificationRecord record = mapper.readValue(json, S3EventNotificationRecord.class);
            List<S3EventNotificationRecord> records = Collections.singletonList(record);
            return new S3Event(records);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

}
