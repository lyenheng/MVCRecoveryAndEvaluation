package com.ly.mvc_recovery_evaluation;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.persistence.EntityManager;

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

    @Bean
    public JPAQueryFactory jpaQueryFactory(EntityManager entityManager){
        return new JPAQueryFactory(entityManager);
    }

}
