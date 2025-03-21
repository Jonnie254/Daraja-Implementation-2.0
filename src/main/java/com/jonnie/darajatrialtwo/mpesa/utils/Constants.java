package com.jonnie.darajatrialtwo.mpesa.utils;

import okhttp3.MediaType;

public class Constants {
    public static final String BASIC_AUTH_STRING = "Basic";
    public static final String AUTHORIZATION_HEADER_STRING = "Authorization";
    public static final String BEARER_AUTH_STRING = "Bearer ";
    public static final String CACHE_CONTROL_HEADER_VALUE = "no-cache";
    public static final String CACHE_CONTROL_HEADER = "Content-Type";
    public static final String ACCOUNT_BALANCE_COMMAND_ID = "AccountBalance";
    public static MediaType  JSON_MEDIA_TYPE = MediaType.parse("application/json; charset=utf-8");

}
