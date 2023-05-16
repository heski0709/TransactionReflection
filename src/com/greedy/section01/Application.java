package com.greedy.section01;

import com.greedy.common.CategoryDTO;
import com.greedy.common.MenuAndCategoryDTO;

public class Application {
    public static void main(String[] args) {
        TransactionProxy proxy = new TransactionProxy();
        // MenuDTO menu = new MenuDTO();
        // menu.setName("test");
        // menu.setPrice(15000);
        // menu.setCategoryCode(1);
        // menu.setOrderableStatus("Y");
        // proxy.insertMenuTest(menu);

        MenuAndCategoryDTO menuAndCategory = new MenuAndCategoryDTO();
        CategoryDTO category = new CategoryDTO();
        
        menuAndCategory.setName("moneymoney");
        menuAndCategory.setPrice(20000);
        menuAndCategory.setOrderableStatus("Y");
        category.setName("test");
        
        menuAndCategory.setCategory(category);

        proxy.insertCategoryAndMenuTest(menuAndCategory);
    }
}
