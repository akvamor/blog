package ua.org.project.domain.rest;

import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import ua.org.project.domain.impl.Comment;
import ua.org.project.domain.impl.Entry;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dmitry Petrov on 19.06.14.
 */
public class CommentRest implements Serializable {
    private Long id;
    private Long entryId;
    private Long parentId;
    private String replyTo;
    private String body;
    private String postDate;
    private String postBy;
    private Long countLikes;
    private Long countNotLikes;
    private List<CommentRest> children = new ArrayList<CommentRest>();

    public CommentRest(){}

    public CommentRest(Comment comment, String formatDate){
        id = comment.getId();
        Entry entry = comment.getEntry();
        if (entry != null) {
            entryId = entry.getId();
        }
        Comment pComment = comment.getParentComment();
        if (pComment != null) {
            replyTo = pComment.getCreatedBy();
        }
        body = comment.getBody();
        if (formatDate!= null && comment.getPostDate() != null) {
            DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(formatDate);
            postDate = dateTimeFormatter.print(comment.getPostDate());
        }

        this.countLikes = comment.getCountLikes();
        this.countNotLikes = comment.getCountNotLikes();

        postBy = comment.getPostBy();
        for (Comment child : comment.getChildComment()) {
            children.add(new CommentRest(child, formatDate));
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEntryId() {
        return entryId;
    }

    public void setEntryId(Long entryId) {
        this.entryId = entryId;
    }

    public String getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(String replyTo) {
        this.replyTo = replyTo;
    }

    @NotEmpty
    @Size(min = 10, max = 2000, message = "{validation.comment.body.Size.message}")
    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getPostDate() {
        return postDate;
    }

    public String getPostBy() {
        return postBy;
    }

    public void setPostBy(String postBy) {
        this.postBy = postBy;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public List<CommentRest> getChildren() {
        return children;
    }

    public void setChildren(List<CommentRest> children) {
        this.children = children;
    }

    public Long getCountLikes() {
        return countLikes;
    }

    public void setCountLikes(Long countLikes) {
        this.countLikes = countLikes;
    }

    public Long getCountNotLikes() {
        return countNotLikes;
    }

    public void setCountNotLikes(Long countNotLikes) {
        this.countNotLikes = countNotLikes;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}
