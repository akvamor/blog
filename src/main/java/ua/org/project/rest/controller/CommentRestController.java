package ua.org.project.rest.controller;

import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.org.project.domain.impl.Comment;
import ua.org.project.domain.impl.Entry;
import ua.org.project.service.CommentService;
import ua.org.project.service.EntryService;
import ua.org.project.web.controller.front.CommentController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.*;

/**
 * Created by Dmitry Petrov on 5/28/14.
 */

@RequestMapping(value = {"/restful/comment"})
@Controller
public class CommentRestController {
    public static final Comment COMMENT = new Comment();
    private static final Logger logger = LoggerFactory.getLogger(CommentRestController.class);

    @Autowired
    private CommentService commentService;

    @Autowired
    private EntryService entryService;

    @Autowired
    private Validator validator;

    @RequestMapping(value = "/list/{id}", method = RequestMethod.GET)
    @ResponseBody
    public List<Comment> listTree(@PathVariable("id") Long id) {
        return commentService.findByEntryIdInTree(id);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(params = "form", value = "/new", method = RequestMethod.GET)
    @ResponseBody
    public Comment createForm(@AuthenticationPrincipal User user){
        return CommentController.COMMENT;
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(params = {"form","entryId"}, method = RequestMethod.POST)
    @ResponseBody
    public Map<String, ? extends Object> save(
            Comment comment,
            @RequestParam(value = "parentId", required = false) Long parentId,
            @RequestParam("entryId") Long entryId,
            @AuthenticationPrincipal User user,
            HttpServletResponse response
    ) {
        if (comment.getId() == null) {
            comment.setCreatedBy(user.getUsername());
            comment.setCreatedDate(new LocalDateTime());
            comment.setPostDate(new LocalDateTime());
            comment.setPostBy(user.getUsername());
            comment.setCountLikes(0l);
            comment.setCountNotLikes(0l);
        }
        if (parentId != null){
            Comment commentParent = commentService.findById(parentId);
            comment.setParentComment(commentParent);
        }

        Entry entry = entryService.findById(entryId);
        comment.setId(null);
        comment.setEntry(entry);

        logger.info("Date" + comment.getCreatedDate());
        Set<ConstraintViolation<Comment>> failures = validator.validate(comment);
        if (!failures.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return validationMessages(failures);
        } else {
            commentService.save(comment);
        }
        return Collections.singletonMap("comment", comment);
    }


    private Map<String, String> validationMessages(Set<ConstraintViolation<Comment>> failures) {
        Map<String, String> failureMessages = new HashMap<String, String>();
        for (ConstraintViolation<Comment> failure : failures) {
            failureMessages.put(failure.getPropertyPath().toString(), failure.getMessage());
        }
        return failureMessages;
    }

}
