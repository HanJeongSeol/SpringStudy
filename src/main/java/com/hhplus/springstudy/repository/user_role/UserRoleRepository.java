package com.hhplus.springstudy.repository.user_role;

import com.hhplus.springstudy.domain.user_role.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
}
