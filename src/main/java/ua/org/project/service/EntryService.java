package ua.org.project.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ua.org.project.domain.impl.Entry;
import ua.org.project.domain.SearchCriteria;

import java.util.List;

/**
 * Created by Dmitry Petrov on 5/28/14.
 */
public interface EntryService {
    public List<Entry> findAll();
    public Entry findById(Long id);
    public List<Entry> findByCategoryId(String categoryId);
    public Entry save(Entry entry);
    public void delete(Entry entry);
    public Page<Entry> findAllByPage(Pageable pageable);
    public Page<Entry> findEntryByCriteria(SearchCriteria searchCriteria, Pageable pageable);
}
