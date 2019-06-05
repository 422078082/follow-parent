package com.follow.result;

public class DefaultFallBack {

    public static ResponseResult defaultFallBack() {
        return ResponseResult.fail(ResponseStatusCode.ADFAULT_SERVICE_ERROR,"服务器繁忙，请稍后再试","",0L);
    }
}
