package reportUser.presenter;

import reportUser.model.User;
import reportUser.repository.iUserDetailsRepo;
import reportUser.view.iViewDetailsData;

import java.util.List;

public class UserDetailsPresenter implements iUserDetailsPresenter<User>{

    private iViewDetailsData<User> view;
    private iUserDetailsRepo<User, Integer> db;

    public UserDetailsPresenter(iViewDetailsData<User> view, iUserDetailsRepo<User, Integer> db) {
        this.view = view;
        this.db = db;
    }

    @Override
    public void load() {
        try {
            List<User> userList = db.getAll();
            view.load(userList);
        } catch (Exception ex){
            throw new RuntimeException(ex.getMessage());
        }
    }

    @Override
    public void add() {
        try {
            User user = view.add();
            db.add(user);
        } catch (Exception ex){
            throw new RuntimeException(ex.getMessage());
        }
    }

    @Override
    public void update() {
        try {
            User user = view.edit();
            if (user != null) {
                db.update(user);
            }
        } catch (Exception ex){
            throw new RuntimeException(ex.getMessage());
        }
    }

    @Override
    public void delete() {
        try {
            User user = view.delete();
            if (user != null) db.delete(user);
        } catch (Exception ex){
            throw new RuntimeException(ex.getMessage());
        }
    }

    @Override
    public void closeDB() {
        try{
            db.closeDB();
        } catch (Exception ex){
            throw new RuntimeException(ex.getMessage());
        }
    }
}
