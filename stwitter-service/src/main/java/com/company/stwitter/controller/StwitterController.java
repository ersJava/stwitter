package com.company.stwitter.controller;

import com.company.stwitter.service.ServiceLayer;
import com.company.stwitter.viewmodel.CommentViewModel;
import com.company.stwitter.viewmodel.PostViewModel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RefreshScope
@CacheConfig(cacheNames = {"posts"})
public class StwitterController {

    public static final String EXCHANGE = "comment-exchange";
    public static final String ROUTING_KEY = "comment.create.commentviewmodel.controller";

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    ServiceLayer service;

    public StwitterController(RabbitTemplate rabbitTemplate, ServiceLayer service) {
        this.rabbitTemplate = rabbitTemplate;
        this.service = service;
    }

    @RequestMapping(value = "/comments/{postId}", method = RequestMethod.POST)
    CommentViewModel addComment(@Valid @RequestBody CommentViewModel comment, @PathVariable int postId) {

        System.out.println("Sending CommentViewModel...");
        rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, comment);
        System.out.println("Comment created.");

        return service.addComment(comment, postId);
    }

    @CachePut(key = "#result.getPostId")
    @RequestMapping(value = "/posts", method = RequestMethod.POST)
    PostViewModel createPost(@Valid @RequestBody PostViewModel post) {
        System.out.println(" *-*-*-* CREATING NEW POST *-*-*-*  ");
        return service.createPost(post);
    }

    @Cacheable
    @RequestMapping(value = "/posts/{id}", method = RequestMethod.GET)
    PostViewModel getPost(@PathVariable int id) {
        System.out.println(" *-*-*-* GETTING POST ID = " + id + " *-*-*-* ");
        return service.retrievePost(id);
    }

    @Cacheable
    @RequestMapping(value = "posts/user/{posterName}", method = RequestMethod.GET)
    List<PostViewModel> getPostsByUser(@PathVariable String posterName) {
        System.out.println(" *-*-*-* GETTING POSTS BY = " + posterName + " *-*-*-* ");
        return service.retrievePostsByUser(posterName);
    }
}
