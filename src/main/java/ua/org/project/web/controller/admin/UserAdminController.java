package ua.org.project.web.controller.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.org.project.web.form.EntryGrid;

import java.util.List;

/**
 * Created by Dmitry Petrov on 5/31/14.
 */
@RequestMapping("/admin")
@Controller
public class UserAdminController {

    private static final Logger logger = LoggerFactory.getLogger(UserAdminController.class);

//    @Autowired
//    private EntryAuditService entryAuditService;

    @PreAuthorize("ROLE_ADMIN")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public EntryGrid listEntryAudit(@PathVariable("id") Long id){
        logger.info("Ged Audit Records for Entry with id: {}", id);

//        List<Entry> auditEntries = entryAuditService.findAuditById(id);
//        EntryGrid entryGrid = new EntryGrid();
//        entryGrid.setCurrentPage(1);
//        entryGrid.setTotalPages(1);
//        entryGrid.setTotalRecords(auditEntries.size());
//        entryGrid.setEntryData(auditEntries);

        return null;
    }

}
