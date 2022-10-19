package com.ll.finalProject.post.service;

import com.ll.finalProject.base.exception.DataNotFoundException;
import com.ll.finalProject.member.entity.Member;
import com.ll.finalProject.member.repository.MemberRepository;
import com.ll.finalProject.post.dto.PostDto;
import com.ll.finalProject.post.entity.Post;
import com.ll.finalProject.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;

    public PostDto write(String username, String subject, String content) {
        Optional<Member> _member = memberRepository.findByusername(username);

        if (_member.isEmpty()) {
            throw new DataNotFoundException("회원이 존재하지 않습니다.");
        }

        Member member = _member.get();
        Post post = Post.builder()
                .subject(subject)
                .content(content)
                .member(member)
                .build();
        postRepository.save(post);
        return modelMapper.map(post, PostDto.class);
    }

    public PostDto findById(Long id) {
        Optional<Post> _post = postRepository.findById(id);

        if (_post.isEmpty()) {
            throw new DataNotFoundException("글이 존재하지 않습니다.");
        }

        Post post = _post.get();
        return modelMapper.map(post, PostDto.class);
    }
}
