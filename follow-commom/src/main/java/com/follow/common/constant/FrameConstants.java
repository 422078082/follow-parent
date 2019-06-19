package com.follow.common.constant;

public interface FrameConstants {

    /**
     * 请求号header标识
     */
    String REQUEST_NO_HEADER_NAME = "Request-No";

    /**
     * 用户信息协议头标识
     */
    String USERINFO_HTTP_HEADER = "UserInfo-Http-Header";

    /**
     * header中的spanId，传递规则：request header中传递本服务的id
     */
    String SPAN_ID_HEADER_NAME = "Span-Id";
}
