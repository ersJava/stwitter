package com.company.post.service;

import com.company.post.dao.PostDao;
import com.company.post.dao.PostDaoJdbcTemplateImpl;
import com.company.post.model.Post;
import com.company.post.viewmodel.PostViewModel;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@Component
public class ServiceLayerTest {

    @Autowired
    private ServiceLayer serviceLayer;

    private PostDao dao;

    @Before
    public void setUp() throws Exception {

        setUpPostDaoMock();

        serviceLayer = new ServiceLayer(dao);
    }

    private void setUpPostDaoMock() {

        dao = mock(PostDaoJdbcTemplateImpl.class);

        Post post = new Post();
        post.setPostId(1);
        post.setPostDate(LocalDate.of(2019, 7, 15));
        post.setPosterName("Beth");
        post.setPost("Today is the first day our Java Bootcamp!");

        Post post1 = new Post();
        post1.setPostDate(LocalDate.of(2019, 7, 15));
        post1.setPosterName("Beth");
        post1.setPost("Today is the first day our Java Bootcamp!");

        // update Mock data
        Post updatePost = new Post();
        updatePost.setPostId(2);
        updatePost.setPostDate(LocalDate.of(2019, 7, 16));
        updatePost.setPosterName("Sandy");
        updatePost.setPost("Looking forward to learning about Java and Spring");

        List<Post> postList = new ArrayList<>();
        postList.add(post);

        // mock savePost
        doReturn(post).when(dao).addPost(post1);

        // mock findPost
        doReturn(post).when(dao).getPost(1);

        // mock findAllPosts
        doReturn(postList).when(dao).getAllPosts();

        // mock removePost
        doNothing().when(dao).deletePost(3);
        doReturn(null).when(dao).getPost(3);

        // mock updatePost
        doNothing().when(dao).updatePost(updatePost);
        doReturn(updatePost).when(dao).getPost(2);

        // mock findPostByUser
        doReturn(postList).when(dao).getPostsByUser("Beth");

    }

    @Test
    public void saveFindPost() {

        PostViewModel pvm = new PostViewModel();
        pvm.setPostDate(LocalDate.of(2019, 7, 15));
        pvm.setPosterName("Beth");
        pvm.setPostContent("Today is the first day our Java Bootcamp!");

        pvm = serviceLayer.savePost(pvm);

        PostViewModel pvm2 = serviceLayer.findPost(pvm.getPostId());

        assertEquals(pvm, pvm2);

    }

    @Test
    public void findAllPosts() {

        List<PostViewModel> postList = serviceLayer.findAllPosts();
        assertEquals(1, postList.size());
    }

    @Test(expected = NoSuchElementException.class)
    public void removePost() {

        serviceLayer.removePost(3);
        PostViewModel pvm = serviceLayer.findPost(3);
    }

    @Test
    public void updatePost() {

        PostViewModel pvmUpdate = new PostViewModel();

        Post updatePost = new Post();
        updatePost.setPostId(2);
        updatePost.setPostDate(LocalDate.of(2019, 7, 16));
        updatePost.setPosterName("Sandy");
        updatePost.setPost("Looking forward to learning about Java and Spring");

        pvmUpdate.setPostId(updatePost.getPostId());
        pvmUpdate.setPostDate(updatePost.getPostDate());
        pvmUpdate.setPosterName(updatePost.getPosterName());
        pvmUpdate.setPostContent(updatePost.getPost());

        serviceLayer.updatePost(pvmUpdate, 2);

        PostViewModel afterUpdate = serviceLayer.findPost(pvmUpdate.getPostId());

        assertEquals(pvmUpdate, afterUpdate);
    }


    @Test
    public void findPostByUser() {

        List<PostViewModel> pvmByUserList = serviceLayer.findPostByUser("Beth");
        assertEquals(1, pvmByUserList.size());
    }

}