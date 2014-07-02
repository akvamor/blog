package ua.org.project.service.jpa;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.org.project.domain.Category;
import ua.org.project.repository.CategoryRepository;
import ua.org.project.service.CategoryService;

import java.util.List;

/**
 * Created by Dmitry Petrov on 5/28/14.
 */
@Service("categoryService")
@Repository
@Transactional
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<Category> findAll() {
        return Lists.newArrayList(categoryRepository.findAll());
    }

    @Transactional(readOnly = true)
    public Category findById(String id) {
        return categoryRepository.findOne(id);
    }

    @Transactional(readOnly = true)
    public List<Category> findAllParentCategory(){
        return categoryRepository.findAllParentCategory();
    }

    @Transactional(readOnly = true)
    public List<Category> findAllSubCategory(String parentCategoryId){
        return categoryRepository.findAllSubCategory(parentCategoryId);
    }
}
