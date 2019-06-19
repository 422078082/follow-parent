package com.follow.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@ApiModel(value = "密码登录VO", description = "密码登录VO")
public class UserPasswordLoginVO {

    @ApiModelProperty(value = "手机号码", name = "mobile", example = "13627885390", required = true,dataType = "java.lang.String")
    @NotNull(message = "手机号码不允许为空")
    @Size(max = 11,min = 11, message = "手机号码长度不正确")
    @Pattern(regexp = "^1[3|4|5|6|7|8|9][0-9]\\d{8}$",message = "手机格式不正确")
    private String mobile;

    @NotNull(message = "密码不允许为空")
    @Size(max = 16,min = 6,message = "密码长度不正确")
    @ApiModelProperty(value = "密码", name = "password", example = "123456",required = true, dataType = "java.lang.String")
    private String password;

    @NotNull(message = "来源不允许为空")
    @ApiModelProperty(value = "来源 0：微信公众号,1：微信小程序,2：平台APP,3：H5,4：后台管理", name = "origin", example = "1",required = true, dataType = "java.lang.String")
    private String origin;
}
