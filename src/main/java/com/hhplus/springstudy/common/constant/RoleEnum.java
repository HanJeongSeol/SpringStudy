package com.hhplus.springstudy.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
@Getter
@AllArgsConstructor
public enum RoleEnum {
    USER("ROLE_USER", "일반 사용자"),
    ADMIN("ROLE_ADMIN", "전체 관리자");

    private final String key;
    private final String title;

    public static RoleEnum findByKey(String key){
        for(RoleEnum role : values()){
            if(role.getKey().equals(key)){
                return role;
            }
        }
        return null;
    }
}
