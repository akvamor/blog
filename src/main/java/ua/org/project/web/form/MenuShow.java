package ua.org.project.web.form;

import ua.org.project.domain.Category;

import java.util.List;

/**
 * Created by Dmitry Petrov on 05.06.14.
 */
public class MenuShow {
    private String currentCategory;

    private String parentCategory;

    private List<Category> categories;

    public String getCurrentCategory() {
        return currentCategory;
    }

    public void setCurrentCategory(String currentCategory) {
        this.currentCategory = currentCategory;
    }

    public String getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(String parentCategory) {
        this.parentCategory = parentCategory;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
