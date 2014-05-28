package ua.org.project.domain.impl;

import ua.org.project.domain.Attachment;

import javax.persistence.*;

/**
 * Created by Dmitry Petrov on 5/28/14.
 */
@Entity
@Table(name = "COMMENT_ATTACHMENT_DETAIL")
public class CommentAttachment extends Attachment {

    private Comment comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMMENT_ID")
    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }
}
