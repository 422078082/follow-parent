package com.follow.filter;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.follow.common.constant.FrameConstants;
import com.follow.common.constant.RedisConstants;
import com.follow.common.jwt.JwtTokenUtil;
import com.follow.common.result.ErrorResponseData;
import com.follow.common.util.DateUtil;
import com.follow.user.enums.UserErrorEnum;
import com.follow.user.enums.UserTypeEnum;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;


/**
 * @description: JWT过滤器
 * @author: huzhiwen
 * @create: 2019-01-30 17:13
 **/

@Slf4j
public class TokenFilter extends ZuulFilter {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Value(value = "${TOKEN.AGINGTIME}")
    private Long AgingTime;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        HttpServletResponse response = requestContext.getResponse();

        //放行接口
      /*  if (getRelease(request)) {
            return null;
        }*/

        // 从Http请求获得授权
        try {
            response.setCharacterEncoding("utf-8");
            response.setContentType("json/application; charset=utf-8");
            final String token = request.getHeader("Authorization");
            if (StringUtils.isBlank(token) || JwtTokenUtil.isTokenExpired(token)) {
                //若访问的接口传入token则可以获取获取用户信息，若不传也可以放行访问
               if (getChoiceRelease(request)) {
                    return null;
                }
                String json = JSONObject.toJSONString(ErrorResponseData.error(UserErrorEnum.未登录), SerializerFeature.WriteMapNullValue);
                response.getWriter().write(json);
                response.getWriter().flush();
                response.getWriter().close();
                requestContext.setSendZuulResponse(false);
                requestContext.setResponseStatusCode(200);
                return null;
            } else {
                String userStr = redisTemplate.opsForValue().get(JwtTokenUtil.getUserIdFromToken(token));
                if (StringUtils.isNotBlank(userStr)) {
                    //校验token是否有效
                    Map<String, Object> userMap = JSONObject.parseObject(userStr, Map.class);
                    Map<String, Object> validateInfo = (Map<String, Object>) userMap.get("validateInfo");
                    boolean flag = false;
                    for (String s : validateInfo.keySet()) {
                        if (validateInfo.get(s).equals(token)) {
                            flag = true;
                            break;
                        }
                    }

                    if (!flag) {
                        //若访问的接口传入token则可以获取获取用户信息，若不传也可以放行访问
                        if (getChoiceRelease(request)) {
                            return null;
                        }
                        String json = JSONObject.toJSONString(ErrorResponseData.error(UserErrorEnum.在别处登录), SerializerFeature.WriteMapNullValue);
                        response.getWriter().write(json);
                        requestContext.setSendZuulResponse(false);
                        requestContext.setResponseStatusCode(200);
                        response.getWriter().flush();
                        response.getWriter().close();
                        return null;
                    }

                    //判断是否跨权限访问
                    String uri = request.getRequestURI();
                    String userType = (String) userMap.get("type");
                    if (UserTypeEnum.普通用户.toValue().equals(userType)) {
                        if (uri.contains("/admin/")) {
                            String json = JSONObject.toJSONString(ErrorResponseData.error(UserErrorEnum.未登录), SerializerFeature.WriteMapNullValue);
                            response.getWriter().write(json);
                            requestContext.setSendZuulResponse(false);
                            requestContext.setResponseStatusCode(200);
                            response.getWriter().flush();
                            response.getWriter().close();
                            return null;
                        }
                    }

                    requestContext.addZuulRequestHeader(FrameConstants.USERINFO_HTTP_HEADER, URLEncoder.encode(userStr, "UTF-8"));
                    //刷新token
                    Date issued = JwtTokenUtil.getIssuedAtDateFromToken(token);
                    long date = DateUtil.formatSubDateForMinutes(new Date(), issued);
                    //若token的有效期大于两个小时则提示前端进行token刷新
                    if (date > AgingTime) {
                        String json = JSONObject.toJSONString(ErrorResponseData.error(UserErrorEnum.请进行令牌刷新), SerializerFeature.WriteMapNullValue);
                        response.getWriter().write(json);
                        requestContext.setSendZuulResponse(false);
                        requestContext.setResponseStatusCode(200);
                        response.getWriter().flush();
                        response.getWriter().close();
                        return null;
                    }
                } else {
                    String json = JSONObject.toJSONString(ErrorResponseData.error(UserErrorEnum.登录超时), SerializerFeature.WriteMapNullValue);
                    response.getWriter().write(json);
                    response.getWriter().flush();
                    response.getWriter().close();
                    requestContext.setSendZuulResponse(false);
                    requestContext.setResponseStatusCode(200);
                    return null;
                }
            }
        } catch (Exception e) {
            log.error("检验token发生异常", e);
            String json = JSONObject.toJSONString(ErrorResponseData.error(UserErrorEnum.未登录), SerializerFeature.WriteMapNullValue);
            try {
                e.printStackTrace();
                response.getWriter().write(json);
                response.getWriter().flush();
                response.getWriter().close();
                requestContext.setSendZuulResponse(false);
                requestContext.setResponseStatusCode(200);
                return null;
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 获取放行接口
     * @param request
     * @return
     */
    public boolean getRelease(HttpServletRequest request) {
        String uri = request.getRequestURI();
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%过滤器1111111111111111111111111="+uri);
      /*
        String str = redisTemplate.opsForValue().get(RedisConstants.API_RELEASE);
       if (StringUtils.isNotBlank(str)) {
            List<ApiReleaseVO> apiReleaseVOList = JSONObject.parseArray(str, ApiReleaseVO.class);
            for (ApiReleaseVO apiReleaseVO : apiReleaseVOList) {
                if (FilterTypeEnum.token过滤器.equals(apiReleaseVO.getType())) {
                    if (uri.contains(apiReleaseVO.getUri())) {
                        return true;
                    }
                }
            }
        }*/
        return true;
//        if (uri.contains("api-docs") || uri.contains("register") || uri.contains("sendSms") || uri.contains("getUserStateInformation") || uri.contains("cxnn/index") ||
//                uri.contains("isRegisterByMobile") || uri.contains("user/file") || uri.contains("wechatpay/notify") || uri.contains("wechatpay/tradeQuery") ||
//                uri.contains("alipay/alipayNotify") || uri.contains("alipay/tradeQuery") ||
//                uri.contains("chinaums/notify") || uri.contains("chinaums/tradeQuery") ||
//                uri.contains("user/check") || uri.contains("getJsSdkSignature") ||
//                uri.contains("advertisement/pageList") || uri.contains("advertisement/projectList") ||
//                uri.contains("bindingPhone")) {
//            return true;
//        }
//
//        //放行登录
//        if (uri.contains("Login") || uri.contains("login")) {
//            return true;
//        }
//
//        //放行广告接口
//        if (uri.contains("getAdvertisementsList") || uri.contains("merchantRecommendation")) {
//            return true;
//        }
//
//
//        //停车业务相关不过滤的
//        //可以考虑只接收来自指定的白名单ip的信号上报
//        if (uri.contains("wentong") || uri.contains("anonymousRecord") || uri.contains("findParkingRecordList") || uri.contains("deviceDataLogReport")) {
//            return true;
//        }
//
//        //计费系统不过滤
//        if (uri.contains("/chargeFee")) {
//            return true;
//        }
//
//        //推送模块不过滤
//        if (uri.contains("pushMessage")) {
//            return true;
//        }
//
//        //测试
//        if (uri.contains("test")) {
//            return true;
//        }
//
//        //查找附近商家、停车场
//        if (uri.contains("/user/seller/findNearby") || uri.contains("/parking/parkingLot/findNearby") || uri.contains("/parking/parkingLot/findNearbyById") || uri.contains("/user/seller/findNearbyById")) {
//            return true;
//        }
    }

    //接口需求 传token时可获取用户信息 不传时放行
    public boolean getChoiceRelease(HttpServletRequest request) {
        String uri = request.getRequestURI();
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%过滤器222222222222222222222="+uri);

        //查询附近服务
       if (uri.contains("login") ) {
            return true;
        }

       /* if (uri.contains("login") || uri.contains("findSellerById") || uri.contains("findNearbyParkingLotByParkingLotType") ||
                uri.contains("findNearbyParkingLotList") || uri.contains("getParkingLotById") || uri.contains("list") || uri.contains("findParkingLotById") ||
                uri.contains("findNearbySeller")) {
            return true;
        }*/

      /**  //服务聚合不过滤的接口
        if (uri.contains("mergeserver/parking/findNearbyParkingLotByParkingLotType")) {
            return true;
        }
*/
        return false;

    }
}
