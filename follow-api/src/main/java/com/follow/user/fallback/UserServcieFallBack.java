package com.follow.user.fallback;

import com.follow.entity.User;
import com.follow.common.result.DefaultFallBack;
import com.follow.common.result.ResponseData;

import com.follow.user.api.UserServiceApi;

import org.springframework.stereotype.Component;


import java.util.List;

@Component
public class UserServcieFallBack implements UserServiceApi {


            @Override
            public ResponseData selectList() {
                return DefaultFallBack.defaultFallBack();
            }

            @Override
            public boolean insertUser(User user) {
                return false;
            }

            @Override
            public ResponseData getOneUser(String id) {
                 return DefaultFallBack.defaultFallBack();
            }

            @Override
            public ResponseData selectListPage(int pageNo , int pageSize) {
                return DefaultFallBack.defaultFallBack();
            }

           @Override
            public ResponseData login(String username, String password) {
                return DefaultFallBack.defaultFallBack();
            }

}
