package com.ll.finalProject.post.service;

import com.ll.finalProject.base.exception.DataNotFoundException;
import com.ll.finalProject.base.exception.NoAuthorizationException;
import com.ll.finalProject.member.dto.MemberDto;
import com.ll.finalProject.member.entity.Member;
import com.ll.finalProject.member.repository.MemberRepository;
import com.ll.finalProject.post.dto.PostDto;
import com.ll.finalProject.post.entity.Post;
import com.ll.finalProject.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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

    public List<PostDto> getAllPostList() {
        List<Post> posts = postRepository.findAll();
        List<PostDto> postDtos = new ArrayList<>();

        for (Post post : posts) {
            MemberDto memberDto = modelMapper.map(post.getMember(), MemberDto.class);
            PostDto postDto = modelMapper.map(post, PostDto.class);
            postDto.setMemberDto(memberDto);
            postDtos.add(postDto);
        }

        return postDtos;
    }

    public List<PostDto> get100PostList() {
        List<Post> posts = postRepository.findTop100ByOrderByModifyDateDesc();
        List<PostDto> postDtos = new ArrayList<>();

        for (Post post : posts) {
            MemberDto memberDto = modelMapper.map(post.getMember(), MemberDto.class);
            PostDto postDto = modelMapper.map(post, PostDto.class);
            postDto.setMemberDto(memberDto);
            postDtos.add(postDto);
        }

        return postDtos;
    }

    public PostDto modifyPost(Long memberId, Long postId, String subject, String content) {
        Optional<Post> _post = postRepository.findById(postId);

        if (_post.isEmpty()) {
            throw new DataNotFoundException("글이 존재하지 않습니다.");
        }

        Optional<Member> _member = memberRepository.findById(memberId);
        Post post = _post.get();

        if (_member.get() != post.getMember()) {
            throw new NoAuthorizationException("해당 글의 수정 권한이 없습니다.");
        }

        post.modifyPost(subject, content);
        postRepository.save(post);

        return modelMapper.map(post, PostDto.class);
    }
}
