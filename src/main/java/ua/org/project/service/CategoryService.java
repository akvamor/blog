package ua.org.project.service;

import ua.org.project.domain.Category;

import java.util.List;

/**
 * Created by Dmitry Petrov on 5/28/14.
 */
public interface CategoryService {
    List<Category> findAll();
    Category findById(String id);
    List<Category> findAllParentCategory();
    List<Category> findAllSubCategory(String parentCategoryId);
}
