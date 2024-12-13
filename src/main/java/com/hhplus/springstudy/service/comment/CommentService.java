package com.hhplus.springstudy.service.comment;

import com.hhplus.springstudy.common.constant.ErrorCode;
import com.hhplus.springstudy.config.security.CustomPrincipal;
import com.hhplus.springstudy.domain.comment.Comment;
import com.hhplus.springstudy.domain.comment.CommentHierarchy;
import com.hhplus.springstudy.domain.post.Post;
import com.hhplus.springstudy.domain.user.User;
import com.hhplus.springstudy.dto.comment.CommentRequestDto;
import com.hhplus.springstudy.dto.comment.CommentResponseDto;
import com.hhplus.springstudy.exception.BusinessException;
import com.hhplus.springstudy.repository.comment.CommentHierarchyRepository;
import com.hhplus.springstudy.repository.comment.CommentRepository;
import com.hhplus.springstudy.repository.post.PostRepository;
import com.hhplus.springstudy.repository.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor    
public class CommentService {
    private final CommentRepository commentRepository;
    private final CommentHierarchyRepository commentHierarchyRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    

    /**
     * 댓글 작성
     * 작성자 정보는 JWT 필터에서 등록한 SpringSecurityContextHolder에서 조회
     */
    @Transactional
    public CommentResponseDto registerComment(CommentRequestDto requestDto, String userId) {
        // 1. JWT 필터에서 등록한 SpringSecurityContextHolder에서 사용자 정보 조회
        User user = userRepository.findByUserId(userId)
        .orElseThrow(() -> new BusinessException(ErrorCode.USER_ID_NOT_FOUND));

        // 2. 게시글 조회
        Post post = postRepository.findById(requestDto.getPostNo())
            .filter(p->p.getDeleteAt().equals(0))
            .orElseThrow(() -> new BusinessException(ErrorCode.POST_ENTITY_NOT_FOUND));

        // 3. 댓글 생성
        Comment comment = Comment.create(requestDto.getCommentContent(), post, user);
        Comment savedComment = commentRepository.save(comment);

        // 4. 댓글 계층 생성
        CommentHierarchy hierarchy;
        List<CommentResponseDto> replies = new ArrayList<>();

        if(requestDto.getParentCommentNo() != null){
            // 대댓글인 경우
            Comment parentComment = commentRepository.findById(requestDto.getParentCommentNo())
            .filter(c -> c.getDeleteAt().equals(0))
            .orElseThrow(() -> new BusinessException(ErrorCode.COMMENT_NOT_FOUND));

            CommentHierarchy parentHierarchy = commentHierarchyRepository.findByCommentNo(parentComment)
            .orElseThrow(() -> new BusinessException(ErrorCode.COMMENT_HIERARCHY_NOT_FOUND));

            hierarchy = CommentHierarchy.createReply(savedComment, parentComment, parentHierarchy.getCommentDepth());
        } else{
            // 루트 댓글인 경우
            hierarchy = CommentHierarchy.createRoot(savedComment);
        }

        CommentHierarchy savedHierarchy = commentHierarchyRepository.save(hierarchy);

        return CommentMapper.toResponseDto(savedComment, savedHierarchy, replies); 
    }
}

