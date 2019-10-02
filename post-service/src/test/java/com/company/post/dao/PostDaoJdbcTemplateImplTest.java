package com.company.post.dao;

import com.company.post.model.Post;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.util.List;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class PostDaoJdbcTemplateImplTest {
    @Autowired
    protected PostDao dao;

    @Before
    public void setUp() throws Exception {

        List<Post> allPosts = dao.getAllPosts();
        allPosts.stream().forEach(post -> dao.deletePost(post.getPostId()));
    }

    @After
    public void tearDown() throws Exception {
    }


    @Test
    public void addGetDeletePost() {

        Post post = new Post();
        post.setPostDate(LocalDate.of(2019, 7, 15));
        post.setPosterName("Beth");
        post.setPost("Today is the first day our Java Bootcamp!");

        post = dao.addPost(post);

        Post post1 = dao.getPost(post.getPostId());

        assertEquals(post, post1);

        dao.deletePost(post.getPostId());

        post1 = dao.getPost(post.getPostId());

        assertNull(post1);

    }

    @Test
    public void getAllPosts() {

        Post post = new Post();
        post.setPostDate(LocalDate.of(2019, 7, 15));
        post.setPosterName("Beth");
        post.setPost("Today is the first day our Java Bootcamp!");

        post = dao.addPost(post);

        List<Post> postList = dao.getAllPosts();

        assertEquals(1, postList.size());

    }

    @Test
    public void updatePost() {

        Post post = new Post();
        post.setPostDate(LocalDate.of(2019, 7, 15));
        post.setPosterName("Beth");
        post.setPost("Today is the first day our Java Bootcamp!");

        post = dao.addPost(post);

        post.setPostDate(LocalDate.of(2019, 7, 15));
        post.setPosterName("UPDATED");
        post.setPost("UPDATED");

        dao.updatePost(post);

        Post post1 = dao.getPost(post.getPostId());

        assertEquals(post, post1);
    }

    @Test
    public void getPostsByUser() {

        Post post = new Post();
        post.setPostDate(LocalDate.of(2019, 7, 15));
        post.setPosterName("Beth");
        post.setPost("Today is the first day our Java Bootcamp!");

        post = dao.addPost(post);

        List<Post> allPost = dao.getPostsByUser("Beth");
        assertEquals(allPost.size(),1);

    }
}
