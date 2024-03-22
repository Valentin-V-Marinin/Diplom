package reportAdmin.presenter;

public interface iReportPresenter{

    void getAllReports();

    void getAllUsersOfReport(int reportId);
    void addreport();

    void update();

    void delete();

    void closeDB();
}
