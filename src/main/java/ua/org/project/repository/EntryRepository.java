package ua.org.project.repository;

import org.joda.time.DateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import ua.org.project.domain.impl.Entry;

import java.util.Collection;

/**
 * Created by Dmitry Petrov on 5/28/14.
 */
public interface EntryRepository extends PagingAndSortingRepository<Entry, Long> {

    @Query("select e from Entry e where e.category.categoryId in :categories  and e.locale like :locale and e.postDate between :fromPostDate and :toPostDate and e.isDeleted = :isDeleted")
    Page<Entry> findEntryByCategoryAndLocaleAndPostDate(
            @Param("categories")Collection<String> categoryId,
            @Param("locale") String locale,
            @Param("fromPostDate") DateTime fromPostDate,
            @Param("toPostDate") DateTime toPostDate,
            @Param("isDeleted") Boolean isDeleted,
            Pageable pageable);

    @Query("select e from Entry e where e.category.categoryId in :categories  and e.locale like :locale and e.isDeleted = :isDeleted")
    Page<Entry> findEntryByCategoryAndLocale(
            @Param("categories")Collection<String> categories,
            @Param("locale") String locale,
            @Param("isDeleted") Boolean isDeleted,
            Pageable pageable);

    @Query("select e from Entry e where e.locale like :locale and e.postDate between :fromPostDate and :toPostDate and e.isDeleted = :isDeleted")
    Page<Entry> findEntryByLocaleAndPostDate(
            @Param("locale") String locale,
            @Param("fromPostDate") DateTime fromPostDate,
            @Param("toPostDate") DateTime toPostDate,
            @Param("isDeleted") Boolean isDeleted,
            Pageable pageable);

    @Query("select e from Entry e where e.subject like :subject and (e.category.categoryId like :categoryId) and e.locale like :locale and e.postDate between :fromPostDate and :toPostDate and e.isDeleted = :isDeleted")
    Page<Entry> searchEntryByCriteria(
            @Param("subject") String subject,
            @Param("categoryId") String categoryId,
            @Param("fromPostDate") DateTime fromPostDate,
            @Param("toPostDate") DateTime toPostDate,
            @Param("locale") String locale,
            @Param("isDeleted") Boolean isDeleted,
            Pageable pageable);

    @Query("select e from Entry e where e.category.categoryId in :categories")
    Page<Entry> findAllByCategory(
            @Param("categories") Collection<String> categories,
            Pageable pageable);

}
