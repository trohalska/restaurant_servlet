package ua.servlet.restaurant.service;

import ua.servlet.restaurant.dao.BasketsDao;
import ua.servlet.restaurant.dao.DBException;
import ua.servlet.restaurant.dao.DaoFactory;
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

    /**
     * Get all basket's items by user id
     * @param user user id
     * @param locale for dto
     * @return BasketDTO (dishes and total price)
     * @throws DBException if DB throw exception or there are no orders
     */
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

    /**
     * Add dish to basket
     * @param user user id
     * @param id dish id
     * @throws DBException if DB throw exception
     */
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

    /**
     * Delete item from basket by dish id and user id.
     * @param login_id owner of basket
     * @param id dish id
     * @throws DBException if cannot delete
     */
    public void delete(Long login_id, Long id) throws DBException {
        try (BasketsDao dao = daoFactory.createBasketsDao()) {
            dao.delete(login_id, id);
        }
    }

    /**
     * Delete all items from basket.
     * @param id item id
     */
    public void deleteAll(Long id) {
        try (BasketsDao dao = daoFactory.createBasketsDao()) {
            dao.deleteAllByLoginId(id.intValue());
        }
    }

}
