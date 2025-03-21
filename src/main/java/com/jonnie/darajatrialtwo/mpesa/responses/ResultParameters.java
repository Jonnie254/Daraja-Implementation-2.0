package com.jonnie.darajatrialtwo.mpesa.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ResultParameters {
    @JsonProperty("ResultParameter")
    private List<ResultParameterItem> resultParameterItem;
}
