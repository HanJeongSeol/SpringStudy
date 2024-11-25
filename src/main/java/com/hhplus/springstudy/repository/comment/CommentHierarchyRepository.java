package com.hhplus.springstudy.repository.comment;

import com.hhplus.springstudy.domain.comment.CommentHierarchy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentHierarchyRepository extends JpaRepository<CommentHierarchy, Long> {
    // 부모 댓글로 대댓글 조회
    List<CommentHierarchy> findByParentCommentNo_CommentNo(Long parentCommentNo);
}
