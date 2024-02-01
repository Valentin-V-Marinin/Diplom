package reportUser.repository;

import java.util.List;

public interface iUserSetReportsRepo<T> {

    List<T> getUserReports(int id);
    void addReport(T item);
    void delReport(T item);
    void closeDB();

}
