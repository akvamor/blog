package ua.org.project.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ua.org.project.domain.impl.Comment;

import java.util.List;

/**
 * Created by Dmitry Petrov on 5/28/14.
 */
public interface CommentRepository extends CrudRepository<Comment, Long> {

    @Query("SELECT c FROM comment c WHERE c.entry.id = :entryId")
    public List<Comment> findByEntryId(@Param("entryId") Long entryId);

}
