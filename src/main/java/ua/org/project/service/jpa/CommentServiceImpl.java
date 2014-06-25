package ua.org.project.service.jpa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.org.project.domain.impl.Comment;
import ua.org.project.domain.rest.CommentRest;
import ua.org.project.repository.CommentLikeRepository;
import ua.org.project.repository.CommentRepository;
import ua.org.project.service.CommentService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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

    @Autowired
    private CommentLikeRepository commentLikeRepository;

    @Transactional(readOnly = true)
    public Comment findById(Long id) {
        return commentRepository.findOne(id);
    }

    @Transactional(readOnly = true)
    public List<Comment> findByEntryId(Long entryId) {
        logger.info("Finding comments for entry with id {}", entryId);
        List<Comment> comments = commentRepository.findByEntryId(entryId);
        this.setLikes(comments);
        return comments;
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
    public List<CommentRest> getCommentRestTree(Long entryId, String formatDate) {
        logger.info("Get Tree of comments by entryId: " + entryId + ", formatDate: " + formatDate);
        List<Comment> comments = commentRepository.findByEntryIdAndParent(entryId);
        this.setLikes(comments);
        List<CommentRest> parentList = new ArrayList<CommentRest>();
        for (Comment comment : comments) {
            if (comment.getParentComment() == null){
                CommentRest commentRest = new CommentRest(comment, formatDate);
                this.setChildren(comment.getChildComment(), commentRest, formatDate);
                parentList.add(commentRest);
            }
        }
        return parentList;
    }

    private void setChildren(Set<Comment> comments, CommentRest commentsJson, String formatDate){
        this.setLikes(comments);
        List<CommentRest> children = new ArrayList<CommentRest>();
        for (Comment comment : comments) {
            CommentRest commentRest = new CommentRest(comment, formatDate);
            children.add(commentRest);
            if (comment.getChildComment() != null){
                this.setChildren(comment.getChildComment(), commentRest, formatDate);
            }
        }
        commentsJson.setChildren(children);
    }

    private void setLikes(Collection<Comment> comments) {
        for (Comment comment : comments) {
            comment.setCountLikes(commentLikeRepository.countAllLike(comment.getId()));
            comment.setCountNotLikes(commentLikeRepository.countAllNotLike(comment.getId()));
        }
    }

}
