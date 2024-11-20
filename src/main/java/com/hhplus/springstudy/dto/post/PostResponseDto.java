package com.hhplus.springstudy.dto.post;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
public class PostResponseDto {
    private Long postNo;
    private String postTitle;
    private String postContent;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private Integer deleteAt;
    private String userName;

    public PostResponseDto(Long postNo, String postTitle, String postContent, LocalDateTime createdDate, LocalDateTime modifiedDate, Integer deleteAt, String userName) {
        this.postNo = postNo;
        this.postTitle = postTitle;
        this.postContent = postContent;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.deleteAt = deleteAt;
        this.userName = userName;
    }
}
