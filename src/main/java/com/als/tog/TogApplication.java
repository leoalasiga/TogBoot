package com.als.tog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @description: TogBoot的启动类
 * @author: liujiajie
 * @date: 2024/2/21 11:15
 */
@SpringBootApplication
@MapperScan("com.als.tog.samples.mapper")
public class TogApplication {
    public static void main(String[] args) {
        SpringApplication.run(TogApplication.class,args);
    }
}
