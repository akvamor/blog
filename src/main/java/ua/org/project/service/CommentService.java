package ua.org.project.service;

import ua.org.project.domain.impl.Comment;

import java.util.List;

/**
 * Created by Dmitry Petrov on 5/28/14.
 */
public interface CommentService {
    public Comment findById(Long id);
    public List<Comment> findByEntryId(Long entryId);
    public Comment save(Comment comment);
    public void delete(Comment comment);
    public List<String> findReplyToByEntryId(Long entryId);
}
