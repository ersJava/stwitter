package com.company.stwitter.service;

import com.company.stwitter.util.feign.CommentClient;
import com.company.stwitter.util.feign.PostClient;
import com.company.stwitter.viewmodel.CommentViewModel;
import com.company.stwitter.viewmodel.PostViewModel;
import org.junit.Before;
import org.junit.Test;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@Component
public class ServiceLayerTest {

    ServiceLayer sl;

    PostClient postClient;
    CommentClient commentClient;

    @Before
    public void setUp() throws Exception {

        setUpPostClientMock();
        setUpCommentClientMock();

        sl = new ServiceLayer(commentClient, postClient);
    }

    private void setUpPostClientMock(){

        postClient = mock(PostClient.class);

        PostViewModel post = new PostViewModel();
        post.setPostId(1);
        post.setPostDate(LocalDate.of(2019, 7, 15));
        post.setPosterName("Beth");
        post.setPostContent("Today is the first day our Java Bootcamp!");

        PostViewModel post1 = new PostViewModel();
        post1.setPostDate(LocalDate.of(2019, 7, 15));
        post1.setPosterName("Beth");
        post1.setPostContent("Today is the first day our Java Bootcamp!");

        // mock createPost
        doReturn(post).when(postClient).createPost(post1);

        // mock retrievePost
        doReturn(post).when(postClient).getPost(1);

        // mock find Post with comments
        PostViewModel post2 = new PostViewModel();
        post2.setPostId(2);
        post2.setPostDate(LocalDate.of(2019, 7, 22));
        post2.setPosterName("Beth");
        post2.setPostContent("Excited to meet everyone");

        CommentViewModel comment = new CommentViewModel();
        comment.setCommentId(10);
        comment.setPostId(2);
        comment.setCreateDate(LocalDate.of(2019, 7, 22 ));
        comment.setCommenterName("Patricia");
        comment.setComment("Looking forward to being apart of a great Cohort");

        List<CommentViewModel> cvmList = new ArrayList<>();
        cvmList.add(comment);
        post2.setComments(cvmList);

        List<PostViewModel> postList = new ArrayList<>();
        postList.add(post);

        // mock retrieve Post with comments
        doReturn(post2).when(postClient).getPost(2);

        // mock retrieveByUser
        doReturn(postList).when(postClient).getPostsByUser("Beth");

    }

    @Test
    public void createRetrievePost() {

        PostViewModel pvm = new PostViewModel();
        pvm.setPostDate(LocalDate.of(2019, 7, 15));
        pvm.setPosterName("Beth");
        pvm.setPostContent("Today is the first day our Java Bootcamp!");

        pvm = postClient.createPost(pvm);

        PostViewModel pvm2 = postClient.getPost(pvm.getPostId());

        assertEquals(pvm, pvm2);
    }

    @Test
    public void retrievePostWithComments() {

        PostViewModel post2 = new PostViewModel();
        post2.setPostId(2);
        post2.setPostDate(LocalDate.of(2019, 7, 22));
        post2.setPosterName("Beth");
        post2.setPostContent("Excited to meet everyone");

        CommentViewModel comment = new CommentViewModel();
        comment.setCommentId(10);
        comment.setPostId(2);
        comment.setCreateDate(LocalDate.of(2019, 7, 22 ));
        comment.setCommenterName("Patricia");
        comment.setComment("Looking forward to being apart of a great Cohort");

        List<CommentViewModel> cvmList = new ArrayList<>();
        cvmList.add(comment);
        post2.setComments(cvmList);

        PostViewModel pvm2 = postClient.getPost(2);
        assertEquals(pvm2, post2);


    }

    @Test
    public void retrievePostsByUser() {

        List<PostViewModel> pvmByUserList = postClient.getPostsByUser("Beth");
        assertEquals(1, pvmByUserList.size());
    }

    // ***** Comments *****
    private void setUpCommentClientMock() {

        commentClient = mock(CommentClient.class);
        //postClient = mock(PostClient.class);

        PostViewModel post = new PostViewModel();
        post.setPostId(1);
        post.setPostDate(LocalDate.of(2019, 7, 15));
        post.setPosterName("Beth");
        post.setPostContent("Today is the first day our Java Bootcamp!");

        CommentViewModel comment = new CommentViewModel();
        comment.setCommentId(1);
        comment.setPostId(10);
        comment.setCreateDate(LocalDate.of(2019, 7, 15 ));
        comment.setCommenterName("Sandy");
        comment.setComment("I'm excited to start the Bootcamp too!");

        CommentViewModel comment1 = new CommentViewModel();
        comment1.setPostId(10);
        comment1.setCreateDate(LocalDate.of(2019, 7, 15 ));
        comment1.setCommenterName("Sandy");
        comment1.setComment("I'm excited to start the Bootcamp too!");

        // mock addComment
        doReturn(comment).when(commentClient).createComment(comment1);

        List<CommentViewModel> commentList = new ArrayList<>();
        commentList.add(comment);

        post.setComments(commentList);

    }


    @Test
    public void addComment() {

        CommentViewModel cvm = new CommentViewModel();
        cvm.setPostId(10);
        cvm.setCreateDate(LocalDate.of(2019, 7, 15 ));
        cvm.setCommenterName("Sandy");
        cvm.setComment("I'm excited to start the Bootcamp too!");

        cvm = commentClient.createComment(cvm);

        List<CommentViewModel> lc = new ArrayList<>();
        lc.add(cvm);

        assertEquals(lc.size(), 1);

    }

}