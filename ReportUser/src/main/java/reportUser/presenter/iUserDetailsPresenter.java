package reportUser.presenter;

public interface iUserDetailsPresenter<T> {

    void load();

    void add();

    void update();

    void delete();

    void closeDB();
}
