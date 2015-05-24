package ua.org.project.domain;

import org.joda.time.LocalDateTime;

/**
 * Created by Dmitry Petrov on 5/27/14.
 */
public interface Blog {
    String getBody();
    void setBody(String body);
    LocalDateTime getPostDate();
    void setPostDate(LocalDateTime postDate);
}
