package com.jonnie.darajatrialtwo.mpesa.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class SimulateB2CRequest {
    @JsonProperty("OriginatorConversationID")
    private String originatorConversationID;

    @JsonProperty("CommandID")
    private String commandID;

    @JsonProperty("Amount")
    private BigDecimal amount;

    @JsonProperty("PartyB")
    private String partyB;

    @JsonProperty("Remarks")
    private String remarks;

    @JsonProperty("Occassion")
    private String ocassion;

    public SimulateB2CRequest() {
        this.originatorConversationID = UUID.randomUUID().toString();
    }
}
