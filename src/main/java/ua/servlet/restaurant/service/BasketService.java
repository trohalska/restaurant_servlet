package ua.servlet.restaurant.service;

import ua.servlet.restaurant.dao.BasketsDao;
import ua.servlet.restaurant.dao.DBException;
import ua.servlet.restaurant.dao.DaoFactory;;
import ua.servlet.restaurant.dao.entity.Baskets;
import ua.servlet.restaurant.dao.entity.Dishes;
import ua.servlet.restaurant.dao.entity.Logins;
import ua.servlet.restaurant.dto.BasketDTO;
import ua.servlet.restaurant.dto.DishesDTO;
import ua.servlet.restaurant.dto.converter.BasketDTOConverter;
import ua.servlet.restaurant.dto.converter.DishesDTOConverter;
import ua.servlet.restaurant.utils.Prop;

import java.math.BigDecimal;
import java.util.List;

public class BasketService {
    DaoFactory daoFactory = DaoFactory.getInstance();

    public BasketDTO getAll(Logins user, String locale) throws DBException {
        List<Baskets> list;

        try (BasketsDao dao = daoFactory.createBasketsDao()) {
            list = dao.findAllByLoginId(user.getId()).orElseThrow(
                    () -> new DBException(Prop.getDBProperty("select.all.baskets.empty")));
        }
        List<DishesDTO> dishesDTO = BasketDTOConverter.convertListFromBaskets(list, locale);
        BigDecimal total = DishesDTOConverter.getTotalPrice(dishesDTO);

        return BasketDTO.builder()
                .dishes(dishesDTO)
                .totalPrice(total)
                .build();
    }

    public void create(Logins user, Long id) throws DBException {
        try (BasketsDao dao = daoFactory.createBasketsDao()) {
            dao.create(Baskets.builder()
                    .dish(Dishes.builder().id(id).build())
                    .login(user)
                    .build())
                    .orElseThrow(
                            () -> new DBException(Prop.getDBProperty("create.basket.dbe")));
        }
    }

    public void delete(Long id) {
        try (BasketsDao dao = daoFactory.createBasketsDao()) {
            dao.delete(id.intValue());
        }
    }

    public void deleteAll(Long id) {
        try (BasketsDao dao = daoFactory.createBasketsDao()) {
            dao.deleteAllByLoginId(id.intValue());
        }
    }

}
