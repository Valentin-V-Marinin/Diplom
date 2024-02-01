package reportAdmin.repository;

import java.util.List;

public interface iReportsRepo<T> {
    List<T> getAllReports();

    void add(T item);

    T update(T item);

    void delete(T item);
    void closeDB();
}
