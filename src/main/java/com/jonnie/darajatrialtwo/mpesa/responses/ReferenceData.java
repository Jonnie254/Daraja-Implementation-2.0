package com.jonnie.darajatrialtwo.mpesa.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ReferenceData {
    @JsonProperty("ReferenceItem")
    private ReferenceItem ReferenceItem;
}
