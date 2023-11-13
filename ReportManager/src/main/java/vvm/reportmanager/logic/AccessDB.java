package vvm.reportmanager.logic;

/**
 * класс содержит поля и методы для организации доступа к БД
 * Бд передается в конструктор параметром, БД должна
 * имплементировать интерфейс iRepo
 */
public class AccessDB {
    private String login;
    private String password;
    private iRepo db;

    public AccessDB(iRepo db) {
        this.db = db;
        this.login = "";
        this.password = "";
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Сравнение hash-а введённого пароля с
     * hash-ем сохранённого в БД
     * @return при совпадении паролей(hash) возвращает true
     */
    public boolean checkPassword() {
        HashCount hashCount = new HashCount();
        String inputPass = hashCount.alterHash(getPassword());
        String[] dbPass;
        try {
            String query = "call ADMIN(1,0,0,'" + getLogin() + "','" + getPassword() + "'); ";
            dbPass = db.loadInfo(query, 1);
        } catch (RuntimeException e){
            dbPass = new String[0];
        }
        if (dbPass.length == 0) {
            return false;
        }
        return inputPass.equals(dbPass[0]);
    }

}
