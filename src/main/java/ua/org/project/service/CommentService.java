package ua.org.project.service;

import ua.org.project.domain.impl.Comment;
import java.util.List;

/**
 * Created by Dmitry Petrov on 5/28/14.
 */
public interface CommentService {
    Comment findById(Long id);
    List<Comment> findByEntryId(Long entryId);
    List<Comment> findByEntryIdInTree(Long entryId);
    Comment save(Comment comment);
    void delete(Comment comment);
}
