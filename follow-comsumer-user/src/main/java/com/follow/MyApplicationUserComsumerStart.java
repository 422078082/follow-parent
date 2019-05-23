package com.follow;

import com.loadbalanced.MyRule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableEurekaClient
//@RibbonClient(name = "MICROSESPRINGCLOUD-USER",configuration = MyRule.class)
@EnableFeignClients(basePackages = {"com.follow.service.user.api"})
@ComponentScan("com.follow")
public class MyApplicationUserComsumerStart
{
    public static void main( String[] args )
    {

        SpringApplication.run(MyApplicationUserComsumerStart.class,args);
    }
}
