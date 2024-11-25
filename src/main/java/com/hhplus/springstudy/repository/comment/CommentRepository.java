package com.hhplus.springstudy.repository.comment;

import com.hhplus.springstudy.domain.comment.Comment;
import com.hhplus.springstudy.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    // 게시글 모든 댓글 조회
    List<Comment> findByPostAndDeleteAt(Post post, Integer deleteAt);
}
