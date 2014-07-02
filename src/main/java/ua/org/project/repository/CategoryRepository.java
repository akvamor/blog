package ua.org.project.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ua.org.project.domain.Category;

import java.util.List;

/**
 * Created by Dmitry Petrov on 5/28/14.
 */
public interface CategoryRepository extends CrudRepository<Category, String> {

    @Query("SELECT c FROM Category c WHERE c.parentCategory IS NULL")
    List<Category> findAllParentCategory();

    @Query("SELECT c FROM Category c WHERE c.parentCategory.categoryId = :parentCategoryId")
    List<Category> findAllSubCategory(@Param("parentCategoryId") String parentCategoryId);
}
