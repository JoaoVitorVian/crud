package com.challenge.crud.controller;

public class ApiResponse<T> {
    private T data;
    private String error;
    private String message;
    private boolean success;

    public ApiResponse() {}

    public ApiResponse(T data) {
        this.data = data;
        this.success = true;
    }

    public ApiResponse(String error) {
        this.error = error;
        this.success = false;
    } 
    public ApiResponse(T data, String message) {
        this.data = data;
        this.message = message;
        this.success = true;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}

