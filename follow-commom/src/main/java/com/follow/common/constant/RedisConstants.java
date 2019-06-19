package com.follow.common.constant;

public interface RedisConstants {
    /**
     * 优惠券发行key  couponsPublish: + uid
     */
    String COUPONS_PUBLISH = "couponsPublish:";

    /**
     * 手机验证码key
     */
    String MOBILE_CAPTCHA = "captcha:";

    /**
     * 微信信息缓存
     */
    String WECHAT_INFO = "wechatInfo:";

    /**
     * API放行规则
     */
    String API_RELEASE = "apiRelease";

    /**
     * 后台菜单
     */
    String ADMIN_MENU = "adminMenu";

    /**
     * 后台权限
     */
    String ADMIN_RELATION = "adminRelation";
}
