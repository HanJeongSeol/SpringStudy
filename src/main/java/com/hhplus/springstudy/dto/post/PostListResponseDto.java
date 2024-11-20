package com.hhplus.springstudy.dto.post;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PostListResponseDto {
    private Long postNo;
    private String postTitle;
    private String postAuthor;
    private String postContent;
    private LocalDateTime createdDate;
    private Integer deleteAt;

    public PostListResponseDto(Long postNo, String postTitle, String postAuthor, String postContent, LocalDateTime createdDate, Integer deleteAt) {
        this.postNo = postNo;
        this.postTitle = postTitle;
        this.postAuthor = postAuthor;
        this.postContent = postContent;
        this.createdDate = createdDate;
        this.deleteAt = deleteAt;
    }
}
