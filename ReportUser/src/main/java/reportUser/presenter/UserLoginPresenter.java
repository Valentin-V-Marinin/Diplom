package reportUser.presenter;

import reportUser.logic.UserLoginData;
import reportUser.view.iViewloginData;
import reportUser.repository.iLoginDataRepo;

public class UserLoginPresenter implements iUserLoginPresenter<UserLoginData>{

    private iViewloginData<UserLoginData> view;
    private iLoginDataRepo<UserLoginData> db;

    public UserLoginPresenter(iViewloginData<UserLoginData> view, iLoginDataRepo<UserLoginData> db) {
        this.view = view;
        this.db = db;
    }


    @Override
    public void loadUserLogInData(int id) {
        UserLoginData user = db.getByID(id);
        view.load(user);
    }

    @Override
    public void addUserLogInData() {
        db.addByID(view.add(true));
        db.addDBuser(view.add(false));
    }

    @Override
    public void updateUserLogInData() {
        db.deleteDBuser(view.delete());
        db.addDBuser(view.add(false));
        db.updateByID(view.edit(true));
    }

    @Override
    public void deleteUserLogInData() {
        db.deleteByID(view.delete());
        db.deleteDBuser(view.delete());
    }

    @Override
    public void closeDB() {
        db.closeDB();
    }
}
