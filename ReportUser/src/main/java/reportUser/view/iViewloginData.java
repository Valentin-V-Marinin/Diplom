package reportUser.view;

public interface iViewloginData<T> {

    void load(T item);

    T add (boolean flagHashOn);

    T edit (boolean flagHashOn);

    T delete();

}
