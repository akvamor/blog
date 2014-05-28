package ua.org.project.service.jpa;

import com.google.common.collect.Lists;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.org.project.domain.SearchCriteria;
import ua.org.project.domain.impl.Entry;
import ua.org.project.repository.EntryRepository;
import ua.org.project.service.EntryService;

import java.util.List;

/**
 * Created by Dmitry Petrov on 5/28/14.
 */
@Service("entryService")
@Repository
@Transactional
public class EntryServiceImpl implements EntryService {

    @Autowired
    private EntryRepository entryRepository;

    @Transactional(readOnly = true)
    public List<Entry> findAll() {
        return Lists.newArrayList(entryRepository.findAll());
    }

    @Transactional(readOnly = true)
    public Entry findById(Long id) {
        return entryRepository.findOne(id);
    }

    @Transactional(readOnly = true)
    public List<Entry> findByCategoryId(String categoryId) {
        return entryRepository.findByCategoryId(categoryId);
    }


    public Entry save(Entry entry) {
        return entryRepository.save(entry);
    }

    @Override
    public void delete(Entry entry) {
        entryRepository.delete(entry);
    }

    @Transactional(readOnly = true)
    public Page<Entry> findAllByPage(Pageable pageable) {
        return entryRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Page<Entry> findEntryByCriteria(SearchCriteria searchCriteria, Pageable pageable) {
        String subject = searchCriteria.getSubject();
        String categoryId = searchCriteria.getCategoryId();
        DateTime fromPostDate = searchCriteria.getFromPostDate();
        DateTime toPostDate = searchCriteria.getToPostDate();
        return entryRepository.findEntryByCriteria(subject, categoryId, fromPostDate, toPostDate, pageable);
    }
}
