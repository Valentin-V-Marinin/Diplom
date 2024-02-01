package reportAdmin;

import reportAdmin.view.ViewReport;

import javax.swing.*;

public class ReportsAdmin {
    public static void main(String[] args) {

        System.out.close();
        System.err.close();

        SwingUtilities.invokeLater(
                new Runnable() {
                    @Override
                    public void run(){
                        new ViewReport(args);
                    }
                }
        );

    }
}
