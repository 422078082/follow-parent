package com.follow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

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
