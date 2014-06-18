package ua.org.project.web.blogapp.front.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.org.project.domain.Sample;
import ua.org.project.domain.impl.Comment;
import ua.org.project.service.CommentService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validator;
import java.util.*;

/**
 * Created by Dmitry Petrov on 17.06.14.
 */

@Controller
@RequestMapping("comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private Validator validator;

    @RequestMapping(params = "form", method = RequestMethod.GET)
    @ResponseBody
    public Comment create(){
        Comment comment = new Comment();
        return comment;
    }

    @RequestMapping(value = "list/{id}", method = RequestMethod.GET)
    @ResponseBody
    public List<Comment> list(
            @PathVariable("id") Long id
    ){
        List<Comment> comments = commentService.findByEntryId(id);

        return commentService.findByParentId(id);
    }

    @RequestMapping(params = "sample", method = RequestMethod.GET)
    @ResponseBody
    public Sample createSample(){
        Sample sample = new Sample();
        return sample;
    }

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

    private Map<String, String> validationMessages(Set<ConstraintViolation<Comment>> failures){
        Map<String, String> failureMessages = new HashMap<String, String>();
        for (ConstraintViolation<Comment> failure : failures) {
            failureMessages.put(failure.getPropertyPath().toString(), failure.getMessage());
        }
        return failureMessages;
    }
}
