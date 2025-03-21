package com.jonnie.darajatrialtwo.mpesa.responses;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AcknowledgeResponse {
    @JsonProperty("ResultCode")
    private int resultCode;
    @JsonProperty("ResultDesc")
    private String resultDesc;
}
