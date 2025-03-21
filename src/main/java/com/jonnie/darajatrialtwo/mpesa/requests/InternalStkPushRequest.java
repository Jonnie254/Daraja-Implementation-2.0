package com.jonnie.darajatrialtwo.mpesa.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class InternalStkPushRequest {
    @JsonProperty("PhoneNumber")
    private String phoneNumber;
    @JsonProperty("Amount")
    private BigDecimal amount;
}
