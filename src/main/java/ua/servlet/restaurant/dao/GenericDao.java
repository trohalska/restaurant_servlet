package ua.servlet.restaurant.dao;

import java.util.List;
import java.util.Optional;

public interface GenericDao<T> extends AutoCloseable {
    Optional<T> create (T entity) throws DBException;
    Optional<T> findById(int id) throws DBException;
    Optional<List<T>> findAll() throws DBException;
    void update(T entity);
    void delete(Long login_id, Long id) throws DBException;
    void close();
}
