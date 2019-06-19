package com.follow.filter;

import com.alibaba.fastjson.JSONObject;
import com.follow.common.constant.FrameConstants;

import com.follow.entity.UserInfoVO;
import com.follow.user.enums.UserTypeEnum;
import com.follow.user.util.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;

@Slf4j
public class UserInfoFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        this.getUserInfo((HttpServletRequest) servletRequest);
        filterChain.doFilter(servletRequest, servletResponse);
    }


    public void getUserInfo(HttpServletRequest request) {
        String userStr = request.getHeader(FrameConstants.USERINFO_HTTP_HEADER);
        if (StringUtils.isNotBlank(userStr)) {
            try {
                userStr = URLDecoder.decode(userStr, "UTF-8");
                Map<String,String> userInfoMap = JSONObject.parseObject(userStr, Map.class);
                if (UserTypeEnum.普通用户.toValue().equals(userInfoMap.get("type"))) {
                    userStr = URLDecoder.decode(userStr, "UTF-8");
                    UserInfoVO userInfoVO = JSONObject.parseObject(userStr, UserInfoVO.class);
                    UserUtil.setCurrentUser(userInfoVO);
                    //设置全局用户信息
                 /*   UserInfo.userId = userInfoVO.getUser().getUid().toString();
                    UserInfo.userType = userInfoVO.getType();
                    UserInfo.ip = IPUtil.getIP(request);*/
                } else {
                    //管理员信息
                }
            } catch (UnsupportedEncodingException e) {
                log.error("获取用户信息失败", e);
            }
        } else {
            System.out.println("没有用户信息");
            UserUtil.setCurrentUser(null);
        }
    }
    @Override
    public void destroy() {

    }
}
