package vvm.reportmanager.logic;

public interface iRepo {
    void setConnectDB(String url, String user, String password);

    String[] loadInfo(String query, int columnNumber);
}
