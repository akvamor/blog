package ua.org.project.domain;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.Type;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Auditable;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created by Dmitry Petrov on 5/27/14.
 */

@MappedSuperclass
@Audited
public class AbstractBlog implements Blog, Auditable<String, Long>, Serializable {

    protected Long id;
    protected String subject;
    protected String body;
    protected DateTime postDate;
    protected String createdBy;
    protected DateTime createdDate;
    protected String lastModifiedBy;
    protected DateTime lastModifiedDate;

    protected int likes;
    protected int version;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "CREATED_BY")
    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Column(name = "CREATED_DATE")
    @Type(type = "org.joda.time.contrib.hibernate.PersistentDateTime")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    public DateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(DateTime createdDate) {
        this.createdDate = createdDate;
    }

    @Column(name = "LAST_MODIFIED_BY")
    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    @Column(name = "LAST_MODIFIED_DATE")
    @Type(type = "org.joda.time.contrib.hibernate.PersistentDateTime")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    public DateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(DateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    @Column(name = "LIKES")
    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    @Version
    @Column(name = "VERSION")
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
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

    @Column(name = "POST_DATE")
    @Type(type = "org.joda.time.contrib.hibernate.PersistentDateTime")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    public DateTime getPostDate() {
        return this.postDate;
    }

    @Override
    public void setPostDate(DateTime postDate) {
        this.postDate = postDate;
    }

    @Override
    @Column(name = "SUBJECT")
    @NotEmpty(message = "{validation.posting.subject.NotEmpty.message}}")
    @Size(min = 10, max = 50, message = "{validation.posting.subject.Size.message}}")
    public String getSubject() {
        return this.subject;
    }

    @Override
    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Transient
    public String getPostDateString(String format){
        return org.joda.time.format.DateTimeFormat.forPattern(format).print(postDate);
    }

    @Transient
    public String getLastModifiedDateString(String format){
        return org.joda.time.format.DateTimeFormat.forPattern(format + " HH:mm:ss").print(this.lastModifiedDate);
    }

    @Transient
    public final boolean isNew() {
        if (id == null)
            return true;
        else
            return false;
    }
}
