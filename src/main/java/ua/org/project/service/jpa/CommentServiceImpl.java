package ua.org.project.service.jpa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.org.project.domain.impl.Comment;
import ua.org.project.domain.json.CommentJson;
import ua.org.project.repository.CommentRepository;
import ua.org.project.service.CommentService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * Created by Dmitry Petrov on 5/28/14.
 */
@Service("commentService")
@Repository
@Transactional
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
        if (comment.isNew()){
            //Todo add save to COMMENT_TREE
        }
        return commentRepository.save(comment);
    }

    public void delete(Comment comment) {
        commentRepository.delete(comment);
    }

    @Transactional(readOnly = true)
    public List<CommentJson> findTreeByEntryId(Long entryId, String formatDate) {
        List<Comment> comments = commentRepository.findByEntryId(entryId);
        List<CommentJson> parentList = new ArrayList<CommentJson>();
        for (Comment comment : comments) {
            if (comment.getParentComment() == null){
                CommentJson commentJson = new CommentJson(comment,formatDate);
                this.setChildren(comment.getChildComment(), commentJson, formatDate);
                parentList.add(commentJson);
            }
        }

        return parentList;
    }

    private void setChildren(Set<Comment> comments, CommentJson commentsJson, String formatDate){
        List<CommentJson> children = new ArrayList<CommentJson>();
        for (Comment comment : comments) {
            CommentJson commentJson = new CommentJson(comment, formatDate);
            children.add(commentJson);
            if (comment.getChildComment() != null){
                this.setChildren(comment.getChildComment(), commentJson, formatDate);
            }
        }
        commentsJson.setChildren(children);
    }

}
