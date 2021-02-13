package ua.servlet.restaurant.dto.converter;

import ua.servlet.restaurant.dao.entity.Categories;
import ua.servlet.restaurant.dto.CategoriesDTO;

public class CategoriesDTOConverter {

    public static CategoriesDTO convert(Categories categories, String lang) {
        return CategoriesDTO.builder()
                .id(categories.getId())
                .category(lang.equals("ua") ? categories.getCategoryUa() : categories.getCategoryEn())
                .build();

    }
}
