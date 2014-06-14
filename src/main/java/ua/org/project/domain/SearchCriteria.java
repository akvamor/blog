package ua.org.project.domain;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

/**
 * Created by Dmitry Petrov on 5/28/14.
 */
public class SearchCriteria {

    private String subject = "%";

    private Collection<String> categoriesId ;

    private DateTime fromPostDate = new DateTime(1900, 1, 1, 0, 0);;

    private DateTime toPostDate = new DateTime(2200, 12, 31, 23, 59);;

    private String locale = "%en-EN%";

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Collection<String> getCategoriesId() {
        return categoriesId;
    }

    public void setCategoriesId(Collection<String> categoriesId) {
        this.categoriesId = categoriesId;
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
                ", categoryId='" + categoriesId + '\'' +
                ", fromPostDate=" + fromPostDate +
                ", toPostDate=" + toPostDate +
                ", locale='" + locale + '\'' +
                '}';
    }
}
