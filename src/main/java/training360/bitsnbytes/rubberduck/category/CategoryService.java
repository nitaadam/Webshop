package training360.bitsnbytes.rubberduck.category;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private CategoryDao categoryDao;

    public CategoryService(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    public long createCategory(Category category) {
        return categoryDao.createCategory(category);
    }

    public List<Category> listCategories() {
        return categoryDao.listCategories();
    }

    public Category getTopViewOrder() {
        return categoryDao.getTopViewOrder();
    }

    public void shiftViewOrder(int i) {
        categoryDao.shiftViewOrder(i);
    }

    public void deleteCategory(long id) {
        categoryDao.deleteCategory(id);
    }

    public void updateCategory(Category category) {
        int topViewOrder = listCategories().size();
        int currentViewOrder = categoryDao.findCategoryById(category.getId()).getViewOrder();
        int newViewOrder = category.getViewOrder();

        if (newViewOrder == currentViewOrder) {
            categoryDao.updateCategoryName(category);
        }
        if (category.getViewOrder() == 0) {
            category.setViewOrder(topViewOrder);
            categoryDao.unshiftViewOrder(currentViewOrder);
        } else if (newViewOrder > currentViewOrder) {
            categoryDao.unshiftViewOrder(currentViewOrder, newViewOrder);
        } else if (newViewOrder < currentViewOrder) {
            categoryDao.shiftViewOrder(currentViewOrder, newViewOrder);
        }
        categoryDao.updateCategory(category);
    }

}
