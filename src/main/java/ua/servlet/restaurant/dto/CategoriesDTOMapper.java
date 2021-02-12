package ua.servlet.restaurant.dto;

import ua.servlet.restaurant.dao.entity.Categories;

public class CategoriesDTOMapper {

    public static CategoriesDTO convert(Categories categories, String lang) {
        return CategoriesDTO.builder()
                .id(categories.getId())
                .category(lang.equals("ua") ? categories.getCategoryUa() : categories.getCategoryEn())
                .build();

    }
}
