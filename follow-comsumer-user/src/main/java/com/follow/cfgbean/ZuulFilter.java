package com.follow.cfgbean;

import com.follow.filter.TokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ZuulFilter {

    @Bean
    public TokenFilter gatewayTokenFilter() {
        return new TokenFilter();
    }

}
