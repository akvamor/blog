package ua.org.project.service;

import ua.org.project.domain.impl.EntryAttachment;

/**
 * Created by Dmitry Petrov on 5/28/14.
 */
public interface EntryAttachmentService {
    EntryAttachment findById(Long id);
    void delete(EntryAttachment entryAttachment);
}
