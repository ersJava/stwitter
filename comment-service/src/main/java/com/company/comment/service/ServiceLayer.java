package com.company.comment.service;

import com.company.comment.dao.CommentDao;
import com.company.comment.model.Comment;
import com.company.comment.viewmodel.CommentViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Component
public class ServiceLayer {

    CommentDao dao;

    @Autowired
    protected ServiceLayer(CommentDao dao) {
        this.dao = dao;
    }

    private CommentViewModel buildCommentViewModel(Comment commment) {

        CommentViewModel cvm = new CommentViewModel();

        cvm.setCommentId(commment.getCommentId());
        cvm.setPostId(commment.getPostId());
        cvm.setCreateDate(commment.getCreateDate());
        cvm.setCommenterName(commment.getCommenterName());
        cvm.setComment(commment.getComment());

        return cvm;
    }

    @Transactional
    public CommentViewModel saveComment(CommentViewModel cvm) {

        Comment comment = new Comment();
        comment.setPostId(cvm.getPostId());
        comment.setCreateDate(cvm.getCreateDate());
        comment.setCommenterName(cvm.getCommenterName());
        comment.setComment(cvm.getComment());

        comment = dao.addComment(comment);
        cvm.setCommentId(comment.getCommentId());

        return cvm;
    }

    public List<CommentViewModel> findAllComments() {

        List<Comment> commentList = dao.getAllComments();

        List<CommentViewModel> cvmList = new ArrayList<>();

        for (Comment c : commentList) {
            CommentViewModel cvm = buildCommentViewModel(c);
            cvmList.add(cvm);
        }
        return cvmList;
    }

    public CommentViewModel findComment(int id) {

        Comment comment = dao.getComment(id);

        if (comment == null)
            throw new NoSuchElementException(String.format("No Comment with id %s found", id));
        else
            return buildCommentViewModel(comment);

    }

    public void removeComment(int id) {

        Comment comment = dao.getComment(id);

        if (comment == null)
            throw new NoSuchElementException(String.format("No Comment with id %s found", id));
        else
            dao.deleteComment(id);
    }

    public void updateComment(CommentViewModel cvm, int id) {

        if(id != cvm.getCommentId()) {
            throw new IllegalArgumentException(String.format("No Comment with id %s found", id));
        }

        Comment comment = new Comment();
        comment.setCommentId(cvm.getCommentId());
        comment.setPostId(cvm.getPostId());
        comment.setCreateDate(cvm.getCreateDate());
        comment.setCommenterName(cvm.getCommenterName());
        comment.setComment(cvm.getComment());

        dao.updateComment(comment);
    }

    public List<CommentViewModel> findAllCommentsByPostId(int id) {

        List<Comment> commentList = dao.getCommentsByPostId(id);

        List<CommentViewModel> commentViewModelList = new ArrayList<>();

        for(Comment c : commentList) {
            CommentViewModel cvm = buildCommentViewModel(c);
            commentViewModelList.add(cvm);
        }

        return commentViewModelList;
    }

}
