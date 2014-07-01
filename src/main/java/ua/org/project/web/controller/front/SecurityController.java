package ua.org.project.web.controller.front;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.org.project.web.form.Message;

import java.util.Locale;

/**
 * Created by Dmitry Petrov on 5/20/14.
 */

@RequestMapping("/security")
@Controller
public class SecurityController {
    final Logger logger = LoggerFactory.getLogger(SecurityController.class);

    @Autowired
    private MessageSource messageSource;

    @RequestMapping("/loginfail")
    public String loginFail(Model uiModel, Locale locale) {
        logger.info("Login fail");
        uiModel.addAttribute(
                "message",
                new Message(
                        "danger",
                        messageSource.getMessage(
                                "message_login_fail",
                                new Object[]{},
                                locale)));
        return "blogs/list";
    }
}
