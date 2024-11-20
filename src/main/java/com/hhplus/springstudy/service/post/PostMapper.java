package com.hhplus.springstudy.service.post;

import com.hhplus.springstudy.domain.post.Post;
import com.hhplus.springstudy.dto.post.PostListResponseDto;
import com.hhplus.springstudy.dto.post.PostResponseDto;

public class PostMapper {
    // 단일 게시글 변환
    public static PostResponseDto toResponseDto(Post post) {
        return PostResponseDto.builder()
                .postNo(post.getPostNo())
                .postTitle(post.getPostTitle())
                .postContent(post.getPostContent())
                .createdDate(post.getCreatedDate())
                .modifiedDate(post.getModifiedDate())
                .deleteAt(post.getDeleteAt())
                .userName(post.getUser().getUserName())
                .build();
    }

    // 모든 게시글 리스트 변환
    public static PostListResponseDto toListResponseDto(Post post) {
        return PostListResponseDto.builder()
                .postNo(post.getPostNo())
                .postTitle(post.getPostTitle())
                .postAuthor(post.getUser().getUserName())
                .postContent(post.getPostContent())
                .createdDate(post.getCreatedDate())
                .deleteAt(post.getDeleteAt())
                .build();
    }
}
