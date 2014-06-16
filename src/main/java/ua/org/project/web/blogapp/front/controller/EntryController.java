package ua.org.project.web.blogapp.front.controller;

import com.google.common.collect.Lists;
import org.apache.xpath.operations.Mod;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.org.project.domain.AppUser;
import ua.org.project.domain.Category;
import ua.org.project.domain.SearchCriteria;
import ua.org.project.domain.impl.Entry;
import ua.org.project.service.AppUserService;
import ua.org.project.service.CategoryService;
import ua.org.project.service.EntryService;
import ua.org.project.web.blogapp.front.form.EntryGrid;
import ua.org.project.web.blogapp.front.form.EntryShow;
import ua.org.project.web.blogapp.front.form.MenuShow;
import ua.org.project.web.blogapp.front.form.UploadItem;
import ua.org.project.web.form.Message;
import ua.org.project.web.util.UrlUtil;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.Validator;
import javax.validation.ConstraintViolation;
import java.util.*;

/************************************************
 * Created by Dmitry Petrov on 5/29/14.
 *
 * This controller is responsible for posts
 ***********************************************/
@RequestMapping(value={"/", "/index", "/blogs"})
@Controller
public class EntryController {

    final Logger logger = LoggerFactory.getLogger(EntryController.class);
    String defaultColumnSort = "postDate";
    int defaultCountRows = 4;
    int defaultStartPage = 1;
    String defaultDateFormat = "yyyy-MM-dd";

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private EntryService entryService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private Validator validator;

    /************************************************
     * Show list of Posts by criteria and without.
     * @param uiModel
     * @param locale
     * @param page
     * @param rows
     * @param isSearch
     * @param subject
     * @param categoryId
     * @param fromPostDateString
     * @param toPostDateString
     * @return
     ************************************************/
    @RequestMapping(method = RequestMethod.GET)
    public String list(
            Model uiModel,
            Locale locale,
            @RequestParam(value = "page",       required = false) Integer page,
            @RequestParam(value = "rows",       required = false) Integer rows,
            @RequestParam(value = "_search",    required = false) boolean isSearch,
            @RequestParam(value = "subject",    required = false) String subject,
            @RequestParam(value = "categoryId", required = false) String categoryId,
            @RequestParam(value = "fromPostDate",required = false) String fromPostDateString,
            @RequestParam(value = "toPostDate", required = false) String toPostDateString) {

        SearchCriteria searchCriteria = new SearchCriteria();
        if (isSearch) {
            String dateFormat = this.getLocaleDateFormat(locale);

            if (fromPostDateString != null) {
                DateTime fromPostDate = DateTimeFormat.forPattern(dateFormat).parseDateTime(fromPostDateString);
                searchCriteria.setFromPostDate(fromPostDate);
            }

            if (toPostDateString != null) {
                DateTime toPostDate = DateTimeFormat.forPattern(dateFormat).parseDateTime(toPostDateString);
                searchCriteria.setToPostDate(toPostDate);
            }

            if (subject != null) {
                subject = "%" + subject + "%";
                searchCriteria.setSubject(subject);
            }
        }

        searchCriteria.setLocale("%" + locale.toString() + "%");

        if (rows == null) {
            rows = defaultCountRows;
        }

        if (page == null) {
            page = defaultStartPage;
        }

        Sort sort = new Sort(Sort.Direction.DESC, defaultColumnSort);
        PageRequest pageRequest = null;

        if (sort == null) {
            pageRequest = new PageRequest(page - 1, rows);
        } else {
            pageRequest = new PageRequest(page - 1, rows, sort);
        }

        Page<Entry> entryPage;

        ArrayList<Category> categories;
        if (categoryId != null) {
            Category currentCategory = categoryService.findById(categoryId);
            categories = Lists.newArrayList(currentCategory.getSubCategories());
            categories.add(currentCategory);
        } else {
            categories = Lists.newArrayList(categoryService.findAll());
        }

        ArrayList<String> categoriesName = new ArrayList<String>();
        for (Category category : categories) {
            categoriesName.add(category.getCategoryId());
        }

        searchCriteria.setCategoriesId(categoriesName);

        logger.info("Criteria: " + searchCriteria.toString());
        entryPage = entryService.findEntryByCriteria(searchCriteria, pageRequest);

        EntryGrid entryGrid = new EntryGrid();
        entryGrid.setCurrentPage(entryPage.getNumber() + 1);
        entryGrid.setTotalPages(entryPage.getTotalPages());
        entryGrid.setTotalRecords(entryPage.getTotalElements());
        entryGrid.setEntryData(Lists.newArrayList(entryPage.iterator()));

        uiModel.addAttribute("entries", entryGrid);
        uiModel.addAttribute("menu", getMenu(categoryId));

        return "blogs/list";
    }

    /************************************************
     * Show post Details
     * @param id
     * @param uiModel
     * @return
     ************************************************/
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String show(
            @PathVariable("id") Long id,
            Model uiModel){

        Entry entry = entryService.findById(id);
        Set<ConstraintViolation<Entry>> violations = validator.validate(entry);

        if (violations.isEmpty()) {
            entry.setImpressions(entry.getImpressions() + 1);
            entryService.save(entry);
        } else {
            logger.error("Invalid post Id: {}. Invalid data, count of errors: " + violations.size(), id);
            for (ConstraintViolation<Entry> violation : violations) {
                logger.error("Path: " + violation.getPropertyPath()
                        + ", message template:" + violation.getMessageTemplate()
                        + ", message: "+ violation.getMessage());
            }
        }

        uiModel.addAttribute("entry", entry);

        UploadItem uploadItem = new UploadItem();
        uploadItem.setBlogId(entry.getId());

        uiModel.addAttribute("uploadItem", uploadItem);
        uiModel.addAttribute("menu", this.getMenu(entry.getCategoryId()));

        return "blogs/show";
    }

