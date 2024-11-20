package com.hhplus.springstudy.domain.role;

import com.hhplus.springstudy.domain.common.BaseTimeEntity;
import com.hhplus.springstudy.domain.user_role.UserRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Role extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("고유 아이디")
    private Long roleNo;

    @Column(nullable = false, unique = true)
    @Comment("권한 아이디")
    private String roleId;

    @Column(nullable = false)
    @Comment("권한명")
    private String roleName;

    @Column(nullable = false)
    @Comment("권한 내용")
    private String roleContent;

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserRole> userRoles;

    public Role(String roleId, String roleName, String roleContent){
        this.roleId = roleId;
        this.roleName = roleName;
        this.roleContent = roleContent;
    }
}
