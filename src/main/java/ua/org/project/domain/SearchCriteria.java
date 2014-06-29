package ua.org.project.domain;

import org.joda.time.DateTime;

/**
 * Created by Dmitry Petrov on 5/28/14.
 */
public class SearchCriteria {

    private String subject = "%";

    private String categoryId;

    private DateTime fromPostDate = new DateTime(1900, 1, 1, 0, 0);

    private DateTime toPostDate = new DateTime(2200, 12, 31, 23, 59);

    private String locale = "%en-EN%";

    private boolean isDeleted = false;

    private boolean isSearch = false;

    private boolean showUnPosted = false;

    public boolean isSearch() {
        return isSearch;
    }

    public void setSearch(boolean isSearch) {
        this.isSearch = isSearch;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

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

    public DateTime getFromPostDate() {
        return fromPostDate;
    }

    public void setFromPostDate(DateTime fromPostDate) {
        this.fromPostDate = fromPostDate;
    }

    public DateTime getToPostDate() {
        return toPostDate;
    }

    public void setToPostDate(DateTime toPostDate) {
        this.toPostDate = toPostDate;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public boolean isShowUnPosted() {
        return showUnPosted;
    }

    public void setShowUnPosted(boolean showUnPosted) {
        this.showUnPosted = showUnPosted;
    }

    @Override
    public String toString() {
        return "SearchCriteria{" +
                "subject='" + subject + '\'' +
                ", categoryId='" + categoryId + '\'' +
                ", fromPostDate=" + fromPostDate +
                ", toPostDate=" + toPostDate +
                ", locale='" + locale + '\'' +
                ", isDeleted=" + isDeleted +
                ", isSearch=" + isSearch +
                ", showUnPosted=" + showUnPosted +
                '}';
    }
}
