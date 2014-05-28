package ua.org.project.service.jpa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ua.org.project.domain.impl.Comment;
import ua.org.project.repository.CommentRepository;
import ua.org.project.service.CommentService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by Dmitry Petrov on 5/28/14.
 */
public class CommentServiceImpl implements CommentService {

    final Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private CommentRepository commentRepository;

    @Transactional(readOnly = true)
    public Comment findById(Long id) {
        return commentRepository.findOne(id);
    }

    @Transactional(readOnly = true)
    public List<Comment> findByEntryId(Long entryId) {
        logger.info("Finding comments for entry with id {}", entryId);
        return commentRepository.findByEntryId(entryId);
    }

    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    public void delete(Comment comment) {
        commentRepository.delete(comment);
    }

    public List<String> findReplyToByEntryId(Long entryId) {
        TypedQuery<String> query = em.createNamedQuery("Comment.findReplyToByEntryId", String.class);
        query.setParameter("entryId", entryId);
        return query.getResultList();
    }
}
