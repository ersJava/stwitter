package com.company.comment.service;

import com.company.comment.dao.CommentDao;
import com.company.comment.dao.CommentDaoJdbcTemplateImpl;
import com.company.comment.model.Comment;
import com.company.comment.viewmodel.CommentViewModel;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class ServiceLayerTest {

    private ServiceLayer serviceLayer;

    private CommentDao dao;

    @Before
    public void setUp() throws Exception {

        setUpCommentDaoMock();

        serviceLayer = new ServiceLayer(dao);

    }

    private void setUpCommentDaoMock() {

        dao = mock(CommentDaoJdbcTemplateImpl.class);

        Comment comment = new Comment();
        comment.setCommentId(1);
        comment.setPostId(10);
        comment.setCreateDate(LocalDate.of(2019, 7, 15 ));
        comment.setCommenterName("Sandy");
        comment.setComment("I'm excited to start the Bootcamp too!");

        Comment comment1 = new Comment();
        comment1.setPostId(10);
        comment1.setCreateDate(LocalDate.of(2019, 7, 15 ));
        comment1.setCommenterName("Sandy");
        comment1.setComment("I'm excited to start the Bootcamp too!");

        // update Mock data
        Comment updateComment = new Comment();
        updateComment.setCommentId(2);
        updateComment.setPostId(20);
        updateComment.setCreateDate(LocalDate.of(2019, 7, 15 ));
        updateComment.setCommenterName("Alicia");
        updateComment.setComment("Looking forward to learning all about web development");

        List<Comment> commentList = new ArrayList<>();
        commentList.add(comment);

        // mock saveComment
        doReturn(comment).when(dao).addComment(comment1);

        // mock findComment
        doReturn(comment).when(dao).getComment(1);

        // mock findAllComments
        doReturn(commentList).when(dao).getAllComments();

        // mock removeComment
        doNothing().when(dao).deleteComment(3);
        doReturn(null).when(dao).getComment(3);

        // mock updateComment
        doNothing().when(dao).updateComment(updateComment);
        doReturn(updateComment).when(dao).getComment(2);

        // mock getCommentsByPostid
        List<Comment> commentsByPostId = new ArrayList<>();
        commentsByPostId.add(comment);
        doReturn(commentsByPostId).when(dao).getCommentsByPostId(10);

    }

    @Test
    public void saveFindComment() {

        CommentViewModel cvm = new CommentViewModel();
        cvm.setPostId(10);
        cvm.setCreateDate(LocalDate.of(2019, 7, 15 ));
        cvm.setCommenterName("Sandy");
        cvm.setComment("I'm excited to start the Bootcamp too!");

        cvm = serviceLayer.saveComment(cvm);

        CommentViewModel cvm2 = serviceLayer.findComment(cvm.getCommentId());

        assertEquals(cvm,cvm2);

    }

    @Test
    public void findAllComments() {

        List<CommentViewModel> commentList = serviceLayer.findAllComments();
        assertEquals(1, commentList.size());
    }

    @Test(expected = NoSuchElementException .class)
    public void removeComment() {

        serviceLayer.removeComment(3);
        CommentViewModel cvm = serviceLayer.findComment(3);

    }

    @Test
    public void updateComment() {

        CommentViewModel cvmUpdate = new CommentViewModel();

        Comment updateComment = new Comment();
        updateComment.setCommentId(2);
        updateComment.setPostId(20);
        updateComment.setCreateDate(LocalDate.of(2019, 7, 15 ));
        updateComment.setCommenterName("Alicia");
        updateComment.setComment("Looking forward to learning all about web development");

        cvmUpdate.setCommentId(updateComment.getCommentId());
        cvmUpdate.setPostId(updateComment.getPostId());
        cvmUpdate.setCreateDate(updateComment.getCreateDate());
        cvmUpdate.setCommenterName(updateComment.getCommenterName());
        cvmUpdate.setComment(updateComment.getComment());

        serviceLayer.updateComment(cvmUpdate, 2);

        CommentViewModel afterUpdate = serviceLayer.findComment(cvmUpdate.getCommentId());

        assertEquals(cvmUpdate, afterUpdate);
    }

    @Test
    public void findCommentByPostId() {

        List<CommentViewModel> commentsByPostId = serviceLayer.findAllCommentsByPostId(10);
        assertEquals(1, commentsByPostId.size());

        commentsByPostId = serviceLayer.findAllCommentsByPostId(99);
        assertEquals(commentsByPostId.size(), 0);
    }
}