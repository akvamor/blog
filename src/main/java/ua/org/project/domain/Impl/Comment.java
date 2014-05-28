package ua.org.project.domain.impl;

import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import ua.org.project.domain.AbstractBlog;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Dmitry Petrov on 5/28/14.
 */
@Entity
@Audited
@Table(name = "comment")
@NamedQueries({
        @NamedQuery(
                name="Comment.findReplyToByEntryId",
                query = "SELECT distinct c.postBy FROM comment c WHERE c.entry.id = :entryId")
})
public class Comment extends AbstractBlog implements Serializable {

    private Entry entry;
    private String replyTo;
    private String postBy;
    private Set<CommentAttachment> attachments = new HashSet<CommentAttachment>();

    public  Comment(){}

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ENTRY_ID")
    public Entry getEntry() {
        return entry;
    }

    public void setEntry(Entry entry) {
        this.entry = entry;
    }

    @Column(name = "REPLY_TO")
    public String getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(String replyTo) {
        this.replyTo = replyTo;
    }

    @Column(name = "POST_BY")
    public String getPostBy() {
        return postBy;
    }

    public void setPostBy(String postBy) {
        this.postBy = postBy;
    }

    @NotAudited
    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "comment",
            cascade = CascadeType.ALL
    )
    public Set<CommentAttachment> getAttachments() {
        return attachments;
    }

    public void addAttachment(CommentAttachment attachment) {
        getAttachments().add(attachment);
    }

    public void setAttachments(Set<CommentAttachment> attachments) {
        this.attachments = attachments;
    }
}
