package ua.org.project.web.blogapp.front.controller;

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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ua.org.project.domain.Category;
import ua.org.project.domain.SearchCriteria;
import ua.org.project.domain.impl.Entry;
import ua.org.project.service.CategoryService;
import ua.org.project.service.EntryService;
import ua.org.project.web.blogapp.front.form.EntryGrid;
import ua.org.project.web.blogapp.front.form.MenuShow;
import ua.org.project.web.blogapp.front.form.UploadItem;

import javax.validation.Validator;
import javax.validation.ConstraintViolation;
import java.util.*;

/**
 * Created by Dmitry Petrov on 5/29/14.
 *
 * This controller is responsible for posts
 */
@RequestMapping(value={"/", "/index", "/blogs"})
@Controller
public class EntryController {

    final Logger logger = LoggerFactory.getLogger(EntryController.class);
    String defaultColumnSort = "postDate";
    int defaultCountRows = 10;
    int defaultStartPage = 1;
    String defaultDateFormat = "yyyy-MM-dd";

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private EntryService entryService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private Validator validator;

    /**
     * Show list of Posts by criteria and without.
     * @param uiModel
     * @param locale
     * @param page
     * @param rows
     * @param isSeach
     * @param subject
     * @param categoryId
     * @param fromPostDateString
     * @param toPostDateString
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public String list(
            Model uiModel,
            Locale locale,
            @RequestParam(value = "page",       required = false) Integer page,
            @RequestParam(value = "rows",       required = false) Integer rows,
            @RequestParam(value = "_search",    required = false) boolean isSeach,
            @RequestParam(value = "subject",    required = false) String subject,
            @RequestParam(value = "categoryId", required = false) String categoryId,
            @RequestParam(value = "fromPostDate",required = false) String fromPostDateString,
            @RequestParam(value = "toPostDate", required = false) String toPostDateString) {

        SearchCriteria searchCriteria = new SearchCriteria();
        if (isSeach) {
            String dateFormat = this.getLocaleDateFormat(locale);

            logger.info("Listing blog entries");
            logger.info("Date Format: " + locale.toString());

            DateTime fromPostDate;
            if (fromPostDateString == null)
                fromPostDate = new DateTime(1900, 1, 1, 0, 0);
            else
                fromPostDate = DateTimeFormat.forPattern(dateFormat).parseDateTime(fromPostDateString);

            DateTime toPostDate;
            if (toPostDateString == null)
                toPostDate = new DateTime(2200, 12, 31, 23, 59);
            else
                toPostDate = DateTimeFormat.forPattern(dateFormat).parseDateTime(toPostDateString);

            if (subject == null)
                subject = "%";
            else
                subject = "%" + subject + "%";

            String categoryIdQuery = "";
            if (categoryId == null)
                categoryIdQuery = "%";
            else
                categoryIdQuery = categoryId + "%";

            String localeStr = "%" + locale.toString() + "%";

            searchCriteria.setSubject(subject);
            logger.info("Subject: " + subject);
            searchCriteria.setCategoryId(categoryIdQuery);
            logger.info("CategoryID: " + categoryIdQuery);
            searchCriteria.setFromPostDate(fromPostDate);
            logger.info("From post date: " + fromPostDate);
            searchCriteria.setToPostDate(toPostDate);
            logger.info("To post date: " + toPostDate);
            searchCriteria.setLocale(localeStr);
            logger.info("Locale: " + localeStr);
        }


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
        if (isSeach) {
            entryPage = entryService.findEntryByCriteria(searchCriteria, pageRequest);
        } else {
            entryPage = entryService.findEntryByLocale("%" + locale.toString() + "%", pageRequest);
        }

        EntryGrid entryGrid = new EntryGrid();
        entryGrid.setCurrentPage(entryPage.getNumber() + 1);
        entryGrid.setTotalPages(entryPage.getTotalPages());
        entryGrid.setTotalRecords(entryPage.getTotalElements());
        entryGrid.setEntryData(Lists.newArrayList(entryPage.iterator()));

        List<Category> categories = categoryService.findAll();

        uiModel.addAttribute("entries", entryGrid);
        logger.info("Count of categories: " + categories.size());
        uiModel.addAttribute("menu", getMenu(categoryId));
        logger.info("Current page: " + entryGrid.getCurrentPage());
        logger.info("Count entries: " + entryPage.getTotalElements());

        return "blogs/list";
    }

    /**
     * Show post Details
     * @param id
     * @param uiModel
     * @return
     */
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

    /**
     * Return date format by Locale
     * @param locale
     * @return
     */
    private String getLocaleDateFormat(Locale locale){
        String dateFormat = messageSource.getMessage("date_format_pattern", new Object[]{}, locale);
        if (dateFormat == null){
            dateFormat = defaultDateFormat;
        }
        return dateFormat;
    }

    /**
     * Return Right Menu
     * @param categoryId
     * @return MenuShow
     */
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
