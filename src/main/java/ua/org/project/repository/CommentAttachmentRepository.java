package ua.org.project.repository;

import org.springframework.data.repository.CrudRepository;
import ua.org.project.domain.impl.CommentAttachment;

/**
 * Created by Dmitry Petrov on 5/28/14.
 */
public interface CommentAttachmentRepository extends CrudRepository<CommentAttachment, Long> {
}
