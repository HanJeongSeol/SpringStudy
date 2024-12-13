package com.hhplus.springstudy.dto.comment;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentRequestDto {
    
    @NotNull(message = "게시글 번호는 필수입니다.")
    private Long postNo;

    @NotNull(message = "댓글 내용은 필수입니다.")
    @Size(min = 1, max = 1000, message = "댓글 내용은 1자 이상 1000자 이하로 입력해야 합니다.")
    private String commentContent;

    private Long parentCommentNo;   // 대댓글인 경우에만 사용

}
