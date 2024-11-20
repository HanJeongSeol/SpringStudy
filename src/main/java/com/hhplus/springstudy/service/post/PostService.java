package com.hhplus.springstudy.service.post;

import com.hhplus.springstudy.common.constant.ErrorCode;
import com.hhplus.springstudy.domain.post.Post;
import com.hhplus.springstudy.domain.user.User;
import com.hhplus.springstudy.dto.post.PostResponseDto;
import com.hhplus.springstudy.dto.post.PostSaveRequestDto;
import com.hhplus.springstudy.exception.BusinessException;
import com.hhplus.springstudy.repository.post.PostRepository;
import com.hhplus.springstudy.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Transactional
    public PostResponseDto registerPost(PostSaveRequestDto requestDto){
        User user = userRepository.findByUserId(requestDto.getUserId())
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_ID_NOT_FOUND));

        Post post = new Post();
        post.setPostTitle(requestDto.getPostTitle());
        post.setPostContent(requestDto.getPostContent());
        post.setUser(user);
        // @Column의 columnDefinition에 의해 기본 값으로 처리되지만, 유지보수를 위해 코드에 명시적으로 할당
        post.setDeleteAt(0);

        Post savePost = postRepository.save(post);
        return PostMapper.toResponseDto(savePost);
    }

}
