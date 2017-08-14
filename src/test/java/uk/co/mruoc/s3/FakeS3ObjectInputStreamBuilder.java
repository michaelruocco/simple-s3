package uk.co.mruoc.s3;

import com.amazonaws.services.s3.model.S3ObjectInputStream;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.HttpGet;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;

public class FakeS3ObjectInputStreamBuilder {

    private static final String ENCODING = "UTF-8";

    public S3ObjectInputStream build(String content) {
        try {
            InputStream in = IOUtils.toInputStream(content, ENCODING);
            return new S3ObjectInputStream(in, new HttpGet());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

}
