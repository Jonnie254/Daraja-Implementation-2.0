package com.jonnie.darajatrialtwo.mpesa.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CheckAccountAsynResponse {

    @JsonProperty("Result")
    private Result result;
}
