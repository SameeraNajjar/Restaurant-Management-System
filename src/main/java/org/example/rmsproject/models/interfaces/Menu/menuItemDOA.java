package org.example.rmsproject.models.interfaces.Menu;

import org.example.rmsproject.models.MenuItem;
import org.example.rmsproject.models.Category;

import java.util.List;

public interface menuItemDOA {

    void save(MenuItem menuItem);
    void update(MenuItem menuItem);
    void delete(MenuItem menuItem);
    MenuItem findById(int id);
    List<MenuItem> getAllMenuItems();
    List<MenuItem> getMenuItemsByCategory(Category category);
}
