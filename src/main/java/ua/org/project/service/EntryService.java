package ua.org.project.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ua.org.project.domain.impl.Entry;
import ua.org.project.domain.SearchCriteria;

import java.util.List;
import java.util.Locale;

/**
 * Created by Dmitry Petrov on 5/28/14.
 */
public interface EntryService {
    public Page<Entry> findAll(Pageable pageable);
    public Entry findById(Long id);
    public Entry save(Entry entry);
    public Page<Entry> findAllByPage(Pageable pageable);
    public Page<Entry> findAllByCategory(String category, Pageable pageable);
    public Page<Entry> findEntryByCriteria(SearchCriteria searchCriteria, Pageable pageable);
    public void increaseImpression(Entry entry);
}
