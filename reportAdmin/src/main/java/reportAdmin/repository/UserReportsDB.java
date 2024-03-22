package reportAdmin.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import reportAdmin.model.Report;
import reportAdmin.model.User;
import reportAdmin.model.UserReportsSet;

import java.util.ArrayList;
import java.util.List;

public class UserReportsDB implements iUserReportsRepo{
    private final SessionFactory sessionFactory;
    private Session session;

    public UserReportsDB(String configFile) {
        sessionFactory = new Configuration()
                .configure(configFile)
                .addAnnotatedClass(UserReportsSet.class)
                .buildSessionFactory();
    }

    @Override
    public List<UserReportsSet> getUsersOfReport(int idReport) {
        List<UserReportsSet> result;
        try{
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();

            String sql = "  SELECT  * " +
                         "  FROM    enterprise.user_reports " +
                         "  WHERE   reports_id = " + idReport +";";

            Query query = session.createSQLQuery(sql).addEntity(UserReportsSet.class);
            result = query.getResultList();
            session.getTransaction().commit();
            return result;
        } catch (Exception exception){
            throw new RuntimeException(exception.getMessage());
        } finally {
            session.close();
        }
    }
}
