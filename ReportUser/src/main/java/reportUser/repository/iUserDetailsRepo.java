package reportUser.repository;

import java.util.List;

public interface iUserDetailsRepo<T, TId> {

    void add(T item);
    void update(T item);
    void delete(T item);
    T getById(TId id);
    List<T> getAll();
    void closeDB();
}
