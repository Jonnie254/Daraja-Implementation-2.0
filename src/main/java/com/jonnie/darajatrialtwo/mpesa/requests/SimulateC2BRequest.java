package com.jonnie.darajatrialtwo.mpesa.requests;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SimulateC2BRequest {
    @JsonProperty("ShortCode")
    private String shortCode;
    @JsonProperty("CommandID")
    private String commandID;
    @JsonProperty("Amount")
    private String amount;
    @JsonProperty("Msisdn")
    private String msisdn;
    @JsonProperty("BillRefNumber")
    private String billRefNumber;
}
