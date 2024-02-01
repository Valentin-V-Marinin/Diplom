package reportUser.logic;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "reports")
public class FullReportsSet  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "repname")
    private String repname;
    @Column(name = "status")
    private int status;
    @Column(name = "start_date")
    private Date startDate;
    @Column(name = "end_date")
    private Date endDate;
    @Column(name = "rep_prog")
    private String repProg;
    @Column(name = "hash")
    private String hash;
    @Column(name = "comment")
    private String comment;


    public FullReportsSet(){}

    public FullReportsSet(int id, String repname, int status, Date startDate, Date endDate, String repProg, String hash, String comment) {
        this.id = id;
        this.repname = repname;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
        this.repProg = repProg;
        this.hash = hash;
        this.comment = comment;
    }

    //region Getters&Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRepname() {
        return repname;
    }

    public void setRepname(String repname) {
        this.repname = repname;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getRepProg() {
        return repProg;
    }

    public void setRepProg(String repProg) {
        this.repProg = repProg;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
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
        return "FullReportsSet{" +
                "id=" + id +
                ", repname='" + repname + '\'' +
                ", status=" + status +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", repProg='" + repProg + '\'' +
                ", hash='" + hash + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }

}
