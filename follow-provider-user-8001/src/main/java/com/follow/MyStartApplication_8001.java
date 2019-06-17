package com.follow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Hello world!
 *
 */
@SpringBootApplication(scanBasePackages = {"com.follow.user", "com.follow.common"})
@EnableEurekaClient
@EnableCircuitBreaker
@EnableFeignClients(basePackages = {"com.follow.user.api"})
public class MyStartApplication_8001
{
    public static void main( String[] args )
    {

        SpringApplication.run(MyStartApplication_8001.class, args);
    }
}
