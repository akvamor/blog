package ua.org.project.domain;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.Type;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;
import org.springframework.data.domain.Auditable;
import org.springframework.format.annotation.DateTimeFormat;
import ua.org.project.domain.impl.EntryAttachment;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.*;

/**
 * Created by Dmitry Petrov on 5/27/14.
 */

@MappedSuperclass
@Audited
public abstract class AbstractBlog implements Blog, Auditable<String, Long>, Serializable {

    protected Long id;
    protected DateTime postDate;
    protected String createdBy;
    protected DateTime createdDate;
    protected String lastModifiedBy;
    protected DateTime lastModifiedDate;
    protected int version;
    protected Long countLikes;
    protected Long countNotLikes;

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
    @NotEmpty(message = "{validation.posting.last_modified_by.NotEmpty.message}")
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

    @Version
    @Column(name = "VERSION")
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
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

    @Transient
    public Long getCountLikes() {
        return countLikes;
    }

    public void setCountLikes(Long countLikes) {
        this.countLikes = countLikes;
    }

    @Transient
    public Long getCountNotLikes() {
        return countNotLikes;
    }

    public void setCountNotLikes(Long countNotLikes) {
        this.countNotLikes = countNotLikes;
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

    @Transient
    abstract protected Set<Attachment> getAttachmentAbstract();

    @Transient
    public Set<Long> getImagesId(){
        TreeSet<Long> list = new TreeSet<Long>();

        Set<Attachment> attachments = this.getAttachmentAbstract();
        for (Attachment attachment : attachments) {
            if (attachment.getContentType().equals("image/jpeg")){
                list.add(attachment.getId());
            }
        }

        return list;
    }


}
