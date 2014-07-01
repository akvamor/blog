package ua.org.project.service.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.org.project.domain.impl.EntryAttachment;
import ua.org.project.repository.EntryAttachmentRepository;
import ua.org.project.service.EntryAttachmentService;

/**
 * Created by Dmitry Petrov on 5/28/14.
 */

@Service("entryAttachmentService")
@Repository
@Transactional
public class EntryAttachmentServiceImpl implements EntryAttachmentService {

    @Autowired
    private EntryAttachmentRepository attachmentRepository;

    @Override
    @Transactional(readOnly=true)
    public EntryAttachment findById(Long id) {
        return attachmentRepository.findOne(id);
    }

    @Override
    public void delete(EntryAttachment entryAttachment) {
        attachmentRepository.delete(entryAttachment);
    }

}

