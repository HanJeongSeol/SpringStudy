package com.hhplus.springstudy.domain.user;

import com.hhplus.springstudy.domain.common.BaseTimeEntity;
import com.hhplus.springstudy.domain.post.Post;
import com.hhplus.springstudy.domain.role.Role;
import com.hhplus.springstudy.domain.user_role.UserRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("고유 아이디")
    private Long userNo;

    @Column(nullable = false, unique = true)
    @Comment("로그인 아이디")
    private String userId;

    @Column(nullable = false)
    @Comment("로그인 비밀번호")
    private String userPassword;

    @Column(nullable = false)
    @Comment("회원 이름")
    private String userName;

    // 연관관계 설정 -> 하나의 사용자는 여러게의 게시글 작성 가능
    @OneToMany(mappedBy = "user", cascade=CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts;

    // userRoles 필드가 null인 상태에서 addRole 메서드 호출 시 NullException이 발생할 수 있기 때문에 초기화 진행
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserRole> userRoles = new ArrayList<>();

    public void addRole(Role role){
        UserRole userRole = new UserRole();
        userRole.setUser(this);
        userRole.setRole(role);
        this.userRoles.add(userRole);
    }
}
