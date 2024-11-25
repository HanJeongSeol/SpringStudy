package com.hhplus.springstudy.domain.user_role;

import com.hhplus.springstudy.domain.role.Role;
import com.hhplus.springstudy.domain.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Comment("user-role 중간 테이블")
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("고유 아이디")
    private Long userRoleNo;

    @ManyToOne
    @JoinColumn(name = "user_no", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "role_no", nullable = false)
    private Role role;
}
