package ua.servlet.restaurant.dto.converter;

import ua.servlet.restaurant.dao.entity.Dishes;
import ua.servlet.restaurant.dto.DishesDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class DishesDTOConverter {

    public static DishesDTO convert(Dishes dish, String lang) {
        return DishesDTO.builder()
                .id(dish.getId())
                .name(lang.equals("ua") ? dish.getNameUa() : dish.getNameEn())
                .price(dish.getPrice())
                .category(CategoriesDTOConverter.convert(dish.getCategories(), lang))
                .build();

    }

    public static List<DishesDTO> convertList(List<Dishes> dishes, String lang) {
        return dishes.stream().map(d -> convert(d, lang)).collect(Collectors.toList());
    }

    /**
     * Count total price for basket page
     * @param dishes dishes in the basket
     * @return BigDecimal total price
     */
    public static BigDecimal getTotalPrice(List<DishesDTO> dishes) {
        return dishes.stream()
                .map(DishesDTO::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
