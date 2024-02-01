package reportUser.view;

import java.util.List;

public interface iViewDetailsData<T> {

    void load(List<T> item);

    T add ();

    T edit ();

    T delete();

}
