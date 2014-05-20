package ua.org.project.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ua.org.project.domain.Contact;

import java.util.Collection;
import java.util.List;

/**
 * Created by Dmitry Petrov on 5/19/14.
 */
public interface ContactService {
    public List<Contact> findAll();
    public Contact findById(Long id);
    public Contact findByFirstNameAndLastName(String firstName, String lastName);
    public Contact save(Contact contact);
    public Page<Contact> findAllByPage(Pageable pageable);

}
