package ua.org.project.service.jpa;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.org.project.domain.Impression;
import ua.org.project.domain.SearchCriteria;
import ua.org.project.domain.impl.Entry;
import ua.org.project.repository.EntryLikeRepository;
import ua.org.project.repository.EntryRepository;
import ua.org.project.repository.ImpressionRepository;
import ua.org.project.service.EntryService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
        entry = entryRepository.save(entry);
        return entry;
    }

    public void increaseImpression(Entry entry){
        Impression impression = entry.getImpressions();
        impression.setQuantity(impression.getQuantity() + 1);
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
    public Page<Entry> findAllByCategory(String category, Pageable pageable) {
        Page<Entry> entries = entryRepository.findAllByCategory(category, pageable);
        this.setLikes(entries.getContent());
        return entries;
    }

    @Transactional(readOnly = true)
    public Page<Entry> findEntryByCriteria(SearchCriteria searchCriteria, Pageable pageable) {
        String subject = searchCriteria.getSubject();
        String categoryId = searchCriteria.getCategoryId();
        DateTime fromPostDate = searchCriteria.getFromPostDate();
        DateTime toPostDate = searchCriteria.getToPostDate();
        String locale = searchCriteria.getLocale();

        Page<Entry> entries;
        if (searchCriteria.isSearch()){
            entries = entryRepository.searchEntryByCriteria(subject, categoryId, fromPostDate, toPostDate, locale, searchCriteria.isDeleted(), pageable);
        } else if (categoryId != null && locale != null && fromPostDate != null && toPostDate!= null){
            entries = entryRepository.findEntryByCategoryAndLocaleAndPostDate(categoryId, locale, fromPostDate, toPostDate, searchCriteria.isDeleted(), pageable);
        } else if (categoryId != null && locale != null && searchCriteria.isShowUnPosted()){
            entries = entryRepository.findEntryByCategoryAndLocale(categoryId, locale, searchCriteria.isDeleted(), pageable);
        } else {
            entries = entryRepository.findEntryByLocaleAndPostDate(locale, fromPostDate, toPostDate, searchCriteria.isDeleted(), pageable);
        }

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
