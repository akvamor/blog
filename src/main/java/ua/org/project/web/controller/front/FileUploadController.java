package ua.org.project.web.controller.front;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.org.project.domain.impl.Comment;
import ua.org.project.domain.impl.CommentAttachment;
import ua.org.project.domain.impl.Entry;
import ua.org.project.domain.impl.EntryAttachment;
import ua.org.project.service.CommentService;
import ua.org.project.service.EntryService;
import ua.org.project.web.form.UploadItem;
import ua.org.project.web.form.Message;
import ua.org.project.util.UrlUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.Locale;

/**
 * Created by Dmitry Petrov on 5/31/14.
 */
@RequestMapping("/blogs/fileupload")
@Controller
public class FileUploadController {

    final Logger logger = LoggerFactory.getLogger(FileUploadController.class);

    @Autowired
    EntryService entryService;

    @Autowired
    CommentService commentService;

    @Autowired
    MessageSource messageSource;

    @RequestMapping(method = RequestMethod.POST)
    public String processUpload(
            UploadItem uploadItem,
            BindingResult result,
            Model uiModel,
            HttpServletRequest request,
            HttpServletResponse response,
            RedirectAttributes redirectAttributes,
            Locale locale,
            @RequestParam("file")Part file) throws IOException {

        Long blogId = uploadItem.getBlogId();
        Long commentId = uploadItem.getCommentId();
        String uploadType = uploadItem.getUploadType();
        Message message;

        if (file.getSize() > 0){
            if (uploadType.equalsIgnoreCase("entry")){
                EntryAttachment entryAttachment = new EntryAttachment();
                entryAttachment.setFileName(getFileName(file));
                entryAttachment.setFileData(org.apache.commons.io.IOUtils.toByteArray(file.getInputStream()));
                entryAttachment.setContentType(file.getContentType());

                Entry entry = entryService.findById(blogId);
                entryAttachment.setEntry(entry);
                entry.addAttachment(entryAttachment);
                entryService.save(entry);
            } else {
                CommentAttachment commentAttachment = new CommentAttachment();
                commentAttachment.setFileName(getFileName(file));
                commentAttachment.setFileData(org.apache.commons.io.IOUtils.toByteArray(file.getInputStream()));
                commentAttachment.setContentType(file.getContentType());

                Comment comment = commentService.findById(commentId);
                commentAttachment.setComment(comment);
                commentService.save(comment);
            }

            message = new Message(
                    "success",
                    messageSource.getMessage(
                            "message_blog_attachment_file_successfully",
                            new Object[]{getFileName(file)},
                            locale));
        } else {
            redirectAttributes.addFlashAttribute("validationResult", "error");
            message = new Message(
                    "error",
                    messageSource.getMessage(
                            "message_blog_attachment_file_error",
                            new Object[]{},
                            locale));
        }
        redirectAttributes.addFlashAttribute("message", message);

        return "redirect:/blogs/" + UrlUtil.encodeUrlPathSegment(blogId.toString(), request);
    }

    private String getFileName(Part file){
        String fileHeader = file.getHeader("content-disposition");
        logger.info("Part Header = " + fileHeader);
        for (String cd : file.getHeader("content-disposition").split(";")){
            if (cd.trim().startsWith("filename")) {
                return cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }

}
