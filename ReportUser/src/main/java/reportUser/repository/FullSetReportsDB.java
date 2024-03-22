package reportUser.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import reportUser.model.FullReportsSet;
import reportUser.model.User;

import java.util.List;

public class FullSetReportsDB implements iFullSetReportsRepo<FullReportsSet> {

    private final SessionFactory sessionFactory;
    private Session session;

    public FullSetReportsDB(String configFile) {
        sessionFactory = new Configuration()
                .configure(configFile)
                .addAnnotatedClass(User.class)
                .buildSessionFactory();
    }

    /**
     * возвращает полный список отчётов
     * @return список типа FullReportsSet (table 'reports')
     */
    @Override
    public List<FullReportsSet> getFullSetReports() {
        List<FullReportsSet> result;
        try{
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();

            String sql = "SELECT * FROM enterprise.reports;";

            Query query = session.createSQLQuery(sql).addEntity(FullReportsSet.class);
            result = query.getResultList();
            session.getTransaction().commit();
            return result;
        } catch (Exception exception){
            throw new RuntimeException(exception.getMessage());
        } finally {
            session.close();
        }
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
