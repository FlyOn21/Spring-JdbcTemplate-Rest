package org.example.app.repository;

import java.util.List;
import java.util.Optional;

public interface IRepository<T> {
    boolean create(T obj);
    Optional<List<T>> getAll();
    Optional<T> getById(Long id);
    boolean update(Long id, T obj);
    boolean delete(Long id);
}
