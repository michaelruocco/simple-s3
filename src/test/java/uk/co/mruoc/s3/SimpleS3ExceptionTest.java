package uk.co.mruoc.s3;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SimpleS3ExceptionTest {

    private static final String MESSAGE = "my message";
    private static final Throwable CAUSE = new Exception();

    @Test
    public void shouldReturnCause() {
        Throwable exception = new SimpleS3Exception(CAUSE);

        assertThat(exception.getCause()).isEqualTo(CAUSE);
    }

    @Test
    public void shouldReturnMessage() {
        Throwable exception = new SimpleS3Exception(MESSAGE);

        assertThat(exception.getMessage()).isEqualTo(MESSAGE);
    }

}
