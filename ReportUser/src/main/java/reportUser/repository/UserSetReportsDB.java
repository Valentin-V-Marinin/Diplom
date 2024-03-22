package reportUser.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import reportUser.model.User;
import reportUser.model.UserReportsSet;

import java.util.List;

public class UserSetReportsDB implements iUserSetReportsRepo<UserReportsSet> {
    private final SessionFactory sessionFactory;
    private Session session;

    public UserSetReportsDB(String configFile) {
        sessionFactory = new Configuration()
                .configure(configFile)
                .addAnnotatedClass(User.class)
                .buildSessionFactory();
    }

    /**
     * Список отчётов пользователя (подмножество полного списка)
     * @param id - ID пользователя, по которому выбираются доступные для него отчёты
     * @return список типа UserReportsSet (tablee 'user_reports')
     */
    @Override
    public List<UserReportsSet> getUserReports(int id) {
        List<UserReportsSet> result;
        try{
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();

            String sql =    "SELECT ur.* FROM enterprise.user_reports ur  " +
                            "JOIN reports r ON ur.reports_id = r.id  " +
                            "JOIN users u ON ur.users_id = u.id  " +
                            "WHERE u.id = " + id + ";";

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


    /**
     * добавление отчета в список отчетов пользователя из
     * полного списка существующих отчетов
     * @param item - тип UserReportsSet - экземпляр данного класса
     *             - запись(строка) в таблице 'user_reports'
     */
    @Override
    public void addReport(UserReportsSet item) {
        try {
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            session.save(item);
            session.getTransaction().commit();
        } catch (Exception exception){
            throw new RuntimeException(exception.getMessage());
        } finally {
            session.close();
        }
    }

    /**
     * удаление конкретного отчёта из персонального списка отчётов пользователя
     * @param item тип UserReportsSet - экземпляр данного класса
     *                   - запись(строка) в таблице 'user_reports'
     */
    @Override
    public void delReport(UserReportsSet item) {
        try {
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            session.delete(session.get(UserReportsSet.class, item.getId()));
            session.getTransaction().commit();
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
