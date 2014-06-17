package ua.org.project.repository;

import org.springframework.data.repository.CrudRepository;
import ua.org.project.domain.impl.CommentLike;

/**
 * Created by Dmitry Petrov on 17.06.14.
 */
public interface CommentLikeRepository extends CrudRepository<CommentLike, Long>{
}
