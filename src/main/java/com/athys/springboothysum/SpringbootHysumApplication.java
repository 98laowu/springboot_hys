package com.athys.springboothysum;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@MapperScan("com.athys.springboothysum.dao")
@SpringBootApplication
@EnableSwagger2
public class SpringbootHysumApplication{
// extends SpringBootServletInitializer {
//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//        return builder.sources(SpringbootHysumApplication.class);
//    }

    public static void main(String[] args) {

        SpringApplication.run(SpringbootHysumApplication.class, args
        );
    }

}
