package ua.org.project.service;

import ua.org.project.domain.impl.Entry;

import java.util.List;

/**
 * Created by Dmitry Petrov on 5/28/14.
 */
public interface EntryAuditService {
    public List<Entry> findAuditById(Long id);
}
