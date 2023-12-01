package com.tekcapzule.insight.domain.exception;

public class InsightServiceException extends RuntimeException{
    public InsightServiceException(String message, Exception e){
        super(message, e);
    }
}
