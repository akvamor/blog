package ua.org.project.domain;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Dmitry Petrov on 5/28/14.
 */
@Entity
@Table(name = "category")
public class Category implements Serializable {
    private String categoryId;
    private Category parentCategory;
    private Set<Category> subCategories = new HashSet<Category>();

    public Category(){}

    public Category(String categoryId){
        this.categoryId = categoryId;
    }

    public Category(
            String categoryId,
            Category parentCategory,
            Set<Category> subCategories
    ) {
        this.categoryId = categoryId;
        this.parentCategory = parentCategory;
        this.subCategories = subCategories;
    }

    @Id
    @Column(name = "CATEGORY_ID")
    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_CATEGORY_ID")
    public Category getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }

    @JsonIgnore
    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "parentCategory"
    )
    public Set<Category> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(Set<Category> subCategories) {
        this.subCategories = subCategories;
    }
}
