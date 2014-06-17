package ua.org.project.web.blogapp.front.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Dmitry Petrov on 17.06.14.
 */

@Controller
@RequestMapping("comment")
public class CommentController {

    @PreAuthorize("isAuthenticated()")
    public String dialog(){
        return "comments/dialog";
    }

    @PreAuthorize("isAuthenticated()")
    public String edit(){
        return "comments/edit";
    }
}
