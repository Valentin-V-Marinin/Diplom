package reportAdmin.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import reportAdmin.model.User;

import java.util.List;

public class UsersDB implements iUsersRepo{

    private final SessionFactory sessionFactory;
    private Session session;

    public UsersDB(String configFile) {
        sessionFactory = new Configuration()
                .configure(configFile)
                .addAnnotatedClass(User.class)
                .buildSessionFactory();
    }

    @Override
    public List<User> getUsersOfReport(String usersList) {
        List<User> result;
        try{
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();

            String sql =    "   SELECT  * " +
                            "   FROM    enterprise.users  " +
                            "   WHERE   id in ( " + usersList + ");";

            Query query = session.createSQLQuery(sql).addEntity(User.class);
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
