package com.jonnie.darajatrialtwo.mpesa.mpesaconfig;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
@ConfigurationProperties(prefix = "mpesa.daraja")
public class MpesaConfiguration {
    private String consumerKey;
    private String consumerSecret;
    private String grantType;
    private String OauthEndpoint;
    private String shortCode;
    private String responseType;
    private String confirmationUrl;
    private String validationUrl;
    private String registerUrl;
    private String simulateC2BUrl;
    private String b2cTransactionUrl;
    private String b2cResultUrl;
    private String b2cQueueTimeOutUrl;
    private String b2cInitiatorName;
    private String b2cSecurityCredential;
    private String transactionResultUrl;
    private String checkAccountBalanceUrl;
    private String stkPassKey;
    private String stkRequestPushUrl;
    private String stkRequestQueryUrl;
    private String stkPushResultCallBackUrl;
    private String stkPushCode;

    @Override
    public String toString() {
        return "MpesaConfiguration [consumerKey=" + consumerKey + ", consumerSecret=" + consumerSecret
                + ", grantType=" + grantType + ", OauthEndpoint=" + OauthEndpoint + "]";
    }
}
