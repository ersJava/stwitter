package com.company.stwitter.util.feign;//package com.company.stwitter.util.feign;

import com.company.stwitter.viewmodel.CommentViewModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name="comments-service")
public interface CommentClient {

    @RequestMapping(value = "/comments", method = RequestMethod.POST)
    CommentViewModel createComment(@Valid @RequestBody CommentViewModel comment);

    @RequestMapping(value = "/comments/posts/{id}", method = RequestMethod.GET)
    List<CommentViewModel> getCommentsByPostId(@PathVariable int id);

}
