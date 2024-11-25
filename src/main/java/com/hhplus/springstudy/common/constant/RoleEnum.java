package com.hhplus.springstudy.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
@Getter
@AllArgsConstructor
public enum RoleEnum {
    USER("ROLE_USER", "일반 사용자", "기본 사용자 권한"),
    ADMIN("ROLE_ADMIN", "전체 관리자","전체 관리자 권한");

    private final String id;
    private final String name;
    private final String content;

    public static RoleEnum findById(String id){
        for(RoleEnum role : values()){
            if(role.getId().equals(id)){
                return role;
            }
        }
        return null;
    }
}
