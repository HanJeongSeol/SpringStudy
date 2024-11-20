package com.hhplus.springstudy.controller.post;

import com.hhplus.springstudy.common.constant.SuccessCode;
import com.hhplus.springstudy.common.response.ApiResponse;
import com.hhplus.springstudy.domain.post.Post;
import com.hhplus.springstudy.dto.post.PostListResponseDto;
import com.hhplus.springstudy.dto.post.PostResponseDto;
import com.hhplus.springstudy.dto.post.PostSaveRequestDto;
import com.hhplus.springstudy.service.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;

    @PostMapping
    public ResponseEntity<ApiResponse<PostResponseDto>> registerPost(@RequestBody PostSaveRequestDto requestDto) {
        PostResponseDto responseDto = postService.registerPost(requestDto);
        return ResponseEntity.ok(ApiResponse.of(SuccessCode.BOARD_CREATE_SUCCESS, responseDto));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PostListResponseDto>>> findAllPosts() {
        List<PostListResponseDto> postList = postService.getAllPosts();
        return ResponseEntity.ok(ApiResponse.of(SuccessCode.BOARD_ALL_READ_SUCCESS, postList));
    }

    @GetMapping("/{postNo}")
    public ResponseEntity<ApiResponse<PostResponseDto>> findPost(@PathVariable("postNo") Long postNo) {
        PostResponseDto responseDto = postService.getPost(postNo);
        return ResponseEntity.ok(ApiResponse.of(SuccessCode.BOARD_READ_SUCCESS, responseDto));
    }
}
