package reportUser.logic;

import javax.persistence.*;


@Entity
@Table(name = "access")
public class UserLoginData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "users_id")
    private int usersID;
    @Column(name = "login")
    private String login;
    @Column(name = "password")
    private String password;

    public UserLoginData(){}

    public UserLoginData(int id, int usersID, String login, String password) {
        this.id = id;
        this.usersID = usersID;
        this.login = login;
        this.password = password;
    }

    //region GettersSetters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUsersID() {
        return usersID;
    }

    public void setUsersID(int usersID) {
        this.usersID = usersID;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    //endregion


    @Override
    public String toString() {
        return "UserLoginData{" +
                "id=" + id +
                ", usersID=" + usersID +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
