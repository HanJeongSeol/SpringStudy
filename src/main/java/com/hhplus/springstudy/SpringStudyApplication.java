package com.hhplus.springstudy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

// 시큐리티 의존성 구현 전까지 사용 안함
//@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@EnableJpaAuditing
@SpringBootApplication
public class SpringStudyApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringStudyApplication.class, args);
    }

}
