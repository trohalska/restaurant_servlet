package ua.servlet.restaurant.dto.converter;

import ua.servlet.restaurant.dao.entity.Baskets;
import ua.servlet.restaurant.dto.DishesDTO;

import java.util.List;
import java.util.stream.Collectors;

public class BasketDTOConverter {

    public static List<DishesDTO> convertListFromBaskets(List<Baskets> baskets, String lang) {
        return baskets.stream()
                .map(b -> DishesDTOConverter.convert(b.getDish(), lang))
                .collect(Collectors.toList());
    }

}
