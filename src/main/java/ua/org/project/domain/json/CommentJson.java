package ua.org.project.domain.json;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import ua.org.project.domain.impl.Comment;
import ua.org.project.domain.impl.Entry;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dmitry Petrov on 19.06.14.
 */
public class CommentJson implements Serializable {
    private Long id;
    private Long entryId;
    private String replyTo;
    private String body;
    private String postDate;
    private String postBy;
    private List<CommentJson> children = new ArrayList<CommentJson>();

    public CommentJson(){}

    public CommentJson(Comment comment, String formatDate){
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
        for (Comment child : comment.getChildComment()) {
            children.add(new CommentJson(child, formatDate));
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

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public List<CommentJson> getChildren() {
        return children;
    }

    public void setChildren(List<CommentJson> children) {
        this.children = children;
    }
}
