package ua.org.project.service.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.org.project.domain.impl.CommentAttachment;
import ua.org.project.repository.CommentAttachmentRepository;
import ua.org.project.service.CommentAttachmentService;

/**
 * Created by Dmitry Petrov on 5/28/14.
 */
@Service("commentAttachmentService")
@Repository
@Transactional
public class CommentAttachmentServiceImpl implements CommentAttachmentService {
    @Autowired
    private CommentAttachmentRepository repository;

    @Transactional(readOnly = true)
    public CommentAttachment findById(Long id) {
        return repository.findOne(id);
    }
}
