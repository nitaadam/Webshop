package training360.bitsnbytes.rubberduck.category;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import training360.bitsnbytes.rubberduck.ResponseStatus;
import training360.bitsnbytes.rubberduck.product.Product;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql(scripts = "/init.sql")
public class CategoryControllerTest {

    @Autowired
    private CategoryController categoryController;
    private Category newCategory = new Category(3L, "super", 1);
    private Category modifiedCategory = new Category(2L, "extra", 2);

    @Test
    public void testListProductsByCategory() {

        List<Product> productList = categoryController.listProductsOfCategory(2L);

        assertEquals(2, productList.size());
    }

    @Test
    public void testCreateCategory() {

        ResponseStatus createCategory = categoryController.createCategory(newCategory);

        assertEquals(true, createCategory.isOk());
        List<Category> categoryList = categoryController.listCategories();

        assertEquals(7, categoryList.size());

    }

    @Test
    public void testListCategories() {

        List<Category> categoryList = categoryController.listCategories();
        assertEquals(6, categoryList.size());

    }

    @Test
    public void testUpdateCategory() {

        ResponseStatus updateCategory = categoryController.updateCategory(modifiedCategory);
        List<Category> categoryList = categoryController.listCategories();

        assertEquals(true, updateCategory.isOk());
        assertEquals("extra", categoryList.get(1).getName());
    }

    @Test
    public void testDeleteCategory() {

        List<Category> categoryList = categoryController.listCategories();
        ResponseStatus deleteCategory = categoryController.deleteCategory(categoryList.get(0));

        assertEquals(true, deleteCategory.isOk());
        assertEquals("normal", categoryList.get(1).getName());
    }
}
