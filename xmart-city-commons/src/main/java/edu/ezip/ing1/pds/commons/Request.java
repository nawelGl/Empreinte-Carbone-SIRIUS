package edu.ezip.ing1.pds.commons;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;

public class Request {

    public String requestId;

    private String requestOrder;

    //private String requestBody;
    private ArrayList<String> requestBody = new ArrayList<String>();

    public void setRequestOrder(String requestOrder) {
        this.requestOrder = requestOrder;
    }

    public void setRequestBody(JsonNode requestBody) {
        //this.requestBody = requestBody.toString();
        this.requestBody.add(requestBody.toString());
    }

    public void setRequestContent(final String requestBody) {
        this.requestBody.add(requestBody);
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

    public String getRequestBody(int index) {
        return requestBody.get(index);
    }

    public ArrayList<String> getRequestBody(){
        return this.requestBody;
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
