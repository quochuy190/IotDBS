package com.vbeeon.iotdbs.data.model;

import androidx.annotation.Nullable;

public class ApiException extends Exception {

    public ApiException(Integer errorCodes, String message) {
        this.message = message;
        this.errorCodes = errorCodes;
    }

    String message;

    Integer errorCodes;

    @Nullable
    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getErrorCodes() {
        return errorCodes;
    }

    public void setErrorCodes(Integer errorCodes) {
        this.errorCodes = errorCodes;
    }
}
