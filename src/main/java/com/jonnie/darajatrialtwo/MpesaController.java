package com.jonnie.darajatrialtwo;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jonnie.darajatrialtwo.mpesa.requests.InternalStkPushRequest;
import com.jonnie.darajatrialtwo.mpesa.requests.InternalTransactionStatusRequest;
import com.jonnie.darajatrialtwo.mpesa.responses.*;
import com.jonnie.darajatrialtwo.mpesa.services.DarajaApi;
import com.jonnie.darajatrialtwo.mpesa.requests.SimulateB2CRequest;
import com.jonnie.darajatrialtwo.mpesa.requests.SimulateC2BRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/mobile-money")
public class MpesaController {
    private final DarajaApi darajaApi;
    private final ObjectMapper objectMapper;

    // This method returns an access token
    @GetMapping(path = "/token", produces = "application/json")
    public ResponseEntity<AccessTokenResponse> getAccessToken() {
        return ResponseEntity.ok(darajaApi.getAccessToken());
    }

    // This method returns a response after registering a URL
    @PostMapping(path = "/register-url", produces = "application/json")
    public ResponseEntity<RegisterUrlResponse> getRegisterUrl() {
        return ResponseEntity.ok(darajaApi.registerUrl());
    }

    @PostMapping(path = "/validation", produces = "application/json")
    public ResponseEntity<AcknowledgeResponse> acknowledgeTransaction(
            @RequestBody TransactionResult transactionResult) {
        return ResponseEntity.ok(new AcknowledgeResponse(0, "Accepted"));
    }

    //simulate c2b transaction
    @PostMapping(path = "/simulate-c2b", produces = "application/json")
    public ResponseEntity<SimulateC2BResponse> simulateC2BTransaction(
            @RequestBody SimulateC2BRequest simulateC2BRequest) {
        return ResponseEntity.ok(darajaApi.simulateC2BTransaction(simulateC2BRequest));
    }

    // where results of  transactions are posted
    @PostMapping(path = "/transactionResult", produces = "application/json")
    public ResponseEntity<AcknowledgeResponse> b2cTransactionResult(
            @RequestBody B2CTransactionalAsyncResponse b2CTransactionalAsyncResponse) throws
            JsonProcessingException {
        return ResponseEntity.ok(new AcknowledgeResponse(0, "Accepted"));
    }

    // simulate b2c queue timeout
    @PostMapping(path="/b2cQueueTimeout", produces = "application/json")
    public ResponseEntity<AcknowledgeResponse> b2cQueueTimeout(
            @RequestBody B2CTransactionalAsyncResponse b2CTransactionalAsyncResponse) throws
            JsonProcessingException {
        log.info(objectMapper.writeValueAsString(b2CTransactionalAsyncResponse));
        return ResponseEntity.ok(new AcknowledgeResponse(0, "Accepted"));
    }

    //simulate b2c  request to pay
    @PostMapping(path = "/simulate-b2c", produces = "application/json")
    public ResponseEntity<CommonSyncResponse> simulateB2CTransaction(
            @RequestBody SimulateB2CRequest simulateB2CRequest) {
        CommonSyncResponse response = darajaApi.simulateB2CTransaction(simulateB2CRequest);
        return ResponseEntity.ok(response);
    }

    // simulate transaction status query
    @PostMapping(path = "/transaction-query-status", produces = "application/json")
    public ResponseEntity<?> getTransactionResult(
            @RequestBody InternalTransactionStatusRequest internalTransactionStatusRequest) {
       CommonSyncResponse response = darajaApi.getTransactionResult(internalTransactionStatusRequest);
        if (response == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Failed to fetch account balance. Please check your request."));
        }
        return ResponseEntity.ok(response);
    }

    // simulate account balance
    @GetMapping(path = "/check-account-balance", produces = "application/json")
    public ResponseEntity<?> checkAccountBalance() {
        CommonSyncResponse response = darajaApi.checkAccountBalance();
        if (response == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Failed to fetch account balance. Please check your request."));
        }
        return ResponseEntity.ok(response);
    }

    // simulate stk push to the customer
    @PostMapping(path = "/stk-push-request", produces = "application/json")
    public ResponseEntity<?> initiateStkPush(@RequestBody InternalStkPushRequest internalStkPushRequest) {
        ExternalStkPushSyncResponse response = darajaApi.initiateStkPush(internalStkPushRequest);
        if (response == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Failed to initiate STK push. Please check your request."));
        }
        return ResponseEntity.ok(response);
    }

    // simulate stk push callback
    @PostMapping(path = "/stk-push-call-backurl", produces = "application/json")
    public ResponseEntity<?> acknowledgeStkPushResponse(@RequestBody StkPushAsyncResult stkPushAsyncResult) throws JsonProcessingException {
        log.info(objectMapper.writeValueAsString(stkPushAsyncResult));
        return ResponseEntity.ok(new AcknowledgeResponse(0, "Accepted"));
    }
}
