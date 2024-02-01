package reportUser.logic;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user_reports")
public class UserReportsSet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "users_id")
    private int usersID;
    @Column(name = "reports_id")
    private int reportsID;
    @Column(name = "status")
    private int status;
    @Column(name = "start_work")
    private Date startWork;
    @Column(name = "end_work")
    private Date endWork;

    public UserReportsSet(){}
    public UserReportsSet(int id, int usersID, int reportsID, int status, Date startWork, Date endWork) {
        this.id = id;
        this.usersID = usersID;
        this.reportsID = reportsID;
        this.status = status;
        this.startWork = startWork;
        this.endWork = endWork;
    }

    //region Getters&Setters

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

    public int getReportsID() {
        return reportsID;
    }

    public void setReportsID(int reportsID) {
        this.reportsID = reportsID;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getStartWork() {
        return startWork;
    }

    public void setStartWork(Date startWork) {
        this.startWork = startWork;
    }

    public Date getEndWork() {
        return endWork;
    }

    public void setEndWork(Date endWork) {
        this.endWork = endWork;
    }
    //endregion

    @Override
    public String toString() {
        return "UserReportsSet{" +
                "id=" + id +
                ", usersID=" + usersID +
                ", reportsID=" + reportsID +
                ", status=" + status +
                ", startWork=" + startWork +
                ", endWork=" + endWork +
                '}';
    }
}
