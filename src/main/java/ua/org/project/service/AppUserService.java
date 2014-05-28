package ua.org.project.service;

import ua.org.project.domain.AppUser;

/**
 * Created by Dmitry Petrov on 5/28/14.
 */
public interface AppUserService {
    public AppUser findByUserName(String userName);
}
