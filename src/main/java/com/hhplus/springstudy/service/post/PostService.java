package com.hhplus.springstudy.service.post;

import com.hhplus.springstudy.common.constant.ErrorCode;
import com.hhplus.springstudy.domain.post.Post;
import com.hhplus.springstudy.domain.user.User;
import com.hhplus.springstudy.dto.post.PostListResponseDto;
import com.hhplus.springstudy.dto.post.PostResponseDto;
import com.hhplus.springstudy.dto.post.PostSaveRequestDto;
import com.hhplus.springstudy.dto.post.PostUpdateRequestDto;
import com.hhplus.springstudy.exception.BusinessException;
import com.hhplus.springstudy.repository.post.PostRepository;
import com.hhplus.springstudy.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.hhplus.springstudy.service.post.PostMapper.toListResponseDto;
import static com.hhplus.springstudy.service.post.PostMapper.toResponseDto;

@Service
@RequiredArgsConstructor
public class PostService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Transactional
    public PostResponseDto registerPost(PostSaveRequestDto requestDto){
        User user = userRepository.findByUserId(requestDto.getUserId())
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_ID_NOT_FOUND));

        Post post = new Post(requestDto.getPostTitle(), requestDto.getPostContent(), user);

        Post savePost = postRepository.save(post);
        return toResponseDto(savePost);
    }

    @Transactional(readOnly = true)
    public List<PostListResponseDto> getAllPosts(){
        List<Post> posts = postRepository.findAll();
        return posts.stream()
                .filter(post -> post.getDeleteAt().equals(0))
                .map(post -> toListResponseDto(post))
                .toList();
    }

    @Transactional
    public PostResponseDto getPost(Long postNo){
        Post post = postRepository.findById(postNo)
                .filter(p -> p.getDeleteAt().equals(0))
                .orElseThrow(() -> new BusinessException(ErrorCode.POST_ENTITY_NOT_FOUND));

        return toResponseDto(post);
    }

    @Transactional
    public PostResponseDto updatePost(Long postNo, PostUpdateRequestDto requestDto) {
        // 게시글 조회
        Post post = postRepository.findById(postNo)
                .filter(p -> p.getDeleteAt().equals(0))
                .orElseThrow(() -> new BusinessException(ErrorCode.POST_ENTITY_NOT_FOUND));

        // 게시글 작성자 확인
        User user = post.getUser();
        if (!user.getUserId().equals(requestDto.getUserId())) {
            throw new BusinessException(ErrorCode.USER_ID_UNAUTHORIZED);
        }

        // 비밀번호 확인
        if (!user.getUserPassword().equals(requestDto.getUserPassword())) {
            throw new BusinessException(ErrorCode.USER_PASSWORD_UNAUTHORIZED);
        }

        // 삭제된 게시글인지 확인
        if(post.getDeleteAt() == 1){
            throw new BusinessException(ErrorCode.POST_ENTITY_NOT_FOUND,"삭제된 게시글입니다.");
        }

        // 게시글 수정
        post.postUpdate(requestDto.getPostTitle(), requestDto.getPostContent());

        return toResponseDto(post);

        // 영속성 컨텍스트로 인하여 별도의 save 메서드가 필요 없다.
//        Post updatedPost = postRepository.save(post);
//
//        return PostMapper.toResponseDto(updatedPost);
    }

    @Transactional
    public void deletePost(Long postNo){
        Post post = postRepository.findById(postNo)
                .orElseThrow(() -> new BusinessException(ErrorCode.POST_ENTITY_NOT_FOUND));

        post.delete();
        postRepository.save(post);
    }

}
