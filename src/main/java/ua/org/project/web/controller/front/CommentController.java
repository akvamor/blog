package ua.org.project.web.controller.front;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.org.project.domain.Sample;
import ua.org.project.domain.impl.Comment;
import ua.org.project.domain.rest.CommentRest;
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
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private Validator validator;

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(params = "form", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, ? extends Object> save(
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
