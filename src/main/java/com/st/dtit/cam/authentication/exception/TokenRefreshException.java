package com.st.dtit.cam.authentication.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class TokenRefreshException extends RuntimeException {

        private static final long serialVersionUID = -5351499287772417109L;

        public TokenRefreshException(String token, String message) {
              super(String.format("Failed for [%s]: %s", token, message));
       }
}
