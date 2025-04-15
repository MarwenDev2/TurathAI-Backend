package pi.turathai.turathaibackend.Services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pi.turathai.turathaibackend.Entites.Category;
import pi.turathai.turathaibackend.Services.ICategory;
import pi.turathai.turathaibackend.Repositories.CategoryRepo;
import pi.turathai.turathaibackend.Repositories.HeritageSiteRepo;

import java.util.List;

@Service
@Slf4j
public class CategoryService implements ICategory {

    @Autowired
    private  CategoryRepo categoryRepo;

    @Autowired
    public CategoryService(CategoryRepo categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    @Override
    public Category add(Category category) {
        log.info("adding category:");
        return categoryRepo.save(category);
    }

    @Override
    public Category update(Category category) {
        log.info("updating category:");
        return categoryRepo.save(category);
    }

    @Override
    public void remove(long id) {
        log.info("deleting category with Id : ");
        categoryRepo.deleteById(id);
    }

    @Override
    public Category getByID(long id) {
        log.info("get category with Id : ");
        return categoryRepo.findById(id).orElse(null);
    }

    @Override
    public List<Category> getAll() {
        log.info("fetch All categories : ");
        return categoryRepo.findAll();
    }
}
