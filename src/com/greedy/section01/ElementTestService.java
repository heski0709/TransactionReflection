package com.greedy.section01;

import java.util.List;

import com.greedy.common.CategoryAndMenuDTO;
import com.greedy.common.MenuAndCategoryDTO;
import com.greedy.common.MenuDTO;

@Transaction
public class ElementTestService {
    private ElementTestMapper mapper;

    public void selectCacheTest() {
        for (int i = 0; i < 10; i++) {
            Long startTime = System.nanoTime();

            List<String> menuNameList = mapper.selectCacheTest();
    
            Long endTime = System.nanoTime();
            Long interVal = endTime - startTime;
            System.out.println(menuNameList);
            System.out.println("수행 시간 : " + interVal + "ns");
        }
    }

    public void selectResultMapTest() {
        List<MenuDTO> menuList = mapper.selectResultMapTest();

        for (MenuDTO menu : menuList) {
            System.out.println(menu);
        }
    }

    public void selectResultMapConstructorTest() {
        List<MenuDTO> menuList = mapper.selectResultMapConstructorTest();

        for (MenuDTO menu : menuList) {
            System.out.println(menu);
        }

    }

    public void selectResultMapAssociationTest() {
        List<MenuAndCategoryDTO> menuList = mapper.selectResultMapAssociationTest();

        for (MenuAndCategoryDTO menu : menuList) {
            System.out.println(menu);
        }

    }

    public void selectResultMapCollectionTest() {
        List<CategoryAndMenuDTO> categoryList = mapper.selectResultMapCollectionTest();

        for (CategoryAndMenuDTO category : categoryList) {
            System.out.println(category);
        }
    }

    public void selectSqlTest() {
        List<MenuDTO> menulist = mapper.selectSqlTest();

        for (MenuDTO menuDTO : menulist) {
            System.out.println(menuDTO);
        }
    }

    public int insertMenuTest(MenuDTO menu) {
        int result = mapper.insertMenuTest(menu);
        return result;
    }

    public int insertCategoryAndMenuTest(MenuAndCategoryDTO menuAndCategory) {
        int result1 = mapper.insertNewCategory(menuAndCategory);
        int result2 = mapper.insertNewMenu(menuAndCategory);

        return result1 & result2;
    }

    public void setMapper(ElementTestMapper mapper) {
        this.mapper = mapper;
    }
}
