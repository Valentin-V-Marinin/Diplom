package reportAdmin.repository;

import reportAdmin.model.UserReportsSet;

import java.util.List;

public interface iUserReportsRepo {

    List<UserReportsSet> getUsersOfReport(int idReport);
}
