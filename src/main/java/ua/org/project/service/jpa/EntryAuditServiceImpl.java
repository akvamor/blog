package ua.org.project.service.jpa;

import com.google.common.collect.Lists;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.org.project.domain.impl.Entry;
import ua.org.project.service.EntryAuditService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dmitry Petrov on 5/28/14.
 */
@Service("entryAuditService")
@Repository
@Transactional
public class EntryAuditServiceImpl implements EntryAuditService{

    @PersistenceContext
    private EntityManager em;

    @Transactional(readOnly = true)
    public List<Entry> findAuditById(Long id) {
        AuditReader auditReader = AuditReaderFactory.get(em);
        List<Number> revisions = auditReader.getRevisions(Entry.class, id);
        revisions = Lists.reverse(revisions);
        List<Entry> entries = new ArrayList<Entry>();
        Entry entry;
        for (Number revision : revisions) {
            entry = auditReader.find(Entry.class, id, revision);
            entries.add(entry);
        }
        return entries;
    }
}
