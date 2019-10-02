package com.company.comment.dao;

import com.company.comment.model.Comment;

import java.util.List;

public interface CommentDao {

    Comment addComment(Comment comment);

    Comment getComment(int id);

    List<Comment> getAllComments();

    void updateComment(Comment comment);

    void deleteComment(int id);

    List<Comment> getCommentsByPostId(int id);

}
