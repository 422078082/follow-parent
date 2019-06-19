package com.follow.user.enums;

public enum UserTypeEnum {

    普通用户("user"),后台管理员("admin"),平台用户("parkingUser"),商家用户("businessUser");

    private String value;

    UserTypeEnum(String value) {
        this.value = value;
    }

    public String toValue() {
        return value;
    }

    public static UserTypeEnum fromValue(String value) {
        UserTypeEnum[] userTypeEnums = UserTypeEnum.values();
        for (UserTypeEnum userTypeEnum : userTypeEnums) {
            if (userTypeEnum.toValue().equals(value)) {
                return userTypeEnum;
            }
        }
        return null;
    }
}
