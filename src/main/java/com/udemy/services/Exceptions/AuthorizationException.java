package com.udemy.services.Exceptions;

public class AuthorizationException extends RuntimeException {
    public AuthorizationException(String mensagem) {
        super(mensagem);
    }

    public AuthorizationException(String mensagem, Throwable cause) {
        super(mensagem, cause);
    }
}
