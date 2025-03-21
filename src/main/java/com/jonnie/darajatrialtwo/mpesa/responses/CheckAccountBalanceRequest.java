package com.jonnie.darajatrialtwo.mpesa.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CheckAccountBalanceRequest {
    @JsonProperty("QueueTimeOutURL")
    private String queueTimeOutURL;
    @JsonProperty("Initiator")
    private String initiator;
    @JsonProperty("Remarks")
    private String remarks;
    @JsonProperty("CommandID")
    private String commandID;
    @JsonProperty("PartyA")
    private String partyA;
    @JsonProperty("IdentifierType")
    private String identifierType;
    @JsonProperty("ResultURL")
    private String resultURL;
    @JsonProperty("SecurityCredential")
    private String securityCredential;
}
