package com.jonnie.darajatrialtwo.mpesa.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ResultParameterItem {
    @JsonProperty("Value")
    private Object value;
    @JsonProperty("Key")
    private String key;
}
