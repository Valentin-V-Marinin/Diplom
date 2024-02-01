package reportUser.presenter;

public interface iSetReportsPresenter {
    void loadUserSetReports(int userID);
    void loadFullSetReports();
    void addReportToUser();
    void removeReportFromUser();

    void closeDB();

}
