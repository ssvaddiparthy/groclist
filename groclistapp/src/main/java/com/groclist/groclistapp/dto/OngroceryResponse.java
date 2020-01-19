package com.groclist.groclistapp.dto;

import java.util.Date;

public class OngroceryResponse {

    private Date timestamp;
    private String message;
    private String requestId;
    private String responseData;

    public OngroceryResponse(){}

    public OngroceryResponse(Date timestamp, String message, String requestId, String details) {
        super();
        this.timestamp = timestamp;
        this.message = message;
        this.requestId = requestId;
        this.responseData = details;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResponseData() {
        return responseData;
    }

    public void setResponseData(String responseData) {
        this.responseData = responseData;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    @Override
    public String toString() {
        return "OngroceryResponse{" +
                ", timestamp=" + timestamp +
                ", message='" + message + '\'' +
                ", details='" + responseData + '\'' +
                '}';
    }
}
