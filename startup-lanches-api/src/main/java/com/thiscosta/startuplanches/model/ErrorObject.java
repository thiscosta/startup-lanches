package com.thiscosta.startuplanches.model;

public class ErrorObject {

    public ErrorObject(boolean success, String errorMessage){
        this.success = success;
        this.errorMessage = errorMessage;
    }

    private boolean success;

    private String errorMessage;

    public String getErrorMessage() {
        return this.errorMessage;
    }
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public boolean getSuccess() {
        return this.success;
    }
    public void setAttribute(boolean success) {
        this.success = success;
    }
}