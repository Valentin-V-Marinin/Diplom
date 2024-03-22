package reportAdmin.repository;

import reportAdmin.model.User;

import java.util.List;

public interface iUsersRepo {

    List<User> getUsersOfReport(String usersList);

}
