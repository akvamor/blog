package ua.org.project.domain.impl;

import ua.org.project.domain.AbstractLike;

import javax.persistence.*;

/**
 * Created by Dmitry Petrov on 17.06.14.
 */

@Entity
@Table(name = "COMMENT_LIKE")
public class CommentLike extends AbstractLike {
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
