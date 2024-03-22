package reportUser.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import reportUser.model.UserLoginData;

import javax.persistence.NoResultException;

public class UserLoginDB implements iLoginDataRepo<UserLoginData> {
    private final SessionFactory sessionFactory;
    private Session session;

    public UserLoginDB(String configFile) {
        sessionFactory = new Configuration()
                .configure(configFile)
                .addAnnotatedClass(UserLoginData.class)
                .buildSessionFactory();
        createUserTable();
    }

    /**
     * Чтение из таблицы доступа
     * @param id - id пользователя
     * @return - данные подключения к БД найденного по id пользователя
     */
    @Override
    public UserLoginData getByID(int id) {
        try {
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            String sql = "SELECT * FROM enterprise.access a " +
                    "JOIN users u ON a.users_id = u.id " +
                    "WHERE u.id = " + id + " ";
            Query query = session.createSQLQuery(sql).addEntity(UserLoginData.class);
            UserLoginData user = (UserLoginData) query.getSingleResult();
            return user;
        } catch (NoResultException resultException){
            throw new NoResultException(resultException.getMessage());
        } finally {
            session.close();
        }
    }

    /**
     * Добавление логина и пароля пользователя в тпблицу доступа
     * @param item - пользователь для добавления
     */
    @Override
    public void addByID(UserLoginData item) {
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
     * Добавляет пользователя в системную таблицу БД mysql.user (создает аккаунт)
     * @param item - логин и пароль для создания аккаунта в БД
     */
    @Override
    public void addDBuser(UserLoginData item) {
        try {
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            String sql = "CREATE USER '" + item.getLogin() + "' IDENTIFIED BY '" + item.getPassword() + "'; ";
            Query query = session.createSQLQuery(sql).addEntity(UserLoginData.class);
            query.executeUpdate();

            sql = "GRANT SELECT, CREATE, EXECUTE ON enterprise.* TO '" + item.getLogin() + "'@'%';";
            query = session.createSQLQuery(sql).addEntity(UserLoginData.class);
            query.executeUpdate();
            session.getTransaction().commit();
        } catch (Exception exception){
            throw new RuntimeException(exception.getMessage());
        } finally {
            session.close();
        }
    }

    /**
     * Обновление данных подключения пользователя
     * @param item - пользователь для обновления
     */
    @Override
    public void updateByID(UserLoginData item) {
        try {
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            UserLoginData retrievedUser = session.get(UserLoginData.class, item.getId());
            retrievedUser.setId(item.getId());
            retrievedUser.setUsersID(item.getUsersID());
            retrievedUser.setLogin(item.getLogin());
            retrievedUser.setPassword(item.getPassword());
            session.update(retrievedUser);
            session.getTransaction().commit();
        } catch (Exception exception){
            throw new RuntimeException(exception.getMessage());
        } finally {
            session.close();
        }
    }


    /**
     * Удаление пароля и логина пользователя
     * @param item - пользователь для удаления
     */
    @Override
    public void deleteByID(UserLoginData item) {
        try {
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            session.delete(session.get(UserLoginData.class, item.getId()));
            session.getTransaction().commit();
        } catch (Exception exception){
            throw new RuntimeException(exception.getMessage());
        } finally {
            session.close();
        }
    }


    /**
     *  Удаление пользователя из системной таблицы БД mysql.user
     *  (удаление аккаунта)
     */
    @Override
    public void deleteDBuser(UserLoginData item) {
        try {
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            String sql = "DROP USER '" + item.getLogin() + "'@'%';";
            Query query = session.createSQLQuery(sql).addEntity(UserLoginData.class);
            query.executeUpdate();
        } catch (Exception exception){
            throw new RuntimeException(exception.getMessage());
        } finally {
            session.close();
        }
    }


    /**
     * Проверка наличия и создание таблицы подключения пользователей
     * при её отсутствии
     */
    private void createUserTable() {
        try{
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();

            String sql = "CREATE TABLE IF NOT EXISTS enterprise.access " +
                    "(id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                    "users_id INT NOT NULL, " +
                    "login VARCHAR(30) NOT NULL, " +
                    "password VARCHAR(255) ) ";

            Query query = session.createSQLQuery(sql).addEntity(UserLoginData.class);
            query.executeUpdate();
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
