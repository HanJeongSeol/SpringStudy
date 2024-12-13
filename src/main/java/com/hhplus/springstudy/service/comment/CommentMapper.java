package com.hhplus.springstudy.service.comment;

import java.util.List;

import com.hhplus.springstudy.domain.comment.Comment;
import com.hhplus.springstudy.domain.comment.CommentHierarchy;
import com.hhplus.springstudy.dto.comment.CommentResponseDto;

public class CommentMapper {
    public static CommentResponseDto toResponseDto(Comment comment, CommentHierarchy hierarchy, List<CommentResponseDto> replies) {
        return CommentResponseDto.builder()
            .commentNo(comment.getCommentNo())
            .commentContent(comment.getCommentContent())
            .userName(comment.getUser().getUserName())
            .createdDate(comment.getCreatedDate())
            .modifiedDate(comment.getModifiedDate())
            .commentDepth(hierarchy.getCommentDepth())
            .replies(replies)
            .build();
    }
}
