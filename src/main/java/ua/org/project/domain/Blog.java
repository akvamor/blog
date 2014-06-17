package ua.org.project.domain;

import org.joda.time.DateTime;

/**
 * Created by Dmitry Petrov on 5/27/14.
 */
public interface Blog {
    public String getBody();
    public void setBody(String body);
    public DateTime getPostDate();
    public void setPostDate(DateTime postDate);
}
