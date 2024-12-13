package com.hhplus.springstudy.dto.comment;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class CommentResponseDto {
    private Long commentNo;
    private String commentContent;
    private String userName;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private Integer commentDepth;
    private List<CommentResponseDto> replies;


    public CommentResponseDto(Long commentNo, String commentContent, String userName, LocalDateTime createdDate, LocalDateTime modifiedDate, Integer commentDepth, List<CommentResponseDto> replies) {
        this.commentNo = commentNo;
        this.commentContent = commentContent;
        this.userName = userName;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.commentDepth = commentDepth;
        this.replies = replies;
    }   
}
