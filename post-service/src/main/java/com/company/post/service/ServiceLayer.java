package com.company.post.service;

import com.company.post.dao.PostDao;
import com.company.post.model.Post;
import com.company.post.viewmodel.PostViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Component
public class ServiceLayer {

    private PostDao dao;

    @Autowired
    protected ServiceLayer(PostDao dao) {
        this.dao = dao;
    }

    private PostViewModel buildPostViewModel(Post post) {

        PostViewModel pvm = new PostViewModel();
        pvm.setPostId(post.getPostId());
        pvm.setPostDate(post.getPostDate());
        pvm.setPosterName(post.getPosterName());
        pvm.setPostContent(post.getPost());

        return pvm;
    }

    @Transactional
    public PostViewModel savePost(PostViewModel pvm) {

        Post post = new Post();
        post.setPostDate(pvm.getPostDate());
        post.setPosterName(pvm.getPosterName());
        post.setPost(pvm.getPostContent());

        post = dao.addPost(post);
        pvm.setPostId(post.getPostId());

        return pvm;
    }

    public PostViewModel findPost(int id) {

        Post post = dao.getPost(id);

        if (post == null)
            throw new NoSuchElementException(String.format("No Post with id %s found", id));
        else
            return buildPostViewModel(post);
    }

    public List<PostViewModel> findAllPosts(){

        List<Post> postList = dao.getAllPosts();

        List<PostViewModel> pvmList = new ArrayList<>();

        for (Post p : postList) {
            PostViewModel pvm = buildPostViewModel(p);
            pvmList.add(pvm);
        }
        return pvmList;
    }

    public void removePost(int id) {

        Post post = dao.getPost(id);

        if (post == null)
            throw new NoSuchElementException(String.format("No Post with id %s found", id));
        else
            dao.deletePost(id);

    }

    @Transactional
    public void updatePost(PostViewModel pvm, int id) {

       if(id != pvm.getPostId()) {
           throw new IllegalArgumentException(String.format("No Post with id %s found", id));
       }

        Post post = new Post();
        post.setPostId(pvm.getPostId());
        post.setPostDate(pvm.getPostDate());
        post.setPosterName(pvm.getPosterName());
        post.setPost(pvm.getPostContent());

        dao.updatePost(post);
    }


    public List<PostViewModel> findPostByUser(String name) {

        List<Post> postList = dao.getPostsByUser(name);

        if (postList != null && postList.size() == 0)
            throw new NoSuchElementException(String.format("No posts found by %s", name));
        else {
            List<PostViewModel> pvmList = new ArrayList<>();

            for(Post p : postList){
                PostViewModel pvm = buildPostViewModel(p);
                pvmList.add(pvm);
            }
            return pvmList;
        }
    }

}
