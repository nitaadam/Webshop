package training360.bitsnbytes.rubberduck.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import training360.bitsnbytes.rubberduck.ResponseStatus;
import training360.bitsnbytes.rubberduck.Validator;
import training360.bitsnbytes.rubberduck.product.Product;
import training360.bitsnbytes.rubberduck.product.ProductsService;

import java.util.List;

@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductsService productsService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/api/categories/{id}")
    public List<Product> listProductsOfCategory(@PathVariable long id){
        return productsService.listProductsByCategory(id);
    }

    @PostMapping("/api/categories")
    public ResponseStatus createCategory(@RequestBody Category category){
        int topViewOrder = categoryService.getTopViewOrder().getViewOrder();
        Category categoryToPass = category;
        if (category.getName() == null || category.getName().trim().equals("")){
            return new ResponseStatus(false, "A név megadása kötelező!");
        }
        if (categoryService.listCategories().stream().anyMatch(category1 -> category.getName().equalsIgnoreCase(category1.getName()))) {
            return new ResponseStatus(false, "Már van ilyen nevű kategória!");
        }
        if (category.getViewOrder() == 0){
            categoryToPass.setViewOrder(topViewOrder+1);
        }
        if (category.getViewOrder() <= topViewOrder){
        categoryService.shiftViewOrder(category.getViewOrder());
        }
        if (category.getViewOrder() > topViewOrder +1){
            return new ResponseStatus(false, "A megadott sorszám túl nagy!");
        }
        if (category.getViewOrder() < 1){
            return new ResponseStatus(false, "A sorszám nem lehet 1-nél kisebb!");
        }

        long id = categoryService.createCategory(categoryToPass);
        return new ResponseStatus(true, "Kategória hozzáadva, id: " + id);
    }

    @GetMapping("/api/categories")
    public List<Category> listCategories() {
        return categoryService.listCategories();
    }

    @PutMapping("/api/categories")
    public ResponseStatus updateCategory(@RequestBody Category category) {
        if (category == null || category.getName().trim().equals("")) {
            return new ResponseStatus(false, "A név megadása kötelező!");
        }
        if (categoryService.listCategories().stream().anyMatch(category1 -> category.getName().equals(category1.getName()) && category.getId() != category1.getId())) {
            return new ResponseStatus(false, "Már van ilyen nevű kategória!");
        }
        if (category.getViewOrder() > categoryService.getTopViewOrder().getViewOrder() + 1
                || category.getViewOrder() > categoryService.getTopViewOrder().getViewOrder()) {
            return new ResponseStatus(false, "A megadott sorszám túl nagy!");
        }
        if (category.getViewOrder() < 1){
            return new ResponseStatus(false, "A sorszám nem lehet 1-nél kisebb!");
        }
        categoryService.updateCategory(category);
        return new ResponseStatus(true, "Kategória frissítve.");
    }

    @DeleteMapping("/api/categories")
    public ResponseStatus deleteCategory(@RequestBody Category category) {
        if (category.getId() == 6){
            return new ResponseStatus(false, "Az egyéb kategóriát nem törölheted!");
        } else{
            categoryService.deleteCategory(category.getId());
            return new ResponseStatus(true, "Kategória törölve!");
        }

    }

}
