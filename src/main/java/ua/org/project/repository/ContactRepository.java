package ua.org.project.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import ua.org.project.domain.Contact;

import java.util.List;

/**
 * Created by Dmitry Petrov on 5/19/14.
 */


public interface ContactRepository extends PagingAndSortingRepository<Contact, Long> {
    public List<Contact> findByFirstNameAndLastName(String firstName, String lastName);
}
