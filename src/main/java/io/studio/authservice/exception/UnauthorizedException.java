package io.studio.authservice.exception;

/**
 * @author:poboking
 * @version:1.0
 * @time:2023/5/29 20:16
 */
public class UnauthorizedException extends AuthException {

    public UnauthorizedException(String message) {
        super(message);
    }
}