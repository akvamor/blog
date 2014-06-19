package ua.org.project.domain;

import ua.org.project.domain.impl.Comment;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Dmitry Petrov on 17.06.14.
 */
@MappedSuperclass
public class AbstractLike implements Serializable {

    private Long id;
    private int like;
    private AppUser appUser;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    @Column(name = "LIKE")
    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }
}
