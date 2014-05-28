package ua.org.project.repository;

import org.joda.time.DateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import ua.org.project.domain.impl.Entry;

import java.util.List;

/**
 * Created by Dmitry Petrov on 5/28/14.
 */
public interface EntryRepository extends PagingAndSortingRepository<Entry, Long> {
    public List<Entry> findByCategoryId(String categoryId);

    public Page<Entry> findEntryByCriteria(
            @Param("subject") String subject,
            @Param("categoryId") String categoryId,
            @Param("fromPostDate") DateTime fromPostDate,
            @Param("toPostDate") DateTime toPostDate,
            Pageable pageable);

}
