package ua.org.project.service;

import ua.org.project.domain.impl.CommentAttachment;

/**
 * Created by Dmitry Petrov on 5/28/14.
 */
public interface CommentAttachmentService {
    CommentAttachment findById(Long id);
}
