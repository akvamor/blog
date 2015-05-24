package ua.org.project.web.controller.front;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.org.project.domain.impl.Comment;
import ua.org.project.rest.controller.CommentRestController;
import javax.servlet.http.HttpServletResponse;

import java.util.*;

/**
 * Created by Dmitry Petrov on 17.06.14.
 */

@Controller
@RequestMapping("/comment")
public class CommentController {

    public static final Comment COMMENT = new Comment();

    @Autowired
    CommentRestController commentRestController;

    @RequestMapping(value = "/list/{id}", method = RequestMethod.GET)
    @ResponseBody
    public List<Comment> listTree(@PathVariable("id") Long id) {
        return commentRestController.listTree(id);
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
        return commentRestController.save(comment, parentId, entryId, user, response);
    }

}
