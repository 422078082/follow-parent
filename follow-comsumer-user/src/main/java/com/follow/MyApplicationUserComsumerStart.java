package com.follow;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.ComponentScan;


/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableEurekaClient
//@RibbonClient(name = "MICROSESPRINGCLOUD-USER",configuration = MyRule.class)
@EnableFeignClients(basePackages = {"com.follow.user"})
@EnableZuulProxy
public class MyApplicationUserComsumerStart
{
    public static void main( String[] args )
    {

        SpringApplication.run(MyApplicationUserComsumerStart.class,args);
    }
}
