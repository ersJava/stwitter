package com.company.comment.dao;

import com.company.comment.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CommentDaoJdbcTemplateImpl implements CommentDao {

    private static final String INSERT_COMMENT_SQL =
            "insert into comment (post_id, create_date, commenter_name, comment) values (?, ?, ?, ?)";

    private static final String SELECT_COMMENT_SQL =
            "select * from comment where comment_id = ?";

    private static final String SELECT_ALL_COMMENTS_SQL =
            "select * from comment";

    private static final String DELETE_COMMENT_SQL =
            "delete from comment where comment_id = ?";

    private static final String UPDATE_COMMENT_SQL =
            "update comment set post_id = ?, create_date = ?, commenter_name = ?, comment = ? where comment_id =?";

    private static final String SELECT_COMMENT_BY_POSTID_SQL =
            "select * from comment where post_id = ?";

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public CommentDaoJdbcTemplateImpl (JdbcTemplate jdbcTemplate) {

        this.jdbcTemplate=jdbcTemplate;
    }

    private Comment mapRowToComment(ResultSet rs, int rowNum) throws SQLException {

        Comment comment = new Comment();
        comment.setCommentId(rs.getInt("comment_id"));
        comment.setPostId(rs.getInt("post_id"));
        comment.setCreateDate(rs.getDate("create_date").toLocalDate());
        comment.setCommenterName(rs.getString("commenter_name"));
        comment.setComment(rs.getString("comment"));

        return comment;
    }

    @Override
    public Comment addComment(Comment comment) {

        jdbcTemplate.update(INSERT_COMMENT_SQL,
                comment.getPostId(),
                comment.getCreateDate(),
                comment.getCommenterName(),
                comment.getComment());
        int id = jdbcTemplate.queryForObject("select last_insert_id()",Integer.class);

        comment.setCommentId(id);

        return comment;

    }

    @Override
    public Comment getComment(int id) {

        try {
            return jdbcTemplate.queryForObject(SELECT_COMMENT_SQL, this::mapRowToComment, id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }

    }

    @Override
    public List<Comment> getAllComments() {
        return jdbcTemplate.query(SELECT_ALL_COMMENTS_SQL, this::mapRowToComment);
    }

    @Override
    public void updateComment(Comment comment) {

        jdbcTemplate.update(UPDATE_COMMENT_SQL,
                comment.getPostId(),
                comment.getCreateDate(),
                comment.getCommenterName(),
                comment.getComment(),
                comment.getCommentId());
    }

    @Override
    public void deleteComment(int id) {

        jdbcTemplate.update(DELETE_COMMENT_SQL, id);
    }

    @Override
    public List<Comment> getCommentsByPostId(int id) {
        return jdbcTemplate.query(SELECT_COMMENT_BY_POSTID_SQL, this::mapRowToComment, id);
    }


}
