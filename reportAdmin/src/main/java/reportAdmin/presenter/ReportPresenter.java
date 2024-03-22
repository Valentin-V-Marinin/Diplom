package reportAdmin.presenter;

import reportAdmin.model.Report;
import reportAdmin.model.User;
import reportAdmin.model.UserReportsSet;
import reportAdmin.repository.UserReportsDB;
import reportAdmin.repository.iReportsRepo;
import reportAdmin.repository.iUserReportsRepo;
import reportAdmin.repository.iUsersRepo;
import reportAdmin.view.iView;

import java.util.ArrayList;
import java.util.List;

public class ReportPresenter implements iReportPresenter{

    private final iReportsRepo<Report> reportsRepo;
    private final iUserReportsRepo userReportsRepo;
    private final iUsersRepo usersRepo;
    private final iView<Report> view;

    public ReportPresenter(iReportsRepo<Report> reportsRepo, iUserReportsRepo userReportsRepo, iUsersRepo usersRepo, iView<Report> view) {
        this.reportsRepo = reportsRepo;
        this.userReportsRepo = userReportsRepo;
        this.usersRepo = usersRepo;
        this.view = view;
    }

    @Override
    public void getAllReports() {
        view.getAllReports(reportsRepo.getAllReports());
    }

    @Override
    public void getAllUsersOfReport(int reportId) {
        List<UserReportsSet> list = userReportsRepo.getUsersOfReport(reportId);
        String integerList = "";
        for (UserReportsSet item: list) {
            integerList += item.getUsersID() + ",";
        }
        List<User> userList = usersRepo.getUsersOfReport( integerList.substring(0, integerList.length()-1) );
        List<String> usrList = new ArrayList<>();
        for (User user: userList) {
            usrList.add(user.getUserSurName() + " " + user.getUserName() +" "+ user.getUserPatronymic());
        }
        view.getAllUsersOfReport(usrList);
    }

    @Override
    public void addreport() {
        reportsRepo.add(view.add());
    }

    @Override
    public void update() {
        reportsRepo.update(view.update());
    }

    @Override
    public void delete() {
        reportsRepo.delete(view.delete());
    }

    @Override
    public void closeDB() {
        reportsRepo.closeDB();
    }
}
