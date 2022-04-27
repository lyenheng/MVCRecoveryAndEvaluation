package com.ly.mvc_recovery_evaluation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author liuyue
 * @date 2022/4/7 14:31
 */
@SpringBootApplication
@EnableSwagger2
public class Application {
    public static void main(String[] args) {
        new SpringApplication(Application.class).run(args);
    }
}
