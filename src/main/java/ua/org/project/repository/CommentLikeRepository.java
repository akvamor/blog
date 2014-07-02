package ua.org.project.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ua.org.project.domain.impl.CommentLike;

/**
 * Created by Dmitry Petrov on 17.06.14.
 */
public interface CommentLikeRepository extends CrudRepository<CommentLike, Long>{

    @Query("select count(l.id) from CommentLike l where l.comment.id = :commentId and l.like > 0")
    long countAllLike(@Param("commentId") Long commentId);
    @Query("select count(l.id) from CommentLike l where l.comment.id = :commentId and l.like < 0")
    long countAllNotLike(@Param("commentId") Long commentId);
}
