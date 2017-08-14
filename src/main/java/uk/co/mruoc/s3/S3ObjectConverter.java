package uk.co.mruoc.s3;

import com.amazonaws.services.s3.model.S3Object;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

public class S3ObjectConverter {

    private static final String DEFAULT_ENCODING = "UTF-8";

    private final String encoding;

    public S3ObjectConverter() {
        this(DEFAULT_ENCODING);
    }

    public S3ObjectConverter(String encoding) {
        this.encoding = encoding;
    }

    public String toContent(S3Object s3Object) {
        InputStream stream = s3Object.getObjectContent();
        try {
            return IOUtils.toString(stream, encoding);
        } catch (IOException e) {
            throw new SimpleS3Exception(e);
        } finally {
            IOUtils.closeQuietly(stream);
        }
    }

}
