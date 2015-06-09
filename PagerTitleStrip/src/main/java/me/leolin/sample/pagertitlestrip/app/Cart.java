package me.leolin.sample.pagertitlestrip.app;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author leolin
 */
public class Cart {
    private static Map<String, Integer> productInCart = new HashMap<String, Integer>();


    public static void addToCart(String productId, int count) {
        Integer countInMap = productInCart.get(productId);
        if (countInMap == null) {
            countInMap = 0;
        }
        countInMap += count;
        if (countInMap < 0) {
            countInMap = 0;
        }
        productInCart.put(productId, countInMap);
    }

    public static int getProductCountInCart(String productId) {
        Integer countInMap = productInCart.get(productId);
        if (countInMap == null) {
            countInMap = 0;
            productInCart.put(productId, countInMap);
        }
        return countInMap;
    }

    public static Map<String, Integer> getProductInCart() {
        return productInCart;
    }

    public static int calculateSumPrice() {
        int sum = 0;
        ProductRepository productRepository = new ProductRepository();
        for (Map.Entry<String, Integer> entry : productInCart.entrySet()) {
            String productId = entry.getKey();
            int count = entry.getValue();
            ProductVo productVo = productRepository.findProductById(productId);
            if (productVo != null) {
                sum += productVo.getPrice() * count;
            }
        }
        return sum;
    }

    public static String getAvailableComboId() {
        List<ComboVo> combos = ComboRepository.getCombos();
        for (ComboVo combo : combos) {
            List<ComboDetailVo> details = combo.getDetails();

            boolean available = true;
            for (ComboDetailVo detail : details) {
                Integer countInProduct = productInCart.get(detail.getProductId());
                Integer countInDetail = detail.getQuantity();

                if (countInDetail.intValue() > countInProduct.intValue()) {
                    available = false;
                    break;
                }
            }

            if (available) {
                return combo.getId();
            }
        }

        return null;
    }


}
