package org.example.rmsproject.models.interfaces.Menu;

import org.example.rmsproject.models.entity.MenuItem;
import org.example.rmsproject.models.entity.Category;

import java.util.List;

public interface menuItemDAO {

    void save(MenuItem menuItem);
    void update(MenuItem menuItem);
    void delete(MenuItem menuItem);
    MenuItem findById(int id);
    List<MenuItem> getAllMenuItems();
    List<MenuItem> getMenuItemsByCategory(Category category);
}
