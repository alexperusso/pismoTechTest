package com.pismo.util.advice;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ValidationErrorInfo {

    private class ViolationInfo {
        private final String fieldName;
        private final String message;

        public ViolationInfo(String fieldName, String message) {
            this.fieldName = fieldName;
            this.message = message;
        }

        public String getFieldName() {
            return fieldName;
        }
        public String getMessage() {
            return message;
        }
    }

    private final ZonedDateTime timestamp;
    private List<ViolationInfo> violacoes = new ArrayList<>();

    public ValidationErrorInfo() {
        timestamp = ZonedDateTime.now();
    }

    public ValidationErrorInfo(String fieldName, String message) {
        this();
        this.addViolationError(fieldName, message);
    }

    public void addViolationError(String fieldName, String message) {
        this.violacoes.add(new ViolationInfo(fieldName, message));
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public List<ViolationInfo> getViolacoes() {
        return violacoes;
    }

}
