package com.follow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableEurekaClient
public class MyApplicationUserComsumerStart
{
    public static void main( String[] args )
    {
        SpringApplication.run(MyApplicationUserComsumerStart.class,args);
    }
}
