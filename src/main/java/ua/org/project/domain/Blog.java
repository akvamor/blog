package ua.org.project.domain;

import org.joda.time.DateTime;

/**
 * Created by Dmitry Petrov on 5/27/14.
 */
public interface Blog {
    String getBody();
    void setBody(String body);
    DateTime getPostDate();
    void setPostDate(DateTime postDate);
}
