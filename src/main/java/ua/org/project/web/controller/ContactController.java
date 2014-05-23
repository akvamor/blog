package ua.org.project.web.controller;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.org.project.domain.Contact;
import ua.org.project.service.ContactService;
import ua.org.project.web.form.ContactGrid;
import ua.org.project.web.form.Message;
import ua.org.project.web.util.UrlUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

/**
 * Created by Dmitry Petrov on 5/20/14.
 */
@RequestMapping("/contacts")
@Controller
public class ContactController {

    final static String logPrefix = "---";
    final Logger logger = LoggerFactory.getLogger(ContactController.class);

    @Autowired
    MessageSource messageSource;

    @Autowired
    private ContactService contactService;

    /**
     * VIEW. Contacts list
     * @param uiModel
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public String list(Model uiModel){
        logger.info(logPrefix + "Load view contacts/list");
        return "contacts/list";
    }

    /**
     * VIEW. Contact details
     * @param id
     * @param uiModel
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String show(@PathVariable("id") Long id, Model uiModel){
        logger.info(logPrefix + "Show contact id: {}", id);
        Contact contact = contactService.findById(id);
        uiModel.addAttribute("contact", contact);
        return "contacts/show";
    }

    /**
     * GET. Grid list of contact using AJAX
     * @param page
     * @param rows
     * @param sortBy
     * @param order
     * @return
     */
    @RequestMapping(value = "/listgrid", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public ContactGrid listGrid(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "rows", required = false) Integer rows,
            @RequestParam(value = "sidx", required = false) String sortBy,
            @RequestParam(value = "sord", required = false) String order
    ) {
        logger.info(logPrefix + "Get ContactGrid");
        logger.info(logPrefix + logPrefix + "Params: page {}, rows: {},", page, rows);
        logger.info(logPrefix + logPrefix + "Params: sort: {}, order: {}", sortBy, order);

        Sort sort = null;
        String orderBy = sortBy;
        if (orderBy != null && orderBy.equals("birthDateString"))
            orderBy = "birthDate";

        if (orderBy != null && order != null) {
            if (order.equals("desc")) {
                sort = new Sort(Sort.Direction.DESC, orderBy);
            } else {
                sort = new Sort(Sort.Direction.ASC, orderBy);
            }
        }

        PageRequest pageRequest = null;

        if (sort != null) {
            pageRequest = new PageRequest(page-1, rows, sort);
        } else {
            pageRequest = new PageRequest(page-1, rows);
        }

        Page<Contact> contactPage = contactService.findAllByPage(pageRequest);
        ContactGrid contactGrid = new ContactGrid();
        contactGrid.setCurrentPage(contactPage.getNumber() + 1);
        contactGrid.setTotalPages(contactPage.getTotalPages());
        contactGrid.setTotalRecords(contactPage.getTotalElements());
        contactGrid.setContactData(Lists.newArrayList(contactPage.iterator()));

        return contactGrid;
    }

    /**
     * GET. Photo of contact
     * @param id
     * @return
     */
    @RequestMapping(value = "/photo/{id}", method = RequestMethod.GET)
    @ResponseBody
    public byte[] downloadPhoto(@PathVariable("id") Long id) {
        logger.info(logPrefix + "Show photo id: {}", id);
        Contact contact = contactService.findById(id);
        if (contact.getPhoto() != null){
            logger.info(logPrefix + logPrefix + "Download photo id: {}, size {}", contact.getId(), contact.getPhoto().length);
        }
        return contact.getPhoto();
    }

