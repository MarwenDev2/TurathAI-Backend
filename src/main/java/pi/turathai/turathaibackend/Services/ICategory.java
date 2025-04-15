package pi.turathai.turathaibackend.Services;
import pi.turathai.turathaibackend.Entites.Category;

import java.util.List;

public interface ICategory {

    Category add(Category category);
    Category update(Category category);
    void remove(long id);
    Category getByID(long id);
    List<Category> getAll();
}
