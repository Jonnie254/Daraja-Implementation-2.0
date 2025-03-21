package com.jonnie.darajatrialtwo.mpesa.requests;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class B2CPaymentRequest {

    @JsonProperty("OriginatorConversationID")
    private String originatorConversationID;

    @JsonProperty("InitiatorName")
    private String initiatorName;

    @JsonProperty("SecurityCredential")
    private String securityCredential;

    @JsonProperty("CommandID")
    private String commandID;

    @JsonProperty("Amount")
    private BigDecimal amount;

    @JsonProperty("PartyA")
    private String partyA;

    @JsonProperty("PartyB")
    private String partyB;

    @JsonProperty("Remarks")
    private String remarks;

    @JsonProperty("QueueTimeOutURL")
    private String queueTimeOutURL;

    @JsonProperty("ResultURL")
    private String resultURL;

    @JsonProperty("Occassion")
    private String occasion;
}