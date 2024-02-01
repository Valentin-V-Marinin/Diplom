package reportAdmin.presenter;

import reportAdmin.logic.Report;
import reportAdmin.repository.iReportsRepo;
import reportAdmin.view.iView;

public class ReportPresenter implements iReportPresenter{

    private iReportsRepo<Report> reportsRepo;
    private iView<Report> view;

    public ReportPresenter(iReportsRepo<Report> reportsRepo, iView<Report> view) {
        this.reportsRepo = reportsRepo;
        this.view = view;
    }

    @Override
    public void getAllReports() {
        view.getAllReports(reportsRepo.getAllReports());
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
}
