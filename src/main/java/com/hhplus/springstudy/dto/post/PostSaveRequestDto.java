package com.hhplus.springstudy.dto.post;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostSaveRequestDto {
    private String postTitle;
    private String postContent;
    private String userId;
}
