package com.authy.onetouch.approvalrequest;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

@SuppressWarnings("unused")
public class Result {

    private String message;
    private Boolean success;
    private String errorCode;
    private Map<String, String> errors;
    private Map<String, String> approvalRequest;

    @JsonProperty("message")
    public String getMessage() {
        return message;
    }

    @JsonProperty("success")
    public Boolean getSuccess() {
        return success;
    }

    @JsonProperty("errors")
    public Map<String, String> getErrors() {
        return errors;
    }

    @JsonProperty("approval_request")
    public Map<String, String> getApprovalRequest() {
        return approvalRequest;
    }

    @JsonProperty("error_code")
    public String getErrorCode() {
        return errorCode;
    }

    public boolean isOk() {
        return success;
    }
}
