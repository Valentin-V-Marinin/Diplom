package reportUser;

import reportUser.view.ViewUserReport;

import javax.swing.*;

public class UserReport {
    public static void main(String[] args) {
        System.out.close();
        System.err.close();

        SwingUtilities.invokeLater(
                new Runnable() {
                    @Override
                    public void run(){
                        new ViewUserReport();
                    }
                }
        );
    }
}
