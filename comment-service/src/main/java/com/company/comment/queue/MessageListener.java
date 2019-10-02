package com.company.comment.queue;

import com.company.comment.CommentServiceApplication;
import com.company.comment.service.ServiceLayer;
import com.company.comment.viewmodel.CommentViewModel;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageListener {

    @Autowired
    protected ServiceLayer sl;

    @RabbitListener(queues = CommentServiceApplication.QUEUE_NAME)
    public void receiveComment(CommentViewModel comment) {

        try {
            CommentViewModel cvm = new CommentViewModel(
                    comment.getPostId(),
                    comment.getCreateDate(),
                    comment.getCommenterName(),
                    comment.getComment());
            sl.saveComment(cvm);
            System.out.println("Creating the following comment: " +comment.toString());

        } catch (Exception e) {
            System.out.println("Server error: " + e.getMessage());
        }
    }
}
