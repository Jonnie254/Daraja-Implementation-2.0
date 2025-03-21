package com.jonnie.darajatrialtwo.mpesa.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RegisterUrlRequest(
        @JsonProperty("ShortCode")
        String shortCode,
        @JsonProperty("ResponseType")
        String responseType,
        @JsonProperty("ConfirmationURL")
        String confirmationUrl,
        @JsonProperty("ValidationURL")
        String validationUrl
) {
}
