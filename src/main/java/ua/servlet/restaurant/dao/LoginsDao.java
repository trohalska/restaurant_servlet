package ua.servlet.restaurant.dao;

import ua.servlet.restaurant.model.Logins;

import java.util.Optional;

public interface LoginsDao extends GenericDao<Logins> {
    Optional<Logins> findByLogin(String login);
}
