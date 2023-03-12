package ru.natlex.natlexTestApp.dto;

import org.springframework.beans.factory.annotation.Value;

public class UnsuccessfulResponse {
    private String error;
    private int statusCode;

    public UnsuccessfulResponse(String error, int statusCode) {
        this.error = error;
        this.statusCode = statusCode;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
