package me.leolin.sample.listviewwithviewpager.app;

import java.util.LinkedList;
import java.util.List;

/**
 * @author leolin
 */
public class ProductRepository {

    private static final List<CategoryVo> categoryVos = new LinkedList<CategoryVo>();

    static {
        CategoryVo drinkCategories = new CategoryVo("cat_1", "飲料");
        drinkCategories.getProductVos().add(new ProductVo("prod_1-1", "咖啡", 45));
        drinkCategories.getProductVos().add(new ProductVo("prod_1-2", "紅茶", 20));
        drinkCategories.getProductVos().add(new ProductVo("prod_1-3", "奶茶", 40));
        drinkCategories.getProductVos().add(new ProductVo("prod_1-4", "果汁", 60));

        CategoryVo foodCategories = new CategoryVo("cat_2", "餐點");
        foodCategories.getProductVos().add(new ProductVo("prod_2-1", "牛排", 120));
        foodCategories.getProductVos().add(new ProductVo("prod_2-2", "雞排", 90));
        foodCategories.getProductVos().add(new ProductVo("prod_2-3", "豬排", 90));
        foodCategories.getProductVos().add(new ProductVo("prod_2-4", "鐵板麵", 60));
        foodCategories.getProductVos().add(new ProductVo("prod_2-5", "牛肉麵", 80));

        CategoryVo sweetCategories = new CategoryVo("cat_3", "甜點");
        sweetCategories.getProductVos().add(new ProductVo("prod_3-1", "蛋糕", 100));
        sweetCategories.getProductVos().add(new ProductVo("prod_3-2", "冰淇淋", 80));
        sweetCategories.getProductVos().add(new ProductVo("prod_3-3", "布丁", 15));
        sweetCategories.getProductVos().add(new ProductVo("prod_3-4", "蛋塔", 25));
        sweetCategories.getProductVos().add(new ProductVo("prod_3-5", "蘋果派", 40));


        categoryVos.add(drinkCategories);
        categoryVos.add(foodCategories);
        categoryVos.add(sweetCategories);


    }

    public List<CategoryVo> getAllCategories() {
        return categoryVos;
    }

    public ProductVo findProductById(String productId) {
        for (CategoryVo categoryVo : categoryVos) {
            for (ProductVo productVo : categoryVo.getProductVos()) {
                if (productId.equals(productVo.getId())) {
                    return productVo;
                }
            }
        }
        return null;
    }
}
