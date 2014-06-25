package ua.org.project.rest.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.org.project.domain.rest.CommentRest;
import ua.org.project.service.CommentService;

import java.util.*;

/**
 * Created by Dmitry Petrov on 5/28/14.
 */

@RequestMapping(value = {"/restful/comment"})
@Controller
public class CommentRestController {
    private static final Logger logger = LoggerFactory.getLogger(CommentRestController.class);

    @Autowired
    private CommentService commentService;

    @Autowired
    private MessageSource messageSource;


    @RequestMapping(value = "/listByEntry/{id}", method = RequestMethod.GET)
    @ResponseBody
    public List<CommentRest> list(@PathVariable("id") Long id, Locale locale){
        logger.info("Get list of comment for entryId: " + id);
        String format = messageSource.getMessage("date_format_pattern_comments", new Object[]{}, locale);
        List<CommentRest> comments = commentService.getCommentRestTree(id, format);
        return comments;
    }

}
