package com.jsure.datacenter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@EnableSwagger2
@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.jsure.datacenter.mapper")
public class DataWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataWebApplication.class, args);
    }

}