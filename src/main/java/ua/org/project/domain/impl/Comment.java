package ua.org.project.domain.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.NotEmpty;
import ua.org.project.domain.AbstractBlog;
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
@Table(name = "COMMENT")
public class Comment extends AbstractBlog implements Serializable {

	private static final long serialVersionUID = -4116615779286784867L;
	private Entry entry;
    private String body;
    private String postBy;
    private Set<Comment> childComment = new HashSet<Comment>();
    private Comment parentComment;
    private String parentUser;
    private Set<CommentAttachment> attachments = new HashSet<CommentAttachment>();

    public Comment() {

    }

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "COMMENT_TREE",
        joinColumns = @JoinColumn(name="PARENT_ID"),
        inverseJoinColumns = @JoinColumn(name = "CHILD_ID"))
    public Set<Comment> getChildComment() {
        return childComment;
    }
    public void setChildComment(Set<Comment> childComment) {
        this.childComment = childComment;
    }


    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinTable(name = "COMMENT_TREE",
            joinColumns = @JoinColumn(name="CHILD_ID"),
            inverseJoinColumns = @JoinColumn(name = "PARENT_ID"))
    public Comment getParentComment() {
        return parentComment;
    }
    public void setParentComment(Comment parentComment) {
        if (parentComment != null)
            this.parentUser = parentComment.getCreatedBy();
        this.parentComment = parentComment;
    }
    @Transient
    public String getParentUser() {
        return parentUser;
    }
    public void setParentUser(String parentUser) {
        this.parentUser = parentUser;
    }

    @NotEmpty(message = "{validation.comment.body.NotEmpty.message}")
    @Size(min = 2, max = 2000, message = "{validation.posting.body.Size.message}")
    @Column(name = "BODY")
    public String getBody() {
        return this.body;
    }
    public void setBody(String body) {
        this.body = body;
    }


    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ENTRY_ID")
    public Entry getEntry() {
        return entry;
    }
    public void setEntry(Entry entry) {
        this.entry = entry;
    }


    @Column(name = "POST_BY")
    public String getPostBy() {
        return postBy;
    }
    public void setPostBy(String postBy) {
        this.postBy = postBy;
    }

    @JsonIgnore
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

}
