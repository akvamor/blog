package ua.org.project.domain.impl;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
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
@Audited
@Table(name = "entry")
public class Entry extends AbstractBlog implements Serializable {

    private static final int MAX_BODY_LENGTH = 300;
    private static final String THREE_DOTS = "...";

    private String categoryId;
    private String locale;
    private int impressions;
    private Set<EntryAttachment> attachments = new HashSet<EntryAttachment>();
    private Set<Comment> comments = new HashSet<Comment>();

    public Entry() {
    }

    @Transient
    public String getShortBody() {
        if (body.length() <= MAX_BODY_LENGTH)
            return body;
        StringBuffer result = new StringBuffer(MAX_BODY_LENGTH + 3);
        result.append(body.substring(0, MAX_BODY_LENGTH));
        result.append(THREE_DOTS);

        return result.toString();
    }

    @Column(name = "LOCALE")
    @NotEmpty(message = "{validation.posting.locale.NotEmpty.message}")
    @Size(min = 4, max = 17, message = "{validation.posting.body.Size.message}")
    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    @Column(name = "IMPRESSIONS")
    public int getImpressions() {
        return impressions;
    }

    public void setImpressions(int impressions) {
        this.impressions = impressions;
    }

    @NotEmpty
    @Column(name = "CATEGORY_ID")
    public String getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    @JsonIgnore
    @NotAudited
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "entry", cascade = CascadeType.ALL)
    @OrderBy("id DESC ")
    public Set<EntryAttachment> getAttachments() {
        return this.attachments;
    }

    @Transient
    public Set<Attachment> getAttachmentAbstract(){
        Set<Attachment> attach = new HashSet<Attachment>(this.attachments);
        return attach;
    }

    public void setAttachments(Set<EntryAttachment> attachments) {
        this.attachments = attachments;
    }

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "entry", cascade = CascadeType.ALL)
    @OrderBy("createdDate DESC ")
    public Set<Comment> getComments() {
        return this.comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public void addComment(Comment comment) {
        comment.setEntry(this);
        getComments().add(comment);
    }

    public void addAttachment(EntryAttachment attachment) {
        getAttachments().add(attachment);
    }

    public String toString() {
        return "Entry id: " + id + " - subject: " + subject + " - category: " + categoryId
                + " - post date: " + postDate
                + " - created by: " + createdBy + " - created date: " + createdDate
                + " - last modified by: " + lastModifiedBy + " - last modified date: " + lastModifiedDate
                + " - version: " + version;
    }
}

