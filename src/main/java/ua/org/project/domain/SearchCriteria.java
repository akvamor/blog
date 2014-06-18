package ua.org.project.domain;

import org.joda.time.DateTime;

/**
 * Created by Dmitry Petrov on 5/28/14.
 */
public class SearchCriteria {

    private String subject = "%";

    private String categoryId;

    private DateTime fromPostDate = new DateTime(1900, 1, 1, 0, 0);;

    private DateTime toPostDate = new DateTime(2200, 12, 31, 23, 59);;

    private String locale = "%en-EN%";

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

    @Override
    public String toString() {
        return "SearchCriteria{" +
                "subject='" + subject + '\'' +
                ", categoryId='" + categoryId + '\'' +
                ", fromPostDate=" + fromPostDate +
                ", toPostDate=" + toPostDate +
                ", locale='" + locale + '\'' +
                '}';
    }
}
