package ua.org.project.domain.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.NotEmpty;
import ua.org.project.domain.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Dmitry Petrov on 5/28/14.
 */
@Entity
@Table(name = "ENTRY")
public class Entry extends AbstractBlog implements Serializable {

	private static final long serialVersionUID = -2105667655692873010L;
    private static final String THREE_DOTS = "...";

    private Category category;
    private String locale;
    private String subject;
    private String body;
    private String bodyShort;
    private Impression impressions;
    private Set<EntryAttachment> attachments = new HashSet<EntryAttachment>();
    private Set<EntryLike> likes = new HashSet<EntryLike>();
    private Set<Comment> comments = new HashSet<Comment>();

    public Entry() {     }

    @NotEmpty(message = "{validation.posting.bodyshort.NotEmpty.message}")
    @Column(name = "BODY_SHORT")
    public String getBodyShort() {return bodyShort;}
    public void setBodyShort(String bodyShort) {this.bodyShort = bodyShort;}


    @Column(name = "SUBJECT")
    @NotEmpty(message = "{validation.posting.subject.NotEmpty.message}")
    @Size(min = 5, max = 200, message = "{validation.posting.subject.Size.message}")
    public String getSubject() {
        return this.subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }


    @JsonIgnore
    @NotEmpty(message = "{validation.posting.body.NotEmpty.message}")
    @Size(min = 10, message = "{validation.posting.body.Size.message}")
    @Column(name = "BODY")
    public String getBody() {
        return this.body;
    }
    public void setBody(String body) {
        this.body = body;
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

    
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "entry", cascade = CascadeType.ALL)
    public Impression getImpressions() {
        return impressions;
    }
    public void setImpressions(Impression impressions) {
        this.impressions = impressions;
    }


    @NotNull
    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CATEGORY_ID")
    public Category getCategory() {
        return this.category;
    }
    public void setCategory(Category category) {
        this.category = category;
    }


    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "entry", cascade = CascadeType.ALL)
    @OrderBy("id DESC ")
    public Set<EntryAttachment> getAttachments() {
        return this.attachments;
    }
    public void setAttachments(Set<EntryAttachment> attachments) {
        this.attachments = attachments;
    }


    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "entry", cascade = CascadeType.ALL)
    @OrderBy("createdDate DESC ")
    public Set<Comment> getComments() {
        return this.comments;
    }
    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }


    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "entry", cascade = CascadeType.ALL)
    public Set<EntryLike> getLikes() {
        return likes;
    }
    public void setLikes(Set<EntryLike> likes) {
        this.likes = likes;
    }


    @Transient
    public Set<Attachment> getAttachmentAbstract(){
        Set<Attachment> attach = new HashSet<Attachment>(this.attachments);
        return attach;
    }

    public void addComment(Comment comment){
        comments.add(comment);
        comment.setEntry(this);
    }
    public void addImpression(Impression impression){
        this.setImpressions(impression);
        impression.setEntry(this);
    }
    public void addAttachment(EntryAttachment attachment) {
        getAttachments().add(attachment);
    }

    public String toString() {
        return "Entry id: " + id + " - subject: " + subject + " - category: "
                + " - post date: " + postDate
                + " - created by: " + createdBy + " - created date: " + createdDate
                + " - last modified by: " + lastModifiedBy + " - last modified date: " + lastModifiedDate
                + " - version: " + version;
    }
}

