package com.jonnie.darajatrialtwo.mpesa.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class InternalTransactionStatusRequest {
    @JsonProperty("TransactionID")
    private String transactionId;
}
