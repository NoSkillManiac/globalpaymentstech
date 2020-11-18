package auer.josh.globalpaymentstech.dto;

import lombok.Builder;

@Builder
public class Error {
    private String resourceKey;
    private String errorCode;
    private String message;
}
