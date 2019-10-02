package com.company.post.controller;

import com.company.post.exception.NotFoundException;
import com.company.post.service.ServiceLayer;
import com.company.post.viewmodel.PostViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/posts")
@RestController
@RefreshScope
public class PostController {

    @Autowired
    private ServiceLayer sl;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostViewModel createPost(@Valid @RequestBody PostViewModel post) {

        return sl.savePost(post);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PostViewModel getPost(@PathVariable int id) {

        PostViewModel post = sl.findPost(id);

        if(post == null){
            throw new NotFoundException(String.format("No Post with id %s found", id));
        }
        return sl.findPost(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePost(@Valid @RequestBody PostViewModel post, @PathVariable int id) {

        sl.updatePost(post, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePost(@PathVariable int id) {

        PostViewModel post = sl.findPost(id);

        if(post == null){
            throw new NotFoundException(String.format("No Post with id %s found", id));
        }

        sl.removePost(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PostViewModel> getAllPosts() {

        return sl.findAllPosts();
    }

    @GetMapping("/user/{posterName}")
    @ResponseStatus(HttpStatus.OK)
    public List<PostViewModel> getPostsByUser(@PathVariable String posterName) {

        return sl.findPostByUser(posterName);
    }

//    private List<String> greetingList = new ArrayList<>();
//    private Random rndGenerator = new Random();
//
//    public PostController() {
//
//        greetingList.add("HiYa!");
//        greetingList.add("Hello!!!");
//        greetingList.add("Howdy!");
//        greetingList.add("Greetings!");
//        greetingList.add("Hi!!!!!");
//    }
//
//    @RequestMapping(value = "/greeting", method = RequestMethod.GET)
//    public String getRandomGreeting() {
//
//        // select and return a random greeting
//        int whichGreeting = rndGenerator.nextInt(5);
//        return greetingList.get(whichGreeting);
//    }

}
