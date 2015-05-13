package ua.org.project.web.controller.front;

import com.google.common.collect.Lists;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.org.project.domain.Category;
import ua.org.project.domain.Impression;
import ua.org.project.domain.SearchCriteria;
import ua.org.project.domain.impl.Entry;
import ua.org.project.domain.impl.EntryAttachment;
import ua.org.project.service.CategoryService;
import ua.org.project.service.EntryAttachmentService;
import ua.org.project.service.EntryService;
import ua.org.project.util.form.CategoryProperty;
import ua.org.project.web.form.*;
import ua.org.project.util.UrlUtil;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.Validator;
import javax.validation.ConstraintViolation;
import java.util.*;

/**
 * *********************************************
 * Created by Dmitry Petrov on 5/29/14.
 * <p/>
 * This controller is responsible for posts
 * *********************************************
 */
@RequestMapping(value = {"/", "/index", "/blogs"})
@Controller
public class EntryController {

    public static final String MENU = "menu";
    public static final String ENTRY = "entry";
    public static final String MESSAGE = "message";
    public static final String DANGER = "danger";
    public static final String SUCCESS = "success";

    final Logger logger = LoggerFactory.getLogger(EntryController.class);
    String defaultColumnSort = "postDate";
    int defaultCountRows = 4;
    int defaultStartPage = 1;
    String defaultDateFormat = "yyyy-MM-dd";
    static List<Category> categories;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private EntryService entryService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private EntryAttachmentService entryAttachmentService;

    @Autowired
    private Validator validator;

    @InitBinder
    public void initBinder(WebDataBinder binder){
        binder.registerCustomEditor(Category.class, new CategoryProperty(categoryService));
    }

