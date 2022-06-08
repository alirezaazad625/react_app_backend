package ir.sharif.iam.iam.domain.exceptions;

import org.springframework.security.authentication.BadCredentialsException;

public class BadCredentialException extends BadCredentialsException {

    public BadCredentialException() {
        super("bad credential");
    }
}
