package edu.ezip.ing1.pds.commons;

import com.fasterxml.jackson.databind.JsonNode;

public class Request {

    public String requestId;

    private String requestOrder;

    private String requestBody;

    public void setRequestOrder(String requestOrder) {
        this.requestOrder = requestOrder;
    }

    public void setRequestBody(JsonNode requestBody) {
        this.requestBody = requestBody.toString();
    }

    public void setRequestContent(final String requestBody) {
        this.requestBody = requestBody;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getRequestId() {
        return requestId;
    }

    public String getRequestOrder() {
        return requestOrder;
    }

    public String getRequestBody() {
        return requestBody;
    }

    @Override
    public String toString() {
        return "Request{" +
                "requestDd=" + requestId +
                ", requestOrder='" + requestOrder + '\'' +
                ", requestBody='" + requestBody + '\'' +
                '}';
    }
}