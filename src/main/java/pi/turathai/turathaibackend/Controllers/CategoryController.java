package pi.turathai.turathaibackend.Controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pi.turathai.turathaibackend.Entites.Category;
import pi.turathai.turathaibackend.Entites.HeritageSite;
import pi.turathai.turathaibackend.Services.CategoryService;
import pi.turathai.turathaibackend.Services.HeritageSiteService;

import java.util.List;




@RestController
@RequestMapping("/api/Categories")
@CrossOrigin(origins = "*")
public class CategoryController {

    @Autowired
    CategoryService categoryService ;


    @PostMapping("/addCategory")
    public Category addCategory(@RequestBody Category category)
    {
        return categoryService.add(category);
    }

    @PutMapping("/updateCategory")
    public Category updateCategory(@RequestBody Category category)
    {
        return categoryService.update(category);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteCategory(@PathVariable long id)
    {
        categoryService.remove(id);
    }

    @GetMapping("/get/{id}")
    public Category getCategoryById(@PathVariable long id) {
        return categoryService.getByID(id);
    }

    @GetMapping("/allCat")
    public List<Category> getAllCategories()
    {
        return categoryService.getAll();
    }

    @GetMapping("/count")
    public long getCategoryCount() {
        return categoryService.countCategories();
    }
}
