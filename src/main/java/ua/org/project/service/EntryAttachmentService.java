package ua.org.project.service;

import ua.org.project.domain.impl.EntryAttachment;

/**
 * Created by Dmitry Petrov on 5/28/14.
 */
public interface EntryAttachmentService {
    public EntryAttachment findById(Long id);
    public void delete(EntryAttachment entryAttachment);
}
