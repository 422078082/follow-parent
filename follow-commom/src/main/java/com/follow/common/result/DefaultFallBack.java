package com.follow.common.result;

public class DefaultFallBack {

    public static ResponseData defaultFallBack() {
        return ResponseData.error(ResponseStatusCode.ADFAULT_SERVICE_ERROR,"服务器繁忙，请稍后再试","");
    }
}
