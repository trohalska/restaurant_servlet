package ua.servlet.restaurant.service;

import ua.servlet.restaurant.dao.CategoriesDao;
import ua.servlet.restaurant.dao.DBException;
import ua.servlet.restaurant.dao.DaoFactory;

import ua.servlet.restaurant.dto.CategoriesDTO;
import ua.servlet.restaurant.dto.converter.CategoriesDTOConverter;
import ua.servlet.restaurant.utils.Prop;

import java.util.List;

public class CategoriesService {
    DaoFactory daoFactory = DaoFactory.getInstance();

    /**
     * Get all categories for create new dish page
     * @param locale for internationalization
     * @return list
     * @throws DBException if cannot find or list is empty
     */
    public List<CategoriesDTO> getAll(String locale) throws DBException {
        try (CategoriesDao dao = daoFactory.createCategoriesDao()) {
            return CategoriesDTOConverter.convertList(dao.findAll().orElseThrow(
                    () -> new DBException(Prop.getDBProperty("select.all.categories.empty"))
            ), locale);
        }
    }

}
