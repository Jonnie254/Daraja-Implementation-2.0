package com.jonnie.darajatrialtwo.mpesa.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SimulateC2BResponse {

    @JsonProperty("ResponseCode")
    private String ResponseCode;
    @JsonProperty("OriginatorCoversationID")
    private String originatorCoversationID;
    @JsonProperty("ResponseDescription")
    private String responseDescription;
}
