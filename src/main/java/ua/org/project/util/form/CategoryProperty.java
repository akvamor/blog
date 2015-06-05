package ua.org.project.util.form;

import ua.org.project.domain.Category;
import ua.org.project.service.CategoryService;

import java.beans.PropertyEditorSupport;

/**
 * Created by Dmitry Petrov on 02.07.14.
 */
public class CategoryProperty extends PropertyEditorSupport {

    private CategoryService categoryService;

    public CategoryProperty(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        Category cat = categoryService.findById(text);
        this.setValue(cat);
    }

    @Override
    public String getAsText() {
        Category cat = (Category) this.getValue();
        if (cat == null)
            cat = categoryService.findAll().stream().findFirst().get();
        return cat.getCategoryId();
    }

}
