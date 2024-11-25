package com.hhplus.springstudy.domain.comment;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class CommentHierarchy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.hibernate.annotations.Comment("고유 아이디")
    private Long hierarchyNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_no", nullable = false)
    @org.hibernate.annotations.Comment("댓글 식별자(본인)")
    private Comment commentNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_comment_no")
    @org.hibernate.annotations.Comment("부모 댓글")
    private Comment parentCommentNo;        // Null인 경우 루트 댓글

    @Column(nullable = false)
    @org.hibernate.annotations.Comment("댓글 깊이")
    private Integer commentDepth;   // 댓글 깊이(0: 루트 댓글, 1 이상 : 대댓글)

    // 댓글 계층 저장을 위한 생성자

    public CommentHierarchy(Comment commentNo, Comment parentCommentNo, Integer commentDepth) {
        this.commentNo = commentNo;
        this.parentCommentNo = parentCommentNo;
        this.commentDepth = commentDepth;
    }

    // 정적 팩토리 메서드
    // 기본 루트 댓글 설정
    public static CommentHierarchy createRoot(Comment comment){
        return new CommentHierarchy(comment, null, 0);
    }

    // 대댓글(댓글 계층 생성) 설정
    public static CommentHierarchy createReply(Comment comment, Comment parentCommentNo, Integer parentDepth){
        return new CommentHierarchy(comment, parentCommentNo, parentDepth + 1);
    }
}
