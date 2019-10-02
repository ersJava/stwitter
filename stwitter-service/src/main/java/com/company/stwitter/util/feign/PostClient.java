package com.company.stwitter.util.feign;//package com.company.stwitter.util.feign;

import com.company.stwitter.viewmodel.PostViewModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name="posts-service")
public interface PostClient {

//    @RequestMapping(value = "/greeting", method = RequestMethod.GET)
//    String getRandomGreeting();

    // create Post
    @RequestMapping(value = "/posts", method = RequestMethod.POST)
    PostViewModel createPost(@Valid @RequestBody PostViewModel post) ;

    // get by Id
    @RequestMapping(value = "/posts/{id}", method = RequestMethod.GET)
    PostViewModel getPost(@PathVariable int id);

    // get by User
    @RequestMapping(value = "posts/user/{posterName}", method = RequestMethod.GET)
    List<PostViewModel> getPostsByUser(@PathVariable String posterName);

}
