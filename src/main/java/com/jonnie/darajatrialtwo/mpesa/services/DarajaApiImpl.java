package com.jonnie.darajatrialtwo.mpesa.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jonnie.darajatrialtwo.mpesa.mpesaconfig.MpesaConfiguration;
import com.jonnie.darajatrialtwo.mpesa.requests.*;
import com.jonnie.darajatrialtwo.mpesa.responses.*;
import com.jonnie.darajatrialtwo.mpesa.utils.Constants;
import com.jonnie.darajatrialtwo.mpesa.utils.HelperUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

import static com.jonnie.darajatrialtwo.mpesa.utils.Constants.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class DarajaApiImpl implements DarajaApi {
    private final MpesaConfiguration mpesaConfiguration;
    private final ObjectMapper objectMapper;
    private final OkHttpClient okHttpClient;

    @Override
    public AccessTokenResponse getAccessToken() {
        // Encode consumer key & secret
        String encodedCredentials = HelperUtility.toBase64String(
                String.format("%s:%s", mpesaConfiguration.getConsumerKey(), mpesaConfiguration.getConsumerSecret())
        );

        String url = String.format("%s?grant_type=%s",
                mpesaConfiguration.getOauthEndpoint(),
                mpesaConfiguration.getGrantType()
        );
        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("Authorization", "Basic " + encodedCredentials)
                .addHeader("Cache-Control", "no-cache")
                .build();
        try (Response response = okHttpClient.newCall(request).execute()) {
            assert response.body() != null;
            String responseBody = response.body().string();
            return objectMapper.readValue(responseBody, AccessTokenResponse.class);
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public RegisterUrlResponse registerUrl() {
        AccessTokenResponse accessTokenResponse = getAccessToken();
        if (accessTokenResponse == null || accessTokenResponse.getAccessToken() == null) {
            return null;
        }
        RegisterUrlRequest registerUrlRequest = new RegisterUrlRequest(
                mpesaConfiguration.getShortCode(),
                mpesaConfiguration.getResponseType(),
                mpesaConfiguration.getConfirmationUrl(),
                mpesaConfiguration.getValidationUrl()
        );

        String requestJson = HelperUtility.toJson(registerUrlRequest);
        RequestBody requestBody = RequestBody.create(
                Objects.requireNonNull(HelperUtility.toJson(requestJson)),
                JSON_MEDIA_TYPE
        );
        Request request = new Request.Builder()
                .url(mpesaConfiguration.getRegisterUrl())
                .post(requestBody)
                .addHeader(AUTHORIZATION_HEADER_STRING, BEARER_AUTH_STRING +
                        accessTokenResponse.getAccessToken())
                .build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            assert response.body() != null;
            String responseBody = response.body().string();
            return objectMapper.readValue(responseBody, RegisterUrlResponse.class);
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public SimulateC2BResponse simulateC2BTransaction(SimulateC2BRequest simulateC2BRequest) {
        AccessTokenResponse accessTokenResponse = getAccessToken();
        if (accessTokenResponse == null || accessTokenResponse.getAccessToken() == null) {
            return null;
        }
        RequestBody requestBody = RequestBody.create(
                Objects.requireNonNull(HelperUtility.toJson(simulateC2BRequest)),
                JSON_MEDIA_TYPE
        );
        Request request = new Request.Builder()
                .url(mpesaConfiguration.getSimulateC2BUrl())
                .post(requestBody)
                .addHeader(AUTHORIZATION_HEADER_STRING, BEARER_AUTH_STRING +
                        accessTokenResponse.getAccessToken())
                .build();

        try {
            Response response = okHttpClient.newCall(request).execute();

            if (response.body() == null) {
                return null;
            }
            String rawResponse = response.body().string();
            return objectMapper.readValue(rawResponse, SimulateC2BResponse.class);

        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public CommonSyncResponse simulateB2CTransaction(SimulateB2CRequest simulateB2CRequest) {
        AccessTokenResponse accessTokenResponse = getAccessToken();
        if (accessTokenResponse == null || accessTokenResponse.getAccessToken() == null) {
            return null;
        }

        B2CPaymentRequest b2cPaymentRequest = new B2CPaymentRequest();
        b2cPaymentRequest.setOriginatorConversationID(UUID.randomUUID().toString());
        b2cPaymentRequest.setCommandID(simulateB2CRequest.getCommandID());
        b2cPaymentRequest.setAmount(simulateB2CRequest.getAmount());
        b2cPaymentRequest.setPartyB(simulateB2CRequest.getPartyB());
        b2cPaymentRequest.setRemarks(simulateB2CRequest.getRemarks());
        b2cPaymentRequest.setOccasion(simulateB2CRequest.getOcassion());
//        String securityCredential = "KnDZM4TGr3xfA9N1YoJhvyabzWJVsOFZEPYR7PXsu0z9GYTYAaKkTdg9F88N0A84040j7TFyoZWNndD1EKqr4vZsl+hFkfZo4MjDJaAjfWZJF6a11mjdkSACFywhE0Lo6SHad1vTqt6DyJboRBcauCfP0loEyC8Fac73C1xP0fygLm2x8FhISD7mNBATKQNlp4jAefQUaxh7+6VWCf5y24Ii5eLF7UE73Y8W8xcYzdwKitIVXoOfUcAJAk+SzJAITFt1eOjDw2XqtKYqYEFl+T8lXgDElJc4kJ36Mpe+doQ18jE6z8RgIoDfAlpF7ejw2j1IFu4bjVavkJ4KnQJlaw==";
        b2cPaymentRequest.setSecurityCredential(HelperUtility.getSecurityCredential(mpesaConfiguration.getB2cSecurityCredential()));
        b2cPaymentRequest.setInitiatorName(mpesaConfiguration.getB2cInitiatorName());
        b2cPaymentRequest.setResultURL(mpesaConfiguration.getB2cResultUrl());
        b2cPaymentRequest.setQueueTimeOutURL(mpesaConfiguration.getB2cQueueTimeOutUrl());
        b2cPaymentRequest.setPartyA(mpesaConfiguration.getShortCode());

        RequestBody requestBody = RequestBody.create(
                Objects.requireNonNull(HelperUtility.toJson(b2cPaymentRequest)),
                JSON_MEDIA_TYPE
        );
        Request request = new Request.Builder()
                .url(mpesaConfiguration.getB2cTransactionUrl())
                .post(requestBody)
                .addHeader(AUTHORIZATION_HEADER_STRING, BEARER_AUTH_STRING +
                        accessTokenResponse.getAccessToken())
                .build();

        try {
            Response response = okHttpClient.newCall(request).execute();
            if (response.body() == null) {
                log.error("Safaricom response body is null");
                return null;
            }
            String rawResponse = response.body().string();
            log.info("Safaricom Response: {}", rawResponse);
            return objectMapper.readValue(rawResponse, CommonSyncResponse.class);
        } catch (IOException e) {
            log.error("Error sending B2C request: {}", e.getMessage(), e);
            return null;
        }
    }


    @Override
    public CommonSyncResponse getTransactionResult(InternalTransactionStatusRequest internalTransactionStatusRequest) {
        AccessTokenResponse accessTokenResponse = getAccessToken();
        TransactionStatusRequest transactionStatusRequest = new TransactionStatusRequest();
        transactionStatusRequest.setTransactionID(internalTransactionStatusRequest.getTransactionId());
        transactionStatusRequest.setInitiator(mpesaConfiguration.getB2cInitiatorName());
        transactionStatusRequest.setSecurityCredential(HelperUtility.getSecurityCredential(mpesaConfiguration.getB2cSecurityCredential()));
        transactionStatusRequest.setCommandID("TransactionStatusQuery");
        transactionStatusRequest.setPartyA(mpesaConfiguration.getShortCode());
        transactionStatusRequest.setIdentifierType("4");
        transactionStatusRequest.setResultURL(mpesaConfiguration.getB2cResultUrl());
        transactionStatusRequest.setQueueTimeOutURL(mpesaConfiguration.getB2cQueueTimeOutUrl());
        transactionStatusRequest.setRemarks("Transaction Status Query");
        transactionStatusRequest.setOccasion("Transaction Status Query");

        try {
            RequestBody requestBody = RequestBody.create(
                    Objects.requireNonNull(HelperUtility.toJson(transactionStatusRequest)),
                    JSON_MEDIA_TYPE
            );
            Request request = new Request.Builder()
                    .url(mpesaConfiguration.getTransactionResultUrl())
                    .post(requestBody)
                    .addHeader(AUTHORIZATION_HEADER_STRING, BEARER_AUTH_STRING +
                            accessTokenResponse.getAccessToken())
                    .build();

            Response response = okHttpClient.newCall(request).execute();
            if (response.body() == null) {
                return null;
            }
            String rawResponse = response.body().string();
            return objectMapper.readValue(rawResponse, CommonSyncResponse.class);
        } catch (IOException e) {
            return null;
        }

    }

    @Override
    public CommonSyncResponse checkAccountBalance() {
        AccessTokenResponse accessTokenResponse = getAccessToken();
        CheckAccountBalanceRequest checkAccountBalanceRequest = new CheckAccountBalanceRequest();
        checkAccountBalanceRequest.setInitiator(mpesaConfiguration.getB2cInitiatorName());
        checkAccountBalanceRequest.setSecurityCredential(HelperUtility.getSecurityCredential(mpesaConfiguration.getB2cSecurityCredential()));
        checkAccountBalanceRequest.setCommandID(Constants.ACCOUNT_BALANCE_COMMAND_ID);
        checkAccountBalanceRequest.setPartyA(mpesaConfiguration.getShortCode());
        checkAccountBalanceRequest.setIdentifierType("4");
        checkAccountBalanceRequest.setRemarks("Check Account Balance");
        checkAccountBalanceRequest.setQueueTimeOutURL(mpesaConfiguration.getB2cQueueTimeOutUrl());
        checkAccountBalanceRequest.setResultURL(mpesaConfiguration.getB2cResultUrl());
        try {
            RequestBody requestBody = RequestBody.create(
                    Objects.requireNonNull(HelperUtility.toJson(checkAccountBalanceRequest)),
                    JSON_MEDIA_TYPE
            );
            Request request = new Request.Builder()
                    .url(mpesaConfiguration.getCheckAccountBalanceUrl())
                    .post(requestBody)
                    .addHeader(AUTHORIZATION_HEADER_STRING, BEARER_AUTH_STRING +
                            accessTokenResponse.getAccessToken())
                    .build();

            Response response = okHttpClient.newCall(request).execute();
            if (response.body() == null) {
                return null;
            }
            String rawResponse = response.body().string();
            log.info("Raw M-Pesa Account Balance Response: {}", rawResponse);
            return objectMapper.readValue(rawResponse, CommonSyncResponse.class);
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public ExternalStkPushSyncResponse initiateStkPush(InternalStkPushRequest internalStkPushRequest) {
        AccessTokenResponse accessTokenResponse = getAccessToken();
        String transactionTimeStamp = HelperUtility.getTransactionTimeStamp();
        ExternalStkPushRequest externalStkPushRequest = new ExternalStkPushRequest();
        String stkPushPassword = HelperUtility.getStkPushPassword(
                mpesaConfiguration.getStkPushCode(),
                mpesaConfiguration.getStkPassKey(),
                transactionTimeStamp
        );
        externalStkPushRequest.setPassword(stkPushPassword);
        externalStkPushRequest.setBusinessShortCode(mpesaConfiguration.getStkPushCode());
        externalStkPushRequest.setTimestamp(transactionTimeStamp);
        externalStkPushRequest.setTransactionType("CustomerPayBillOnline");
        externalStkPushRequest.setAmount(String.valueOf(internalStkPushRequest.getAmount()));
        externalStkPushRequest.setCallBackURL(mpesaConfiguration.getStkPushResultCallBackUrl());
        externalStkPushRequest.setPhoneNumber(internalStkPushRequest.getPhoneNumber());
        externalStkPushRequest.setPartyA(internalStkPushRequest.getPhoneNumber());
        externalStkPushRequest.setPartyB(mpesaConfiguration.getStkPushCode());
        externalStkPushRequest.setAccountReference(HelperUtility.getTransactionUniqueId());
        externalStkPushRequest.setTransactionDesc(String.format("Payment for %s", internalStkPushRequest.getPhoneNumber()));
        try{
            RequestBody requestBody = RequestBody.create(
                    Objects.requireNonNull(HelperUtility.toJson(externalStkPushRequest)),
                    JSON_MEDIA_TYPE
            );
            Request request = new Request.Builder()
                    .url(mpesaConfiguration.getStkRequestPushUrl())
                    .post(requestBody)
                    .addHeader(AUTHORIZATION_HEADER_STRING, BEARER_AUTH_STRING +
                            accessTokenResponse.getAccessToken())
                    .build();
            Response response = okHttpClient.newCall(request).execute();
            if (response.body() == null) {
                return null;
            }
            String rawResponse = response.body().string();
            return objectMapper.readValue(rawResponse, ExternalStkPushSyncResponse.class);
        } catch (IOException e) {
            return null;
        }

    }
}
