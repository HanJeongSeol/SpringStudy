package com.hhplus.springstudy.config;

import com.hhplus.springstudy.common.constant.RoleEnum;
import com.hhplus.springstudy.domain.role.Role;
import com.hhplus.springstudy.repository.role.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 애플리케이션 처음 실행 시, Role 테이블에 기본 테이터를 자동으로 추가하기 위한 클래스
 */
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;


    @Override
    public void run(String... args) throws Exception {
        for(RoleEnum roleEnum : RoleEnum.values()){
            roleRepository.findByRoleId(roleEnum.getId())
                    .orElseGet(() -> roleRepository.save(new Role(roleEnum.getId(), roleEnum.getName(), roleEnum.getContent())));
        }
    }
}
