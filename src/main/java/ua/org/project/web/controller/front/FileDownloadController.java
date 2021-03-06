package ua.org.project.web.controller.front;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.org.project.domain.Attachment;
import ua.org.project.domain.impl.CommentAttachment;
import ua.org.project.domain.impl.EntryAttachment;
import ua.org.project.service.CommentAttachmentService;
import ua.org.project.service.EntryAttachmentService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Dmitry Petrov on 5/31/14.
 */
@RequestMapping("/blogs/filedownload")
@Controller
public class FileDownloadController {
    final Logger logger = LoggerFactory.getLogger(FileDownloadController.class);

    @Autowired
    private EntryAttachmentService entryAttachmentService;

    @Autowired
    private CommentAttachmentService commentAttachmentService;

    @RequestMapping(value = "/entry/{id}", method = RequestMethod.GET, produces = "application/force-download")
    @ResponseBody
    public byte[] downloadEntryAttachment(
            @PathVariable("id") Long id,
            HttpServletRequest request,
            HttpServletResponse response) {
        logger.info("Processing download for entry attachment with id {}", id);
        EntryAttachment entryAttachment = entryAttachmentService.findById(id);

        response.setContentType(entryAttachment.getContentType());
        response.setContentLength(entryAttachment.getFileData().length);
        response.setHeader("Content-Disposition", "attachment; filename=\"" + entryAttachment.getFileName() + "\"");

        return entryAttachment.getFileData();
    }

    @RequestMapping(value = "/comment/{id}", method = RequestMethod.GET, produces = "application/force-download")
    @ResponseBody
    public byte[] downloadCommentAttachment(
            @PathVariable("id") Long id,
            HttpServletResponse response) {
        logger.info("Processing download for comment attachment with id {}", id);
        CommentAttachment commentAttachment = commentAttachmentService.findById(id);

        response.setContentType(commentAttachment.getContentType());
        response.setContentLength(commentAttachment.getFileData().length);
        response.setHeader("Content-Disposition", "attachment; filename=\"" + commentAttachment.getFileName() + "\"");

        return commentAttachment.getFileData();
    }

    @RequestMapping(value = "/images/{type}/{id}", method = RequestMethod.GET)
    @ResponseBody
    public void showPhoto(
            @PathVariable("type") String type,
            @PathVariable("id") Long id,
            HttpServletResponse response){
        Attachment attachment;

        if (type.equals("entry")){
            attachment = entryAttachmentService.findById(id);
        } else {
            attachment = commentAttachmentService.findById(id);
        }
        if (attachment.getFileData() != null) {
            logger.info("Download photo for {}, id: {}", type, id);
        }
        response.setContentType("image/jpeg, image/jpg, image/png, image/gif");

        try {
            response.getOutputStream().write(attachment.getFileData());
            response.getOutputStream().close();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

}
