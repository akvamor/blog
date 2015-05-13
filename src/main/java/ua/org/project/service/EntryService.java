package ua.org.project.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ua.org.project.domain.impl.Entry;
import ua.org.project.domain.SearchCriteria;

import java.util.Set;

/**
 * Created by Dmitry Petrov on 5/28/14.
 */
public interface EntryService {
    Page<Entry> findAll(Pageable pageable);
    Entry findById(Long id);
    Entry save(Entry entry);
    Page<Entry> findAllByPage(Pageable pageable);
    Page<Entry> findAllByCategory(Set<String> category, Pageable pageable);
    Page<Entry> findEntryByCriteria(SearchCriteria searchCriteria, Pageable pageable);
    void increaseImpression(Entry entry);
}
