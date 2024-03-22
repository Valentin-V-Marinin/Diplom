package reportUser.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import reportUser.model.User;

import java.util.List;

public class UserDetailDB implements iUserDetailsRepo<User, Integer> {

    private final SessionFactory sessionFactory;
    private Session session;

    public UserDetailDB(String configFile) {
        sessionFactory = new Configuration()
            .configure(configFile)
            .addAnnotatedClass(User.class)
            .buildSessionFactory();
        createUserTable();
    }

    /**
     * Добавление пользователя в тпблицу пользователей
     * @param item - пользователь для добавления
     */
    @Override
    public void add(User item) {
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
     * Обновление пользователя
     * @param item - пользователь для обновления
     */
    @Override
    public void update(User item) {
        try {
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            User retrievedUser = session.get(User.class, item.getId());
            retrievedUser.setUserSurName(item.getUserSurName());
            retrievedUser.setUserName(item.getUserName());
            retrievedUser.setUserPatronymic(item.getUserPatronymic());
            retrievedUser.setBirthday(item.getBirthday());
            retrievedUser.setStatus(item.getStatus());
            retrievedUser.setStartAction(item.getStartAction());
            retrievedUser.setEndAction(item.getEndAction());
            retrievedUser.setComment(item.getComment());
            session.update(retrievedUser);
            session.getTransaction().commit();
        } catch (Exception exception){
            throw new RuntimeException(exception.getMessage());
        } finally {
            session.close();
        }
    }


    /**
     * Удаление пользователя
     * @param item - пользователь для удаления
     */
    @Override
    public void delete(User item) {
        try {
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            session.delete(session.get(User.class, item.getId()));
            session.getTransaction().commit();
        } catch (Exception exception){
            throw new RuntimeException(exception.getMessage());
        } finally {
            session.close();
        }
    }

    /**
     * Чтение из таблицы
     * @param integer - id пользователя
     * @return - найденный по id пользователь
     */
    @Override
    public User getById(Integer integer) {
        try {
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            User user = session.get(User.class, integer);
            session.getTransaction().commit();
            return user;
        } catch (Exception exception){
            throw new RuntimeException(exception.getMessage());
        } finally {
            session.close();
        }
    }


    /**
     * метод возвращает весь список пользователей
     * @return List, тип User
     */
    @Override
    public List<User> getAll() {
        List<User> result;
        try {
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();

            String sql = "SELECT * FROM enterprise.users;";

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

    /**
     * Отключение БД
     */
    @Override
    public void closeDB(){
        session = sessionFactory.getCurrentSession();
        session.close();
        sessionFactory.close();
    }


    /**
     * Проверка наличия и создание таблицы пользователей
     * при её отсутствии
     */
    private void createUserTable() {
        try {
        session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        String sql = "CREATE TABLE IF NOT EXISTS enterprise.users " +
                        "(id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                        "surname VARCHAR(30) NOT NULL, " +
                        "name VARCHAR(30) NOT NULL, " +
                        "patronymic VARCHAR(30) NOT NULL, " +
                        "birthday DATE NOT NULL, " +
                        "status INT NOT NULL, " +
                        "start_action DATE NOT NULL, " +
                        "end_action DATE, " +
                        "comment VARCHAR(255) ) ";

        Query query = session.createSQLQuery(sql).addEntity(User.class);
        query.executeUpdate();
        session.getTransaction().commit();
        } catch (Exception exception){
            throw new RuntimeException(exception.getMessage());
        } finally {
            session.close();
        }
    }
}
