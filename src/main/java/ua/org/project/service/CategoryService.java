package ua.org.project.service;

import ua.org.project.domain.Category;

import java.util.List;

/**
 * Created by Dmitry Petrov on 5/28/14.
 */
public interface CategoryService {
    public List<Category> findAll();
    public List<Category> findAllParentCategory();
    public List<Category> findAllSubCategory(String parentCategoryId);
}
