package ua.servlet.restaurant.dto.converter;

import ua.servlet.restaurant.dao.entity.Categories;
import ua.servlet.restaurant.dto.CategoriesDTO;

import java.util.List;
import java.util.stream.Collectors;

public class CategoriesDTOConverter {

    public static CategoriesDTO convert(Categories categories, String lang) {
        return CategoriesDTO.builder()
                .id(categories.getId())
                .category(lang.equals("ua") ? categories.getCategoryUa() : categories.getCategoryEn())
                .build();
    }

    public static List<CategoriesDTO> convertList(List<Categories> categories, String lang) {
        return categories.stream().map(c -> convert(c, lang)).collect(Collectors.toList());
    }
}
