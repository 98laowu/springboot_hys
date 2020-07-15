package com.athys.springboothysum;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@MapperScan("com.athys.springboothysum.dao")
@SpringBootApplication
public class SpringbootHysumApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootHysumApplication.class, args);
    }

}
