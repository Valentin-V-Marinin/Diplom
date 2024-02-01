package reportUser.repository;

public interface iLoginDataRepo<T> {

    T getByID(int id);

    void addByID (T item);

    void addDBuser(T item);

    void updateByID (T item);

    void deleteByID(T item);

    void deleteDBuser(T item);
    void closeDB();

}
