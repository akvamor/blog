package ua.org.project.web.blogapp.front.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ua.org.project.domain.impl.Entry;
import ua.org.project.service.CategoryService;
import ua.org.project.service.EntryService;

import java.util.List;

/**
 * Created by Dmitry Petrov on 5/29/14.
 */
@RequestMapping(value={"/", "/index"})
@Controller
public class EntryController {
    final Logger logger = LoggerFactory.getLogger(EntryController.class);

    @Qualifier("messageSource")
    @Autowired
    private MessageSource messageSource;

    @Autowired
    private EntryService entryService;

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(
            method = RequestMethod.GET)
    public String list(
            Model uiModel,
            @RequestParam(value = "page",       required = false) Integer page,
            @RequestParam(value = "rows",       required = false) Integer rows,
            @RequestParam(value = "_search",    required = false) boolean isSeach,
            @RequestParam(value = "subject",    required = false) String subject,
            @RequestParam(value = "categoryId", required = false) String categoryId,
            @RequestParam(value = "fromPostDate",required = false) String fromPostDateString,
            @RequestParam(value = "toPostDate", required = false) String toPostDateString) {
        logger.info("Listing blog entries");
        logger.info("-----------------------------------");
        logger.info(uiModel.toString());

        List<Entry> entries = entryService.findAll();
        uiModel.addAttribute("entries", entries);

        return "blogs/list";
    }
}
