package ua.org.project.service.jpa;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.org.project.domain.Contact;
import ua.org.project.repository.ContactRepository;
import ua.org.project.service.ContactService;

import java.util.List;

/**
 * Created by Dmitry Petrov on 5/20/14.
 */
@Service("contactService")
@Transactional
@Repository
public class ContactServiceImpl implements ContactService{

    @Autowired
    private ContactRepository contactRepository;

    @Transactional(readOnly = true)
    public List<Contact> findAll() {
        return Lists.newArrayList(contactRepository.findAll());
    }

    @Transactional(readOnly = true)
    public Contact findById(Long id) {
        return contactRepository.findOne(id);
    }

    @Transactional(readOnly = true)
    public Contact findByFirstNameAndLastName(String firstName, String lastName) {
        Contact contact = null;
        List<Contact> contacts = contactRepository.findByFirstNameAndLastName(firstName, lastName);
        if (contacts.size() > 0)
            contact = contacts.get(0);
        return contact;
    }

    @Override
    public Contact save(Contact contact) {
        return contactRepository.save(contact);
    }

    @Transactional(readOnly = true)
    public Page<Contact> findAllByPage(Pageable pageable) {
        return contactRepository.findAll(pageable);
    }
}
