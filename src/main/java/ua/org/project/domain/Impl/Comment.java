package ua.org.project.domain.impl;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.validator.constraints.NotEmpty;
import ua.org.project.domain.AbstractBlog;
import ua.org.project.domain.AbstractLike;
import ua.org.project.domain.Attachment;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Dmitry Petrov on 5/28/14.
 */
@Entity
@Audited
@Table(name = "comment")
public class Comment extends AbstractBlog implements Serializable {

    private Entry entry;
    private String body;
    private Comment parentComment;
    private String postBy;
    private Set<CommentLike> likes = new HashSet<CommentLike>();
    private Set<CommentAttachment> attachments = new HashSet<CommentAttachment>();

    public Comment() {
    }

    @JsonIgnore
    @NotEmpty(message = "{validation.posting.body.NotEmpty.message}")
    @Size(min = 10, max = 2000, message = "{validation.posting.body.Size.message}")
    @Column(name = "BODY")
    public String getBody() {
        return this.body;
    }

    @Override
    public void setBody(String body) {
        this.body = body;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ENTRY_ID")
    public Entry getEntry() {
        return entry;
    }

    public void setEntry(Entry entry) {
        this.entry = entry;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "comment", cascade = CascadeType.ALL)
    public Set<CommentLike> getLikes() {
        return likes;
    }

    public void setLikes(Set<CommentLike> likes) {
        this.likes = likes;
    }

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PARENT_COMMENT_ID")
    public Comment getParentComment() {
        return parentComment;
    }

    public void setParentComment(Comment parentComment) {
        this.parentComment = parentComment;
    }

    @Column(name = "POST_BY")
    public String getPostBy() {
        return postBy;
    }

    public void setPostBy(String postBy) {
        this.postBy = postBy;
    }

    @NotAudited
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "comment", cascade = CascadeType.ALL)
    @OrderBy("id DESC ")
    public Set<CommentAttachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(Set<CommentAttachment> attachments) {
        this.attachments = attachments;
    }

    public void addAttachment(CommentAttachment attachment) {
        getAttachments().add(attachment);
    }

    @Transient
    public Set<Attachment> getAttachmentAbstract() {
        Set<Attachment> attach = new HashSet<Attachment>(this.attachments);
        return attach;
    }

    @Transient
    protected Set<AbstractLike> getLikesAbstract() {
        return new HashSet<AbstractLike>(this.likes);
    }
}
