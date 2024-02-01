package reportAdmin.view;

import java.util.List;

public interface iView<T> {

    void getAllReports(List<T> item);

    T add();

    T update();

    T delete();



}
