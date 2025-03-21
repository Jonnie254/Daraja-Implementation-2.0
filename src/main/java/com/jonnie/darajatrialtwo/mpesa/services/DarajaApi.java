package com.jonnie.darajatrialtwo.mpesa.services;

import com.jonnie.darajatrialtwo.mpesa.requests.InternalStkPushRequest;
import com.jonnie.darajatrialtwo.mpesa.requests.InternalTransactionStatusRequest;
import com.jonnie.darajatrialtwo.mpesa.responses.*;
import com.jonnie.darajatrialtwo.mpesa.requests.SimulateB2CRequest;
import com.jonnie.darajatrialtwo.mpesa.requests.SimulateC2BRequest;


public interface DarajaApi {

    AccessTokenResponse getAccessToken();
    RegisterUrlResponse registerUrl();
    SimulateC2BResponse simulateC2BTransaction(SimulateC2BRequest simulateC2BRequest);
    CommonSyncResponse simulateB2CTransaction(SimulateB2CRequest simulateB2CRequest);
    CommonSyncResponse getTransactionResult(InternalTransactionStatusRequest internalTransactionStatusRequest);
    CommonSyncResponse checkAccountBalance();
    ExternalStkPushSyncResponse initiateStkPush(InternalStkPushRequest internalStkPushRequest);

}
