package com.company.stwitter.service;

import com.company.stwitter.util.feign.CommentClient;
import com.company.stwitter.util.feign.PostClient;
import com.company.stwitter.viewmodel.CommentViewModel;
import com.company.stwitter.viewmodel.PostViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Component
public class ServiceLayer {

    private final CommentClient commentClient;
    private final PostClient postClient;

    @Autowired
    ServiceLayer(CommentClient commentClient, PostClient postClient) {
        this.commentClient = commentClient;
        this.postClient = postClient;
    }

    @Transactional
    public PostViewModel createPost(PostViewModel postViewModel) {

        // call postClient and returns PVM with id
        postViewModel = postClient.createPost(postViewModel);

        // sets Id to from pvm to postViewModel
        postViewModel.setPostId(postViewModel.getPostId());

        // returns pvm with id generated from postClient
        return postViewModel;
    }

    @Transactional
    public CommentViewModel addComment(CommentViewModel commentViewModel, int postId) {

        PostViewModel pvm = postClient.getPost(postId);

        if (pvm == null)
            throw new NoSuchElementException(String.format("No Post with id %s found", postId));
         else {

            // setting postId to match the comment to the desired post
             commentViewModel.setPostId(postId);

             // call commentClient and returns CVM with id
             commentViewModel = commentClient.createComment(commentViewModel);

             // set the id to cvm
             commentViewModel.setCommentId(commentViewModel.getCommentId());

        }

        return commentViewModel;
    }


    public PostViewModel retrievePost(int id) {

        PostViewModel pvm = postClient.getPost(id);

        if(pvm == null)
            throw new NoSuchElementException(String.format("No Post with id %s found", id));

        List<CommentViewModel> cvmList = commentClient.getCommentsByPostId(id);
        pvm.setComments(cvmList);

        return pvm;
    }

    public List<PostViewModel> retrievePostsByUser(String userName) {

        List<PostViewModel> pvmList = postClient.getPostsByUser(userName);

        if (pvmList != null && pvmList.size() == 0)
            throw new NoSuchElementException(String.format("No posts found by %s", userName));
        else
            return pvmList;
    }
}
