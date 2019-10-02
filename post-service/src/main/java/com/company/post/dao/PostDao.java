package com.company.post.dao;

import com.company.post.model.Post;

import java.util.List;

public interface PostDao {

    Post addPost(Post post);

    Post getPost(int id);

    List<Post>getAllPosts();

    void updatePost(Post post);

    void deletePost(int id);

    List<Post>getPostsByUser(String user);
}
