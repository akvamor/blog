package ua.org.project.domain;

import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;

/**
 * Created by Dmitry Petrov on 5/28/14.
 */
public class SearchCriteriaPage {

    private String subject;

    private String categoryId;

    private LocalDateTime fromPostDate;

    private LocalDateTime toPostDate;

    private int offset;

    private int pageSize;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public LocalDateTime getFromPostDate() {
        return fromPostDate;
    }

    public void setFromPostDate(LocalDateTime fromPostDate) {
        this.fromPostDate = fromPostDate;
    }

    public LocalDateTime getToPostDate() {
        return toPostDate;
    }

    public void setToPostDate(LocalDateTime toPostDate) {
        this.toPostDate = toPostDate;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
