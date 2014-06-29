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

    @Query("select e from Entry e where e.categoryId = :categoryId and e.locale like :locale and e.postDate between :fromPostDate and :toPostDate and e.isDeleted = :isDeleted")
    public Page<Entry> findEntryByCategoryAndLocaleAndPostDate(
            @Param("categoryId")String categoryId,
            @Param("locale") String locale,
            @Param("fromPostDate") DateTime fromPostDate,
            @Param("toPostDate") DateTime toPostDate,
            @Param("isDeleted") Boolean isDeleted,
            Pageable pageable);

    @Query("select e from Entry e where e.categoryId = :categoryId and e.locale like :locale and e.isDeleted = :isDeleted")
    public Page<Entry> findEntryByCategoryAndLocale(
            @Param("categoryId")String categoryId,
            @Param("locale") String locale,
            @Param("isDeleted") Boolean isDeleted,
            Pageable pageable);

    @Query("select e from Entry e where e.locale like :locale and e.postDate between :fromPostDate and :toPostDate and e.isDeleted = :isDeleted")
    public Page<Entry> findEntryByLocaleAndPostDate(
            @Param("locale") String locale,
            @Param("fromPostDate") DateTime fromPostDate,
            @Param("toPostDate") DateTime toPostDate,
            @Param("isDeleted") Boolean isDeleted,
            Pageable pageable);

    @Query("select e from Entry e where e.subject like :subject and e.categoryId like :categoryId and e.locale like :locale and e.postDate between :fromPostDate and :toPostDate and e.isDeleted = :isDeleted")
    public Page<Entry> searchEntryByCriteria(
            @Param("subject") String subject,
            @Param("categoryId") String categoryId,
            @Param("fromPostDate") DateTime fromPostDate,
            @Param("toPostDate") DateTime toPostDate,
            @Param("locale") String locale,
            @Param("isDeleted") Boolean isDeleted,
            Pageable pageable);

    @Query("select e from Entry e where e.categoryId = :categoryId")
    public Page<Entry> findAllByCategory(
            @Param("categoryId") String categoryId,
            Pageable pageable);

}
