package ua.org.project.domain;

/**
 * Created by Dmitry Petrov on 18.06.14.
 */
public class SearchCriteriaCategory {
    private String locale;
    private String categoryId;

    public SearchCriteriaCategory(String categoryId, String locale) {
        this.locale = locale;
        this.categoryId = categoryId;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
}
