package Repository;

import DatabaseManager.DatabaseManager;

import java.util.List;

public interface RepositoryInterface<T> {
    T findById(int id, DatabaseManager dbManager);

    List<T> findAll(DatabaseManager dbManager);

    void create(T entity, DatabaseManager dbManager);

    void update(T entity, DatabaseManager dbManager);

    void delete(int id, DatabaseManager dbManager);
}