package ua.org.project.repository;

import org.joda.time.DateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import ua.org.project.domain.impl.Entry;

import java.util.Collection;
import java.util.List;

/**
 * Created by Dmitry Petrov on 5/28/14.
 */
public interface EntryRepository extends PagingAndSortingRepository<Entry, Long> {
    public List<Entry> findByCategoryId(String categoryId);

    @Query("select e from Entry e where e.subject like :subject and e.categoryId in :categoryId and e.locale like :locale and e.postDate between :fromPostDate and :toPostDate")
    public Page<Entry> findEntryByCriteria(
            @Param("subject") String subject,
            @Param("categoryId") Collection<String> categoryId,
            @Param("fromPostDate") DateTime fromPostDate,
            @Param("toPostDate") DateTime toPostDate,
            @Param("locale") String locale,
            Pageable pageable);

    @Query("select e from Entry e where e.locale like :locale")
    public Page<Entry> findEntryByLocale(
            @Param("locale") String locale,
            Pageable pageable);
}
