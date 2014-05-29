package ua.org.project.domain;

import ua.org.project.domain.AppUser;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Dmitry Petrov on 5/28/14.
 */
@Entity
@Table(name = "role")
public class Role implements Serializable {
    private String roleId;
    private String description;
    private Set<AppUser> appUsers = new HashSet<AppUser>(0);

    public Role(){}

    public Role(String roleId){
        this.roleId = roleId;
    }

    public Role(String roleId, String description, Set<AppUser>appUsers){
        this.roleId = roleId;
        this.description = description;
        this.appUsers = appUsers;
    }

    @Id
    @Column(name = "ROLE_ID")
    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    @Column(name = "DESCRIPTION")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "roles")
    public Set<AppUser> getAppUsers() {
        return appUsers;
    }

    public void setAppUsers(Set<AppUser> appUsers) {
        this.appUsers = appUsers;
    }
}
