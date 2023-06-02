package io.studio.authservice.exception;

/**
 * @author:poboking
 * @version:1.0
 * @time:2023/5/29 20:16
 */


public class AuthException extends RuntimeException {

    public AuthException(String message) {
        super(message);
    }
}
