package ua.org.project.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ua.org.project.domain.Contact;
import ua.org.project.service.ContactService;

import java.util.List;

/**
 * Created by Dmitry Petrov on 5/20/14.
 */
@RequestMapping("/contacts")
@Controller
public class ContactController {

    final Logger logger = LoggerFactory.getLogger(ContactController.class);

    @Autowired
    MessageSource messageSource;

    @Autowired
    private ContactService contactService;

    @RequestMapping(method = RequestMethod.GET)
    public String list(Model uiModel){
        logger.info("Show all contact");
        List<Contact> contacts = contactService.findAll();
        uiModel.addAttribute("contacts", contacts);
        logger.info("Count of contacts: " + contacts.size());
        return "contacts/list";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String show(@PathVariable("id") Long id, Model uiModel){
        Contact contact = contactService.findById(id);
        uiModel.addAttribute("contact", contact);
        return "contacts/show";
    }
}
