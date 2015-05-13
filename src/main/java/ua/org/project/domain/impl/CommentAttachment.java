package ua.org.project.domain.impl;

import ua.org.project.domain.Attachment;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Dmitry Petrov on 5/28/14.
 */
@Entity
@Table(name = "COMMENT_ATTACHMENT_DETAIL")
public class CommentAttachment extends Attachment implements Serializable {

	private static final long serialVersionUID = 7933780208237188366L;

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
