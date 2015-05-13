package ua.org.project.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ua.org.project.domain.impl.EntryLike;

/**
 * Created by Dmitry Petrov on 17.06.14.
 */
public interface EntryLikeRepository extends CrudRepository<EntryLike, Long>{
    @Query("select count(l.id) from EntryLike l where l.entry.id = :entryId and l.like > 0")
    long countAllLike(@Param("entryId") Long entryId);
    @Query("select count(l.id) from EntryLike l where l.entry.id = :entryId and l.like < 0")
    long countAllNotLike(@Param("entryId") Long entryId);
}
