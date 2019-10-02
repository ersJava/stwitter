package com.company.comment.controller;

import com.company.comment.exception.NotFoundException;
import com.company.comment.service.ServiceLayer;
import com.company.comment.viewmodel.CommentViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RequestMapping("/comments")
@RestController
@RefreshScope
public class CommentController {

    @Autowired
    private ServiceLayer sl;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommentViewModel createComment(@Valid @RequestBody CommentViewModel comment) {

        return sl.saveComment(comment);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CommentViewModel getComment(@PathVariable int id) {

        CommentViewModel comment = sl.findComment(id);

        if(comment == null){
            throw new NotFoundException(String.format("No Comment with id %s found", id));
        }
        return sl.findComment(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CommentViewModel> getAllComments() {

        return sl.findAllComments();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateComment(@Valid @RequestBody CommentViewModel comment, @PathVariable int id) {

        sl.updateComment(comment, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable int id) {

        CommentViewModel comment = sl.findComment(id);

        if(comment == null){
            throw new NotFoundException(String.format("No Comment with id %s found", id));
        }
        sl.removeComment(id);
    }

    @GetMapping("/posts/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<CommentViewModel> getCommentsByPostId(@PathVariable int id) {

        return sl.findAllCommentsByPostId(id);
    }
}
