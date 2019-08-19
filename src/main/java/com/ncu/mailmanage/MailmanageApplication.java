package com.ncu.mailmanage;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.ncu.mailmanage.dao")
public class MailmanageApplication {

    public static void main(String[] args) {
        SpringApplication.run(MailmanageApplication.class, args);
    }

}
