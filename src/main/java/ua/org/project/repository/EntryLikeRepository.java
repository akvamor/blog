package ua.org.project.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.org.project.domain.impl.EntryLike;

/**
 * Created by Dmitry Petrov on 17.06.14.
 */
public interface EntryLikeRepository extends CrudRepository<EntryLike, Long>{
}
