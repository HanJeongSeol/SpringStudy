package com.hhplus.springstudy.controller.comment;

import com.hhplus.springstudy.common.constant.SuccessCode;
import com.hhplus.springstudy.common.response.ApiResponse;
import com.hhplus.springstudy.config.security.CustomPrincipal;
import com.hhplus.springstudy.dto.comment.CommentRequestDto;
import com.hhplus.springstudy.dto.comment.CommentResponseDto;
import com.hhplus.springstudy.service.comment.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<ApiResponse<CommentResponseDto>> registerComment(@Valid @RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal CustomPrincipal principal) {
        CommentResponseDto responseDto = commentService.registerComment(requestDto, principal.getUserId());
        return ResponseEntity.ok(ApiResponse.of(SuccessCode.BOARD_CREATE_SUCCESS, responseDto));
    }

    // 게시글의 모든 댓글 조회
    @GetMapping("/{postNo}")
    public ResponseEntity<ApiResponse<List<CommentResponseDto>>> findAllComments(@PathVariable("postNo") Long postNo) {
        List<CommentResponseDto> commentList = commentService.getAllCommentByPost(postNo);
        return ResponseEntity.ok(ApiResponse.of(SuccessCode.BOARD_ALL_READ_SUCCESS, commentList));
    }

}
