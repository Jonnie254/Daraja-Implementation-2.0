package com.jonnie.darajatrialtwo.mpesa.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RegisterUrlResponse {
    @JsonProperty("ConversationID")
    private String conversationID;
    @JsonProperty("ResponseDescription")
    private String responseDescription;
    @JsonProperty("OriginatorCoversationID")
    private String originatorCoversationID;
}
