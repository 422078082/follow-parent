package com.follow;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;


/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableConfigServer
public class MyStartApplication_Config
{
    public static void main( String[] args )
    {

        SpringApplication.run(MyStartApplication_Config.class, args);
    }
}
