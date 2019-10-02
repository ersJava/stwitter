package com.company.comment.dao;

import com.company.comment.model.Comment;
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
public class CommentDaoJdbcTemplateImplTest {

    @Autowired
    protected CommentDao dao;

    @Before
    public void setUp() throws Exception {

        List<Comment> allComments = dao.getAllComments();
        allComments.stream().forEach(comment -> dao.deleteComment(comment.getCommentId()));
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void addGetDeleteComment() {

        Comment comment = new Comment();
        comment.setPostId(1);
        comment.setCreateDate(LocalDate.of(2019, 7, 15 ));
        comment.setCommenterName("Sandy");
        comment.setComment("I'm excited to start the Bootcamp too!");

        comment = dao.addComment(comment);

        Comment comment1 = dao.getComment(comment.getCommentId());

        assertEquals(comment, comment1);

        dao.deleteComment(comment.getCommentId());

        comment1 = dao.getComment(comment.getCommentId());

        assertNull(comment1);
    }

    @Test
    public void getAllComments() {

        Comment comment = new Comment();
        comment.setPostId(1);
        comment.setCreateDate(LocalDate.of(2019, 7, 15 ));
        comment.setCommenterName("Sandy");
        comment.setComment("I'm excited to start the Bootcamp too!");

        comment = dao.addComment(comment);

        List<Comment> allComments = dao.getAllComments();

        assertEquals(1, allComments.size());
    }

    @Test
    public void updateComment() {

        Comment comment = new Comment();
        comment.setPostId(1);
        comment.setCreateDate(LocalDate.of(2019, 7, 15 ));
        comment.setCommenterName("Sandy");
        comment.setComment("I'm excited to start the Bootcamp too!");

        comment = dao.addComment(comment);

        comment.setPostId(100);
        comment.setCreateDate(LocalDate.of(2019, 7, 16 ));
        comment.setCommenterName("UPDATED");
        comment.setComment("UPDATED");

        dao.updateComment(comment);

        Comment comment1 = dao.getComment(comment.getCommentId());
        assertEquals(comment, comment1);

    }

    @Test
    public void getCommentsByPostId() {

        Comment comment = new Comment();
        comment.setPostId(1);
        comment.setCreateDate(LocalDate.of(2019, 7, 15 ));
        comment.setCommenterName("Sandy");
        comment.setComment("I'm excited to start the Bootcamp too!");
        comment = dao.addComment(comment);

        List<Comment> commentList = dao.getCommentsByPostId(1);
        assertEquals(commentList.size(), 1);

        commentList = dao.getCommentsByPostId(9);
        assertEquals(commentList.size(), 0);
    }
}