    /**
     * *********************************************
     * Show list of Posts by criteria and without.
     *
     * @param uiModel
     * @param locale
     * @param page
     * @param rows
     * @param isSearch
     * @param subject
     * @param categoryId
     * @param fromPostDateString
     * @param toPostDateString
     * @return **********************************************
     */
    @RequestMapping(method = RequestMethod.GET)
    public String list(
            Model uiModel,
            Locale locale,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "rows", required = false) Integer rows,
            @RequestParam(value = "_search", required = false) boolean isSearch,
            @RequestParam(value = "subject", required = false) String subject,
            @RequestParam(value = "categoryId", required = false) String categoryId,
            @RequestParam(value = "showUnPosted", required = false) boolean showUnPosted,
            @RequestParam(value = "showDeleted", required = false) boolean showDeleted,
            @RequestParam(value = "find", required = false) boolean findAll,
            @RequestParam(value = "fromPostDate", required = false) String fromPostDateString,
            @RequestParam(value = "toPostDate", required = false) String toPostDateString) {

        PageRequest pageRequest = this.getPageRequest(rows, page);
        String dateFormat = this.getLocaleDateFormat(locale);

        Set<String> categories = new HashSet<String>();
        if (categoryId != null) {
            Category category = categoryService.findById(categoryId);
            for (Category category1 : category.getSubCategories()) {
                categories.add(category1.getCategoryId());
            }
            categories.add(categoryId);
        } else {
            List<Category> subcategories = categoryService.findAll();
            for (Category subCategory : subcategories) {
                categories.add(subCategory.getCategoryId());
            }
        }
        Page<Entry> entryPage = null;
        if (findAll) {
            if (categoryId != null) {
                entryPage = entryService.findAllByCategory(categories, pageRequest);
            } else {
                entryPage = entryService.findAllByPage(pageRequest);
            }
        } else {
            SearchCriteria searchCriteria = new SearchCriteria();
            searchCriteria.setDeleted(showDeleted);
            searchCriteria.setShowUnPosted(showUnPosted);
            searchCriteria.setCategories(categories);
            searchCriteria.setLocale("%" + locale + "%");

            if (fromPostDateString != null) {
                DateTime fromPostDate = DateTimeFormat.forPattern(dateFormat).parseDateTime(fromPostDateString);
                searchCriteria.setFromPostDate(fromPostDate);
            }
            if (toPostDateString != null) {
                DateTime toPostDate = DateTimeFormat.forPattern(dateFormat).parseDateTime(toPostDateString);
                searchCriteria.setToPostDate(toPostDate);
            } else {
                DateTime dateTime = new DateTime();
                dateTime = dateTime.plusDays(1);
                searchCriteria.setToPostDate(dateTime);
            }
            if (isSearch) {
                searchCriteria.setSearch(isSearch);
                if (subject != null) {
                    subject = "%" + subject + "%";
                    searchCriteria.setSubject(subject);
                }
                if (categoryId != null){
                    searchCriteria.setCategories(Collections.singleton("%" + categoryId + "%"));
                }
            }
            logger.info("Search criteria: " + searchCriteria.toString());
            entryPage = entryService.findEntryByCriteria(searchCriteria, pageRequest);
        }
        EntryGrid entryGrid = new EntryGrid();
        entryGrid.setCurrentPage(entryPage.getNumber() + 1);
        entryGrid.setTotalPages(entryPage.getTotalPages());
        entryGrid.setTotalRecords(entryPage.getTotalElements());
        entryGrid.setEntryData(Lists.newArrayList(entryPage.iterator()));

        uiModel.addAttribute("entries", entryGrid);
        uiModel.addAttribute(EntryController.MENU, getMenu(categoryId));

        return "blogs/list";
    }

    /**
     * First post must
     * @param uiModel
     * @return
     */
    @RequestMapping(value = "contact", method = RequestMethod.GET)
    public String showContact(Model uiModel){
        Entry entry = entryService.findById(1l);
        uiModel.addAttribute(EntryController.ENTRY, entry);
        return "blogs/show";
    }

    /**
     * *********************************************
     * Show post Details
     *
     * @param id
     * @param uiModel
     * @return **********************************************
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String show(
            @PathVariable("id") Long id,
            Model uiModel) {

        Entry entry = entryService.findById(id);
        Set<ConstraintViolation<Entry>> violations = validator.validate(entry);

        if (violations.isEmpty()) {
            entryService.increaseImpression(entry);
        } else {
            logger.error("Invalid post Id: {}. Invalid data, count of errors: " + violations.size(), id);
            for (ConstraintViolation<Entry> violation : violations) {
                logger.error("Path: " + violation.getPropertyPath()
                        + ", message template:" + violation.getMessageTemplate()
                        + ", message: " + violation.getMessage());
            }
        }

        uiModel.addAttribute(EntryController.ENTRY, entry);

        UploadItem uploadItem = new UploadItem();
        uploadItem.setBlogId(entry.getId());

        uiModel.addAttribute("uploadItem", uploadItem);
        uiModel.addAttribute(EntryController.MENU, this.getMenu(entry.getCategory().getCategoryId()));

        return "blogs/show";
    }

    /**
     * *********************************************
     * Form for creating new post
     *
     * @param uiModel
     * @return **********************************************
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(params = "form", method = RequestMethod.GET)
    public String createForm(Model uiModel, @AuthenticationPrincipal User user) {
        Entry entry = new Entry();
        uiModel.addAttribute(EntryController.ENTRY, entry);
        uiModel.addAttribute("currentDate", new DateTime());
        uiModel.addAttribute(EntryController.MENU, this.getMenu(entry.getCategory().getCategoryId()));
        return "blogs/create";
    }

    /**
     * *********************************************
     * Process of saving new post
     *
     * @param entry
     * @param bindingResult
     * @param uiModel
     * @param httpServletRequest
     * @param redirectAttributes
     * @param locale
     * @return **********************************************
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(params = "form", method = RequestMethod.POST)
    public String create(
            @Valid Entry entry,
            BindingResult bindingResult,
            Model uiModel,
            HttpServletRequest httpServletRequest,
            RedirectAttributes redirectAttributes,
            Locale locale
    ) {
        logger.info("Create post: " + entry.getId());
        logger.info("Entry: " + entry.toString());
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute(EntryController.MESSAGE, new Message(
                    EntryController.DANGER,
                    messageSource.getMessage("message_entry_save_fail", new Object[]{}, locale)));
            uiModel.addAttribute(EntryController.ENTRY, entry);
            uiModel.addAttribute(EntryController.MENU, this.getMenu(entry.getCategory().getCategoryId()));
            return "blogs/create";
        }
        uiModel.asMap().clear();
        redirectAttributes.addFlashAttribute(EntryController.MESSAGE, new Message(
                EntryController.SUCCESS,
                messageSource.getMessage("message_entry_save_success", new Object[]{}, locale)));

        entry.addImpression(new Impression());
        entry = entryService.save(entry);

        return "redirect:/blogs/" + UrlUtil.encodeUrlPathSegment(entry.getId().toString(), httpServletRequest);
    }

    /**
     * *********************************************
     * Form for updating post
     *
     * @param id
     * @param uiModel
     * @return **********************************************
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/{id}", params = "form", method = RequestMethod.GET)
    public String updateForm(
            @PathVariable("id") Long id,
            Model uiModel,
            HttpServletRequest httpServletRequest,
            RedirectAttributes redirectAttributes,
            @AuthenticationPrincipal User user,
            Locale locale
    ) {
        logger.info("Get update form: " + user.toString());
        Entry entry = entryService.findById(id);

        if (!entry.getCreatedBy().equals(user.getUsername())) {
            redirectAttributes.addFlashAttribute(EntryController.MESSAGE, new Message(
                    EntryController.DANGER,
                    messageSource.getMessage("message_entry_access_fail", new Object[]{}, locale)));
            return "redirect:/blogs/" + UrlUtil.encodeUrlPathSegment(entry.getId().toString(), httpServletRequest);
        }
        uiModel.addAttribute(EntryController.ENTRY, entry);
        uiModel.addAttribute("currentDate", new DateTime());
        uiModel.addAttribute(EntryController.MENU, this.getMenu(entry.getCategory().getCategoryId()));
        return "blogs/update";
    }

    /**
     * *****************************************************
     * Process for saving post
     *
     * @param entry
     * @param bindingResult
     * @param uiModel
     * @param httpServletRequest
     * @param redirectAttributes
     * @param locale
     * @return *********************************************
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/{id}", params = "form", method = RequestMethod.POST)
    public String update(
            @Valid Entry entry,
            BindingResult bindingResult,
            Model uiModel,
            HttpServletRequest httpServletRequest,
            RedirectAttributes redirectAttributes,
            @AuthenticationPrincipal User user,
            Locale locale) {

        logger.info("Updating post id: " + entry.getId());
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute(EntryController.MESSAGE, new Message(
                    EntryController.DANGER,
                    messageSource.getMessage("message_entry_save_fail", new Object[]{}, locale)));
            logger.warn(bindingResult.getAllErrors().toString());
            logger.warn(entry.toString());
            uiModel.addAttribute(EntryController.ENTRY, entry);
            uiModel.addAttribute(EntryController.MENU, this.getMenu(entry.getCategory().getCategoryId()));
            return "blogs/update";
        }
        uiModel.asMap().clear();
        redirectAttributes.addFlashAttribute(EntryController.MESSAGE, new Message(
                EntryController.SUCCESS,
                messageSource.getMessage("message_entry_save_success", new Object[]{}, locale)));
        entryService.save(entry);
        return "redirect:/blogs/" + UrlUtil.encodeUrlPathSegment(entry.getId().toString(), httpServletRequest);
    }

    /**
     * Remove the post. Set entry.isDeleted = true
     * @param id
     * @param redirectAttributes
     * @param locale
     * @return
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "remove/{id}", method = RequestMethod.GET)
    public String remove(
            @PathVariable("id") Long id,
            RedirectAttributes redirectAttributes,
            Locale locale
    ) {
        logger.info("Deleting post id: " + id);
        Entry entry = entryService.findById(id);
        if (entry == null){
            redirectAttributes.addFlashAttribute(EntryController.MESSAGE, new Message(
                    EntryController.DANGER,
                    messageSource.getMessage("message_entry_removed_fail", new Object[]{}, locale)));
        } else {
            entry.setIsDeleted(true);
            entryService.save(entry);
            redirectAttributes.addFlashAttribute(EntryController.MESSAGE, new Message(
                    EntryController.SUCCESS,
                    messageSource.getMessage("message_entry_removed_success", new Object[]{}, locale)));
        }
        return "redirect:blogs/" + id.toString();
    }

    /**
     * Turn back the post. Set entry.isDeleted = false
     * @param id
     * @param redirectAttributes
     * @param locale
     * @return
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "unremove/{id}")
    public String unremove(
            @PathVariable("id") Long id,
            RedirectAttributes redirectAttributes,
            Locale locale
    ) {
        Entry entry = entryService.findById(id);
        if (entry == null){
            redirectAttributes.addFlashAttribute(EntryController.MESSAGE, new Message(
                    EntryController.DANGER,
                    messageSource.getMessage("message_entry_unremoved_fail", new Object[]{}, locale)));
        } else {
            entry.setIsDeleted(false);
            entryService.save(entry);
            redirectAttributes.addFlashAttribute(EntryController.MESSAGE, new Message(
                    EntryController.SUCCESS,
                    messageSource.getMessage("message_entry_unremoved_success", new Object[]{}, locale)));
        }
        return "redirect:blogs/" + id.toString();
    }

    /**
     * Remove attachment file
     * @param id
     * @param redirectAttributes
     * @param locale
     * @return
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "attachment/remove/{id}", method = RequestMethod.GET)
    public String removeAttachment(
            @PathVariable("id") Long id,
            RedirectAttributes redirectAttributes,
            Locale locale
    ) {
        EntryAttachment entryAttachment = entryAttachmentService.findById(id);
        Entry entry = null;
        if (entryAttachment == null){
            redirectAttributes.addFlashAttribute(EntryController.MESSAGE, new Message(
                    EntryController.DANGER,
                    messageSource.getMessage("message_entry_attachment_removed_fail", new Object[]{}, locale)));
        } else {
            entry = entryAttachment.getEntry();
            entryAttachmentService.delete(entryAttachment);
            redirectAttributes.addFlashAttribute(EntryController.MESSAGE, new Message(
                    EntryController.SUCCESS,
                    messageSource.getMessage("message_entry_attachment_removed_success", new Object[]{}, locale)));
        }
        return "redirect:/blogs/" + entry.getId().toString();
    }

    /**
     * Catch Exception
     * @param exception
     * @param httpServletRequest
     * @return
     */
