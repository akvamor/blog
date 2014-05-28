package ua.org.project.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Dmitry Petrov on 5/28/14.
 */
@Entity
@Table(name = "app_user")
public class AppUser implements Serializable{
    private String userId;
    private String password;
    private String userName;
    private String createdBy;
    private Date createdDate;
    private String lastModifiedBy;
    private Date lastModifiedDate;
    private Set<Role> roles = new HashSet<Role>(0);

    public AppUser(){};

    /**
     * Without role
     * @param userId
     * @param password
     * @param createdBy
     * @param createdDate
     * @param lastModifiedBy
     * @param lastModifiedDate
     */
    public AppUser(
            String userId,
            String password,
            String createdBy,
            Date createdDate,
            String lastModifiedBy,
            Date lastModifiedDate
    ){
        this.userId = userId;
        this.password = password;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.lastModifiedBy = lastModifiedBy;
        this.lastModifiedDate = lastModifiedDate;
    }

    /**
     * With role
     * @param userId
     * @param password
     * @param createdBy
     * @param createdDate
     * @param lastModifiedBy
     * @param lastModifiedDate
     * @param roles
     */
    public AppUser(
            String userId,
            String password,
            String createdBy,
            Date createdDate,
            String lastModifiedBy,
            Date lastModifiedDate,
            Set<Role> roles
    ){
        this.userId = userId;
        this.password = password;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.lastModifiedBy = lastModifiedBy;
        this.lastModifiedDate = lastModifiedDate;
        this.roles = roles;
    }

    @Id
    @Column(name = "USER_ID")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Column(name = "PASSWORD")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "USER_NAME")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Column(name = "CREATED_BY")
    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Column(name = "CREATED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
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
    @Temporal(TemporalType.TIMESTAMP)
    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role_detail",
            joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "ROLE_ID")
    )
    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}