package reportAdmin.repository;

import reportAdmin.logic.Report;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;

public class ReportsDB implements iReportsRepo<Report> {

    private final SessionFactory sessionFactory;
    private Session session;

    public ReportsDB(String configFile) {
        sessionFactory = new Configuration()
                .configure(configFile)
                .addAnnotatedClass(Report.class)
                .buildSessionFactory();
    }

    /**
     * возвращает полный список отчётов
     * @return список типа Report (table 'reports')
     */
    @Override
    public List<Report> getAllReports() {
        List<Report> result;
        try{
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();

            String sql = "SELECT * FROM enterprise.reports;";

            Query query = session.createSQLQuery(sql).addEntity(Report.class);
            result = query.getResultList();
            session.getTransaction().commit();
            return result;
        } catch (Exception exception){
            throw new RuntimeException(exception.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
    public void add(Report item) {
        session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.save(item);
        session.getTransaction().commit();
    }

    @Override
    public Report update(Report item) {
        session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Report updatedReport = session.get(Report.class, item.getId());
        updatedReport.setRepname(item.getRepname());
        updatedReport.setStatus(item.getStatus());
        updatedReport.setStartDate(item.getStartDate());
        updatedReport.setEndDate(item.getEndDate());
        updatedReport.setHash(item.getHash());
        updatedReport.setRepProg(item.getRepProg());
        updatedReport.setComment(item.getComment());
        session.update(updatedReport);
        session.getTransaction().commit();
        return updatedReport;
    }

    @Override
    public void delete(Report item) {
        session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.delete(item);
        session.getTransaction().commit();
    }

    /**
     * Отключение ДБ
     */
    public void closeDB(){
        session = sessionFactory.getCurrentSession();
        session.close();
        sessionFactory.close();
    }

}