    /**
     * EVENT. Updating contact
     * @param id
     * @param contact
     * @param bindingResult
     * @param uiModel
     * @param httpServletRequest
     * @param redirectAttributes
     * @param locale
     * @param file
     * @return
     */
    @RequestMapping(value = "/{id}", params = "form", method = RequestMethod.POST)
    public String update(
            @PathVariable("id") Long id,
            @Valid Contact contact,
            BindingResult bindingResult,
            Model uiModel,
            HttpServletRequest httpServletRequest,
            RedirectAttributes redirectAttributes,
            Locale locale,
            @RequestParam(value = "file", required = false) Part file
    ){
        logger.info(logPrefix + "Updating contact id: {}", id);
        if (bindingResult.hasErrors()){
            uiModel.addAttribute(
                    "message",
                    new Message(
                            "error",
                            messageSource.getMessage("contact_save_fail", new Object[]{}, locale)));
            uiModel.addAttribute("contact", contact);
            return "contacts/update";
        }
        uiModel.asMap().clear();
        redirectAttributes.addFlashAttribute(
                "message",
                new Message(
                        "success",
                        messageSource.getMessage("contact_save_success", new Object[]{}, locale)));

        setPhotoToContact(contact, file);

        contactService.save(contact);
        return "redirect:/contacts/" + UrlUtil.encodeUrlPathSegment(contact.getId().toString(), httpServletRequest);
    }

    /**
     * VIEW. A form to updating contact
     * @param id
     * @param uiModel
     * @return
     */
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/{id}", params = "form", method = RequestMethod.GET)
    public String updateForm(
            @PathVariable("id") Long id,
            Model uiModel
    ) {
        logger.info(logPrefix + "Show form update");
        uiModel.addAttribute("contact", contactService.findById(id));
        return "contacts/update";
    }

    /**
     * EVENT. Creating new contact
     * @param contact
     * @param bindingResult
     * @param uiModel
     * @param httpServletRequest
     * @param redirectAttributes
     * @param locale
     * @param file
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public String create(
            @Valid Contact contact,
            BindingResult bindingResult,
            Model uiModel,
            HttpServletRequest httpServletRequest,
            RedirectAttributes redirectAttributes,
            Locale locale,
            @RequestParam(value = "file", required = false) Part file
    ) {
        logger.info(logPrefix + "Creating contact");
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute(
                    "message",
                    new Message(
                            "error",
                            messageSource.getMessage(
                                    "contact_save_fail",
                                    new Object[]{},
                                    locale)));
            uiModel.addAttribute("contact", contact);
            logger.info(logPrefix + logPrefix + "Not valid contact");
            return "contacts/create";
        }
        uiModel.asMap().clear();
        redirectAttributes.addFlashAttribute(
                "message",
                new Message(
                        "success",
                        messageSource.getMessage(
                                "contact_save_success",
                                new Object[]{},
                                locale
                        )));
        logger.info(logPrefix + logPrefix + "Contact id: {}", contact.getId());


        setPhotoToContact(contact, file);
        contactService.save(contact);
        return "redirect:/contacts/" + UrlUtil.encodeUrlPathSegment(contact.getId().toString(), httpServletRequest);
    }

    /**
     * Set photo to the contact
     * @param contact
     * @param file
     */
    private void setPhotoToContact(Contact contact, Part file){
        if (file != null) {
            logger.info(logPrefix + logPrefix + "File name: {}, size: {}",file.getName(), file.getSize());
            logger.info(logPrefix + logPrefix + "File content type: {}", file.getContentType());

            byte[] fileContent = null;
            try{
                InputStream inputStream = file.getInputStream();
                if (inputStream == null)
                    logger.info(logPrefix + logPrefix + "File inputstream is empty");
                fileContent = org.apache.commons.io.IOUtils.toByteArray(inputStream);
            } catch (IOException ex) {
                logger.error(logPrefix + logPrefix + "Error saving file");
            }

            contact.setPhoto(fileContent);
        }
    }

    /**
     * VIEW. A form to create a new contact
     * @param uiModel
     * @return
     */
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(params = "form", method = RequestMethod.GET)
    public String createForm(Model uiModel) {
        Contact contact = new Contact();
        uiModel.addAttribute("contact", contact);
        return "contacts/create";
    }

}
