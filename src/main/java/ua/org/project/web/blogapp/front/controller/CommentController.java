package ua.org.project.web.blogapp.front.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.org.project.domain.Sample;
import ua.org.project.domain.impl.Comment;
import ua.org.project.domain.json.CommentJson;
import ua.org.project.service.CommentService;
import ua.org.project.service.EntryService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validator;
import java.util.*;

/**
 * Created by Dmitry Petrov on 17.06.14.
 */

@Controller
@RequestMapping("/comment")
public class CommentController {

    final Logger logger = LoggerFactory.getLogger(CommentController.class);

    @Autowired
    private CommentService commentService;

    @Autowired
    private EntryService entryService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private Validator validator;

    @RequestMapping(params = "form", method = RequestMethod.GET)
    @ResponseBody
    public Comment create(){
        Comment comment = new Comment();
        return comment;
    }

    @RequestMapping(value = "/listByEntry/{id}", method = RequestMethod.GET)
    @ResponseBody
    public List<CommentJson> list(@PathVariable("id") Long id, Locale locale){
        logger.info("Get list of comment for entryId: " + id);
        String format = messageSource.getMessage("date_format_pattern_comments", new Object[]{}, locale);
        List<CommentJson> commentsJson = commentService.findTreeByEntryId(id, format);
        return commentsJson;
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(params = "sample", method = RequestMethod.GET)
    @ResponseBody
    public Sample createSample(){
        Sample sample = new Sample();
        return sample;
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(params = "form", method = RequestMethod.POST)
    @ResponseBody
    public  Map<String, ? extends Object> save(
            @RequestBody @Valid Comment comment,
            HttpServletResponse response
    ){
        Set<ConstraintViolation<Comment>> failures = validator.validate(comment);
        if (!failures.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return validationMessages(failures);
        } else {
            return Collections.singletonMap("id", comment.getId());
        }
    }

    /**
     * Convert set of ConstraintViolation to Map of errors
     * @param failures
     * @return
     */
    private Map<String, String> validationMessages(Set<ConstraintViolation<Comment>> failures){
        Map<String, String> failureMessages = new HashMap<String, String>();
        for (ConstraintViolation<Comment> failure : failures) {
            failureMessages.put(failure.getPropertyPath().toString(), failure.getMessage());
        }
        return failureMessages;
    }
}
