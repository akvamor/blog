package ua.org.project.repository;

import org.springframework.data.repository.CrudRepository;
import ua.org.project.domain.AppUser;

/**
 * Created by Dmitry Petrov on 5/28/14.
 */
public interface AppUserRepository extends CrudRepository<AppUser, String> {

}
