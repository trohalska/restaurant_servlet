package ua.servlet.restaurant.dto;

import ua.servlet.restaurant.dao.entity.Dishes;

import java.util.List;
import java.util.stream.Collectors;

public class DishesDTOMapper {

    public static DishesDTO convert(Dishes dish, String lang) {
        return DishesDTO.builder()
                .id(dish.getId())
                .name(lang.equals("ua") ? dish.getNameUa() : dish.getNameEn())
                .price(dish.getPrice())
                .category(CategoriesDTOMapper.convert(dish.getCategories(), lang))
                .build();

    }

    public static List<DishesDTO> convertList(List<Dishes> dishes, String lang) {
        return dishes.stream().map(d -> convert(d, lang)).collect(Collectors.toList());
    }
}
