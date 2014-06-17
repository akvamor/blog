package ua.org.project.util;

import org.exolab.castor.mapping.GeneralizedFieldHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import ua.org.project.domain.impl.Comment;
import ua.org.project.service.CommentService;

/**
 * Created by Dmitry Petrov on 17.06.14.
 */
public class CommentHandler extends GeneralizedFieldHandler {

    @Autowired
    private static CommentService commentService;

    @Override
    public Object convertUponGet(Object value) {
        Comment comment = (Comment) value;
        return comment.getId();
    }

    @Override
    public Object convertUponSet(Object value) {
        Long id = (Long) value;
        Comment comment = commentService.findById(id);
        return comment;
    }

    @Override
    public Class getFieldType() {
        return Comment.class;
    }
}
