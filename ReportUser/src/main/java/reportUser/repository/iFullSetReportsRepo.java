package reportUser.repository;

import java.util.List;

public interface iFullSetReportsRepo<T> {
    List<T> getFullSetReports();

    void closeDB();
}
