package reportAdmin.model;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "users")
public class User implements Comparable<User>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "surname")
    private String userSurName;
    @Column(name = "name")
    private String userName;
    @Column(name = "patronymic")
    private String userPatronymic;
    @Column(name = "birthday")
    private Date birthday;
    @Column(name = "status")
    private int status;
    @Column(name = "start_action")
    private Date startAction;
    @Column(name = "end_action")
    private Date endAction;
    @Column(name = "comment")
    private String comment;


    public User() {}

    public User(int id, String userSurName, String userName, String userPatronymic, Date birthday,
                int status, Date startAction, Date endAction, String comment) {
        this.id = id;
        this.userSurName = userSurName;
        this.userName = userName;
        this.userPatronymic = userPatronymic;
        this.birthday = birthday;
        this.status = status;
        this.startAction = startAction;
        this.endAction = endAction;
        this.comment = comment;
    }


    //region Getters&Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserSurName() {
        return userSurName;
    }

    public void setUserSurName(String userSurName) {
        this.userSurName = userSurName;
    }

    public String getUserPatronymic() {
        return userPatronymic;
    }

    public void setUserPatronymic(String userPatronymic) {
        this.userPatronymic = userPatronymic;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getStartAction() {
        return startAction;
    }

    public void setStartAction(Date startAction) {
        this.startAction = startAction;
    }

    public Date getEndAction() {
        return endAction;
    }

    public void setEndAction(Date endAction) {
        this.endAction = endAction;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
    //endregion


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userSurName='" + userSurName + '\'' +
                ", userName='" + userName + '\'' +
                ", userPatronymic='" + userPatronymic + '\'' +
                ", birthday=" + birthday +
                ", status=" + status +
                ", startAction=" + startAction +
                ", endAction=" + endAction +
                ", comment='" + comment + '\'' +
                '}';
    }

    @Override
    public int compareTo(User o) {
        return (this.userSurName).compareTo(o.userSurName);
    }
}
