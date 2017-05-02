package com.norsys.service.exception;


public class AuthenticationException extends Exception {

    public AuthenticationException() {
        super("AuthenticationException : nombre maximal de tentatives atteint.");
    }
}
