package DAO;

import java.util.List;

public interface DAOGenerico<T> {

    void create(T t);
    T getById(int id);
    List<T> getAll();
    void update(T t);
    void delete(int id);
}
