package ua.org.project.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ua.org.project.domain.SearchCriteriaCategory;
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
    public Page<Entry> findByCategoryId(SearchCriteriaCategory criteriaCategory, Pageable pageable);
    public Entry save(Entry entry);
    public void delete(Entry entry);
    public Page<Entry> findAllByPage(Pageable pageable);
    public Page<Entry> findEntryByCriteria(SearchCriteria searchCriteria, Pageable pageable);
    public Page<Entry> findEntryByLocale(String locale, Pageable pageable);
    public void increaseImpression(Entry entry);
}
