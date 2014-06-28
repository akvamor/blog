package ua.org.project.rest.controller;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.org.project.auditor.AuditorAwareBean;
import ua.org.project.domain.impl.Comment;
import ua.org.project.domain.impl.Entry;
import ua.org.project.service.CommentService;
import ua.org.project.service.EntryService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validator;
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

    @Autowired
    private EntryService entryService;

    @Autowired
    private Validator validator;

    @Autowired
    private AuditorAwareBean auditorAwareBean;

    @RequestMapping(value = "/list/{id}", method = RequestMethod.GET)
    @ResponseBody
    public List<Comment> listTree(@PathVariable("id") Long id) {
        return commentService.findByEntryIdInTree(id);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(params = "form",value = "/new", method = RequestMethod.GET)
    @ResponseBody
    public Comment createForm(@AuthenticationPrincipal User user){
        Comment comment = new Comment();
        return comment;
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
            comment.setCreatedDate(new DateTime());
            comment.setPostDate(new DateTime());
            comment.setPostBy(auditorAwareBean.getCurrentAuditor());
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
