package com.hhplus.springstudy.domain.comment;

import com.hhplus.springstudy.domain.common.BaseTimeEntity;
import com.hhplus.springstudy.domain.post.Post;
import com.hhplus.springstudy.domain.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.hibernate.annotations.Comment("고유 아이디")
    private Long commentNo;

    @Column(nullable = false)
    @org.hibernate.annotations.Comment("댓글 내용")
    private String commentContent;

    @Column(nullable = false, columnDefinition = "tinyint default 0")
    private Integer deleteAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_no", nullable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_no", nullable = false)
    private User user;

    // DB에 저장하기 위한 생성자
    public Comment(String commentContent, Post post, User user) {
        this.commentContent = commentContent;
        this.post = post;
        this.user = user;
    }

    // 정적 팩토리 메서드 사용
    public static Comment create(String commentContent, Post post, User user){
        return new Comment(commentContent, post, user);
    }

    // 댓글 수정
    public void updateContent(String commentContent){
        this.commentContent = commentContent;
    }

    // 댓글 삭제
    public void deleteComment(Integer deleteType){
        this.deleteAt = deleteType;
    }
}
