package ua.servlet.restaurant.dao;

import java.util.List;
import java.util.Optional;

public interface GenericDao<T> extends AutoCloseable {
    Optional<T> create (T entity) throws DBException;
    T findById(int id);
    List<T> findAll() throws DBException;
    void update(T entity);
    void delete(int id);
    void close();
}
