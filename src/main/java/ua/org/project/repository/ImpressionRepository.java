package ua.org.project.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.org.project.domain.Impression;

/**
 * Created by Dmitry Petrov on 28.06.14.
 */
public interface ImpressionRepository extends CrudRepository<Impression, Long>{
}
