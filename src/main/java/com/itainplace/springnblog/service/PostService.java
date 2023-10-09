package com.itainplace.springnblog.service;

import com.itainplace.springnblog.dto.PostDto;
import com.itainplace.springnblog.entities.Post;
import com.itainplace.springnblog.entities.User;
import com.itainplace.springnblog.exceptions.PostNotFoundException;
import com.itainplace.springnblog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Service
public class PostService {
    @Autowired
    PostRepository postRepository;

    @Autowired
    AuthService authService;
    @Transactional(readOnly = false)
    public PostDto creatPost( PostDto dto){
        Post post = new Post();
        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());
        post.setCreatedOn(Instant.now());

        User user = authService.getCurrentUser().orElseThrow(() ->
                new IllegalArgumentException("No user logged in"));

        post.setUsername(user.getUsername());

        postRepository.save(post);

        return new PostDto(post);
    }

    @Transactional(readOnly = true)
    public Page<PostDto> showAllPosts(Pageable pageable) {
        Page<Post> result = postRepository.findAll(pageable);
        return result.map(x->new PostDto(x));

    }

    @Transactional(readOnly = true)
    public PostDto showById(Long id) {
        Optional<Post>  result = postRepository.findById(id) ;
        Post post = result.orElseThrow(() -> new PostNotFoundException("Not found id: " + id));
        return new PostDto(post);
    }
}
