package com.follow.user.enums;

public enum OriginEnum {

    微信公众号("0"),微信小程序("1"),车图网APP("2"),
    H5("3"),出行南宁("4");

    private String value;

    OriginEnum(String value) {
        this.value = value;
    }

    public String toValue() {
        return value;
    }

    public static OriginEnum fromValue(String value) {
        OriginEnum[] originEnums = OriginEnum.values();
        for (OriginEnum originEnum : originEnums) {
            if (originEnum.toValue().equals(value)) {
                return originEnum;
            }
        }
        return null;
    }
}
