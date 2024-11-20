package com.hhplus.springstudy.domain.common;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@MappedSuperclass
@EntityListeners({AuditingEntityListener.class})
public class BaseTimeEntity {

    @CreatedDate
    @Comment("생성 일시")
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Comment("수정 일시")
    private LocalDateTime modifiedDate;
}
