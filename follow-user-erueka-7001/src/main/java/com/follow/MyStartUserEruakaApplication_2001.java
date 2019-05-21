package com.follow;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableEurekaServer
public class MyStartUserEruakaApplication_2001
{
    public static void main( String[] args )
    {

        SpringApplication.run(MyStartUserEruakaApplication_2001.class, args);
    }
}
