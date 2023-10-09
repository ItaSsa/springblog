package com.itainplace.springnblog.controller;

import com.itainplace.springnblog.dto.PostDto;
import com.itainplace.springnblog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/posts")
public class PostController {
        @Autowired
        PostService postService;
        @PostMapping
        public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto){
                return ResponseEntity.ok(postService.creatPost(postDto));
        }

        @GetMapping("/all")
        public ResponseEntity<Page<PostDto>> showAllPosts(Pageable pageable){
                return ResponseEntity.ok(postService.showAllPosts(pageable));
        }


        @GetMapping(value ="/{id}")
        public ResponseEntity<PostDto> showById(@PathVariable Long id){
                return ResponseEntity.ok(postService.showById(id));
        }

}
