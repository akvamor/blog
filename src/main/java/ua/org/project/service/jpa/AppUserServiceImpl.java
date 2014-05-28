package ua.org.project.service.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.org.project.domain.AppUser;
import ua.org.project.repository.AppUserRepository;
import ua.org.project.service.AppUserService;

/**
 * Created by Dmitry Petrov on 5/28/14.
 */

@Service("appUserService")
@Repository
@Transactional
public class AppUserServiceImpl implements AppUserService{

    @Autowired
    private AppUserRepository repository;

    @Transactional(readOnly = true)
    public AppUser findByUserName(String userName) {
        return repository.findOne(userName);
    }
}
