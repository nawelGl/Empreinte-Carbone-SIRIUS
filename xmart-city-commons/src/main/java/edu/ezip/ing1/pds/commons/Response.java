package edu.ezip.ing1.pds.commons;

import com.fasterxml.jackson.databind.JsonNode;

public class Response {
    public String requestId;

    public String responseBody;

    public Response() {

    }
    public Response(String requestId, String responseBody) {
        this.requestId = requestId;
        this.responseBody = responseBody;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

    public void setBody(JsonNode responseBody) {
        this.responseBody = responseBody.toString();
    }

    @Override
    public String toString() {
        return "Response{" +
                "requestId='" + requestId + '\'' +
                ", responseBody='" + responseBody + '\'' +
                '}';
    }
}
