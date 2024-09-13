package com.petrtitov.config;

import java.util.List;

public class ErrorInfo {
    private ErrorType type;
    private List<String> details;

    public ErrorInfo(ErrorType type, List<String> details) {
        this.type = type;
        this.details = details;
    }

    public ErrorInfo() {
    }


    public ErrorType getType() {
        return type;
    }

    public List<String> getDetails() {
        return details;
    }
}