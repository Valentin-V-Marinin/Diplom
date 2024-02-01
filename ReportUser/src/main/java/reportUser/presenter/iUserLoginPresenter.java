package reportUser.presenter;

public interface iUserLoginPresenter<T> {

    void loadUserLogInData(int id);

    void addUserLogInData();

    void updateUserLogInData();

    void deleteUserLogInData();

    void closeDB();
}
