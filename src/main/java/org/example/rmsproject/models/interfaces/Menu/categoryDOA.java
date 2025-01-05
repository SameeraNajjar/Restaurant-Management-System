package org.example.rmsproject.models.interfaces.Menu;


import org.example.rmsproject.models.Category;

import java.util.List;

public interface categoryDOA {
    void save(Category category);
    void update(Category category);
    void delete(Category category);
    Category findById(int id);
    List<Category> getAllCategories();


}