//    @ExceptionHandler(Exception.class)
//    public String handleMyException(Exception exception, HttpServletRequest httpServletRequest) {
//        return "redirect:/errorMessage/?error=" + UrlUtil.encodeUrlPathSegment("404", httpServletRequest);
//    }
//
//    @RequestMapping(value = "/errorMessage", method = RequestMethod.GET)
//    public String handleMyExceptionOnRedirect(
//            @RequestParam("error") String error,
//            RedirectAttributes redirectAttributes,
//            Locale locale
//            ) {
//        redirectAttributes.addFlashAttribute(EntryController.MESSAGE, new Message(
//                EntryController.DANGER,
//                messageSource.getMessage(error, new Object[]{}, locale)));
//        return "redirect:/blogs";
//    }

    /**
     * *********************************************
     * Return date format by Locale
     *
     * @param locale
     * @return **********************************************
     */
    private String getLocaleDateFormat(Locale locale) {
        String dateFormat = messageSource.getMessage("date_format_pattern", new Object[]{}, locale);
        if (dateFormat == null) {
            dateFormat = defaultDateFormat;
        }
        return dateFormat;
    }

    /**
     * *********************************************
     * Return Right Menu
     *
     * @param categoryId
     * @return MenuShow
     * **********************************************
     */
    private MenuShow getMenu(String categoryId) {
        logger.info("Get category id: " + categoryId);
        MenuShow menu = new MenuShow();
        if (categories == null) {
            categories = categoryService.findAll();
        }
        Category currentCategory = null;
        if (categoryId != null) {
            currentCategory = categoryService.findById(categoryId);
            if (currentCategory != null && currentCategory.getParentCategory() != null) {
                logger.info("Get parent category id: " + currentCategory.getParentCategory().getCategoryId());
                menu.setParentCategory(currentCategory.getParentCategory().getCategoryId());
            }
        }
        menu.setCategories(categories);
        menu.setCurrentCategory(categoryId);

        return menu;
    }

    /**
     * Create PageRequest
     *
     * @param rows
     * @param page
     * @return
     */
    private PageRequest getPageRequest(Integer rows, Integer page) {
        Sort sort = new Sort(Sort.Direction.DESC, defaultColumnSort);
        PageRequest pageRequest = null;

        if (rows == null) {
            rows = defaultCountRows;
        }

        if (page == null) {
            page = defaultStartPage;
        }
        pageRequest = new PageRequest(page - 1, rows, sort);
        return pageRequest;
    }

}