    /************************************************
     * Form for creating new post
     * @param uiModel
     * @return
     ************************************************/
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(params = "form", method = RequestMethod.GET)
    public String createForm(Model uiModel, @AuthenticationPrincipal User user){
        Entry entry = new Entry();
        uiModel.addAttribute("entry", entry);
        uiModel.addAttribute("currentDate", new DateTime());
        uiModel.addAttribute("menu", this.getMenu(entry.getCategoryId()));
        return "blogs/create";
    }

    /************************************************
     * Process of saving new post
     * @param entry
     * @param bindingResult
     * @param uiModel
     * @param httpServletRequest
     * @param redirectAttributes
     * @param locale
     * @return
     ************************************************/
    @RequestMapping(params="form", method = RequestMethod.POST)
    public String create(
            @Valid Entry entry,
            BindingResult bindingResult,
            Model uiModel,
            HttpServletRequest httpServletRequest,
            RedirectAttributes redirectAttributes,
            Locale locale
    ) {
        logger.info("Create post: " + entry.getId());
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("message", new Message(
                    "danger",
                    messageSource.getMessage("message_entry_save_fail", new Object[]{}, locale)));
            uiModel.addAttribute("entry", entry);
            uiModel.addAttribute("menu", this.getMenu(entry.getCategoryId()));
            return "blogs/create";
        }
        uiModel.asMap().clear();
        redirectAttributes.addFlashAttribute("message", new Message(
                "success",
                messageSource.getMessage("message_entry_save_success", new Object[]{}, locale)));

        entryService.save(entry);
        return "redirect:/blogs/" + UrlUtil.encodeUrlPathSegment(entry.getId().toString(), httpServletRequest);
    }

    /************************************************
     * Form for updating post
     * @param id
     * @param uiModel
     * @return
     ************************************************/
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/{id}", params = "form", method = RequestMethod.GET)
    public String updateForm(
            @PathVariable("id") Long id,
            Model uiModel,
            HttpServletRequest httpServletRequest,
            RedirectAttributes redirectAttributes,
            @AuthenticationPrincipal User user,
            Locale locale
    ){
        logger.info("Get update form: " + user.toString());
        Entry entry = entryService.findById(id);

        if (!entry.getCreatedBy().equals(user.getUsername())){
            redirectAttributes.addFlashAttribute("message", new Message(
                    "warning",
                    messageSource.getMessage("message_entry_access_fail", new Object[]{}, locale)));
            return "redirect:/blogs/" + UrlUtil.encodeUrlPathSegment(entry.getId().toString(), httpServletRequest);
        }
        uiModel.addAttribute("entry", entry);
        uiModel.addAttribute("currentDate", new DateTime());
        uiModel.addAttribute("menu", this.getMenu(entry.getCategoryId()));
        return "blogs/update";
    }

    /***********************************************
     * Process for saving post
     * @param entry
     * @param bindingResult
     * @param uiModel
     * @param httpServletRequest
     * @param redirectAttributes
     * @param locale
     * @return
     ***********************************************/
    @RequestMapping(value = "/{id}", params = "form", method = RequestMethod.POST)
    public String update(
            @Valid Entry entry,
            BindingResult bindingResult,
            Model uiModel,
            HttpServletRequest httpServletRequest,
            RedirectAttributes redirectAttributes,
            @AuthenticationPrincipal User user,
            Locale locale){

        logger.info("Updating post id: " + entry.getId());
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("message", new Message(
                    "danger",
                    messageSource.getMessage("message_entry_save_fail", new Object[]{}, locale)));
            logger.warn(bindingResult.getAllErrors().toString());
            logger.warn(entry.toString());
            uiModel.addAttribute("entry", entry);
            uiModel.addAttribute("menu", this.getMenu(entry.getCategoryId()));
            return "blogs/update";
        }
        uiModel.asMap().clear();
        redirectAttributes.addFlashAttribute("message", new Message(
                "success",
                messageSource.getMessage("message_entry_save_success", new Object[]{}, locale)));
        entryService.save(entry);
        return "redirect:/blogs/" + UrlUtil.encodeUrlPathSegment(entry.getId().toString(), httpServletRequest);
    }

    /************************************************
     * Return date format by Locale
     * @param locale
     * @return
     ************************************************/
    private String getLocaleDateFormat(Locale locale){
        String dateFormat = messageSource.getMessage("date_format_pattern", new Object[]{}, locale);
        if (dateFormat == null){
            dateFormat = defaultDateFormat;
        }
        return dateFormat;
    }

    /************************************************
     * Return Right Menu
     * @param categoryId
     * @return MenuShow
     ************************************************/
    private MenuShow getMenu(String categoryId){
        logger.info("Get category id: " + categoryId);
        MenuShow menu = new MenuShow();
        List<Category> categories = categoryService.findAll();
        Category currentCategory = null;
        if (categoryId != null) {

            currentCategory = categoryService.findById(categoryId);
            if (currentCategory.getParentCategory() != null){
                logger.info("Get parent category id: " + currentCategory.getParentCategory().getCategoryId());
                menu.setParentCategory(currentCategory.getParentCategory().getCategoryId());
            }
        }
        menu.setCategories(categories);
        menu.setCurrentCategory(categoryId);

        return menu;
    }


}
