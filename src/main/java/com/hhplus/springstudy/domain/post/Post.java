package com.hhplus.springstudy.domain.post;

import com.hhplus.springstudy.domain.common.BaseTimeEntity;
import com.hhplus.springstudy.domain.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Post extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("고유 아이디")
    private Long postNo;

    @Column(nullable = false)
    @Comment("게시글 제목")
    private String postTitle;

    @Comment("게시글 내용")
    private String postContent;

    @Column(nullable = false, columnDefinition = "tinyint default 0")
    private Integer deleteAt;   // 0 : 미삭제 | 1: 삭제

    @ManyToOne
    @JoinColumn(name = "user_no", nullable = false)
    private User user;

    public Post(String postTitle, String postContent, User user) {
        this.postTitle = postTitle;
        this.postContent = postContent;
        this.user = user;
        this.deleteAt = 0;
    }

    public void postUpdate(String postTitle, String postContent){
        this.postTitle = postTitle;
        this.postContent = postContent;
    }

    public void delete(){
        this.deleteAt = 1;
    }
}
