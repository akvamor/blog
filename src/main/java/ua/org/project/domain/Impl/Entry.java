package ua.org.project.domain.impl;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.validator.constraints.NotEmpty;
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
@Table(name = "entry")
public class Entry extends AbstractBlog implements Serializable {

    private static final int MAX_BODY_LENGTH = 80;
    private static final String THREE_DOTS = "...";

    private String categoryId;
    private String subCategoryId;
    private Set<EntryAttachment> attachments = new HashSet<EntryAttachment>();
    private Set<Comment> comments = new HashSet<Comment>();

    public Entry(){}

    @NotEmpty
    @Column(name = "CATEGORY_ID")
    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    @Column(name = "SUB_CATEGORY_ID")
    public String getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(String subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    @JsonIgnore
    @NotAudited
    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "entry",
            cascade = CascadeType.ALL
    )
    public Set<EntryAttachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(Set<EntryAttachment> attachments) {
        this.attachments = attachments;
    }

    @JsonIgnore
    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "entry",
            cascade = CascadeType.ALL
    )
    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    @Transient
    public String getShortBody(){
        if (body.length() <= MAX_BODY_LENGTH)
            return body;
        StringBuffer result = new StringBuffer(MAX_BODY_LENGTH + 3);
        result.append(body.substring(0, MAX_BODY_LENGTH));
        result.append(THREE_DOTS);

        return result.toString();
    }

    public void addComment(Comment comment) {
        comment.setEntry(this);
        getComments().add(comment);
    }

    public void addAttachment(EntryAttachment entryAttachment){
        getAttachments().add(entryAttachment);
    }

    @Override
    public String toString() {
        return "Entry{" +
                "categoryId='" + categoryId + "\'\n" +
                ", subCategoryId='" + subCategoryId + "\'\n" +
                ", attachments=" + attachments + "\n" +
                ", comments=" + comments +
                '}' +
                "Inherit properties{" +
                "Subject='" + subject + "\'\n" +
                "Category='" + categoryId + "\'\n" +
                "Post Date='" + postDate + "\'\n" +
                "Version='" + version + "}" ;
    }
}
