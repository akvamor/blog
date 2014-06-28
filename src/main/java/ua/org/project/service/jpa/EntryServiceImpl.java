package ua.org.project.service.jpa;

import com.google.common.collect.Lists;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.org.project.domain.SearchCriteria;
import ua.org.project.domain.SearchCriteriaCategory;
import ua.org.project.domain.impl.Entry;
import ua.org.project.repository.EntryLikeRepository;
import ua.org.project.repository.EntryRepository;
import ua.org.project.service.EntryService;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Collections;
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

    @Autowired
    private EntryLikeRepository entryLikeRepository;

    @PersistenceContext
    private EntityManager em;

    @Transactional(readOnly = true)
    public Page<Entry> findAll(Pageable pageable) {
        Page<Entry> entries = entryRepository.findAll(pageable);
        this.setLikes(entries.getContent());
        return entries;
    }

    @Transactional(readOnly = true)
    public Entry findById(Long id) {
        Entry entry = entryRepository.findOne(id);
        this.setLikes(Collections.singletonList(entry));
        return entry;
    }

    @Transactional(readOnly = true)
    public Page<Entry> findByCategoryId(SearchCriteriaCategory criteriaCategory, Pageable pageable) {
        Page<Entry> entries = entryRepository.findByCategoryId(
                criteriaCategory.getCategoryId(), criteriaCategory.getLocale(),pageable);
        this.setLikes(entries.getContent());
        return entries;
    }

    public Entry save(Entry entry) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username ;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        entry.setLastModifiedBy(username);
        entry.setLastModifiedDate(new DateTime());
        return entryRepository.save(entry);
    }

    public void delete(Entry entry) {
        entryRepository.delete(entry);
    }

    @Transactional(readOnly = true)
    public Page<Entry> findAllByPage(Pageable pageable) {
        Page<Entry> entries = entryRepository.findAll(pageable);
        this.setLikes(entries.getContent());
        return entries;
    }

    @Transactional(readOnly = true)
    public Page<Entry> findEntryByCriteria(SearchCriteria searchCriteria, Pageable pageable) {
        String subject = searchCriteria.getSubject();
        String categoriesId = searchCriteria.getCategoryId();
        DateTime fromPostDate = searchCriteria.getFromPostDate();
        DateTime toPostDate = searchCriteria.getToPostDate();
        String locale = searchCriteria.getLocale();
        Page<Entry> entries = entryRepository.findEntryByCriteria(subject, categoriesId, fromPostDate, toPostDate, locale, pageable);
        this.setLikes(entries.getContent());
        return entries;
    }

    @Transactional(readOnly = true)
    public Page<Entry> findEntryByLocale(String locale, Pageable pageable) {
        Page<Entry> entries = entryRepository.findEntryByLocale(locale, pageable);
        this.setLikes(entries.getContent());
        return entries;
    }

    private void setLikes(List<Entry> entries) {
        for (Entry entry : entries) {
            entry.setCountLikes(entryLikeRepository.countAllLike(entry.getId()));
            entry.setCountNotLikes(entryLikeRepository.countAllNotLike(entry.getId()));
        }
    }
}
