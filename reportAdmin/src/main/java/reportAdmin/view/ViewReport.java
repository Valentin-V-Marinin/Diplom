package reportAdmin.view;

import reportAdmin.logic.ConfigData;
import reportAdmin.logic.HashCount;
import reportAdmin.logic.Report;
import reportAdmin.presenter.ReportPresenter;
import reportAdmin.presenter.iReportPresenter;
import reportAdmin.repository.ReportsDB;

import javax.swing.*;
import java.awt.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ViewReport extends JFrame implements iView<Report> {

    private ReportFieldsPanel reportFieldsPanel;
    private ReportListPanel reportListPanel;
    private List<Report> reportList;
    private Report selectedReport;
    private final ViewReport mainFrame;
    private ConfigData configData;

    private HashCount hashCount;

    private iReportPresenter reportPresenter;


    public ViewReport(String[] args){

        mainFrame = this;
        setTitle("УПРАВЛЕНИЕ ОТЧЁТАМИ");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(620,330));
        setResizable(false);

        setGUI(args);
        pack();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void setGUI(String[] args){

        configData = new ConfigData();
        configData.loadConfigInfo(args[2]); // третий параметр - путб к configfile
        hashCount = new HashCount();

        selectedReport = null;

        // базовая панель
        JPanel basicPanel = new JPanel(new BorderLayout());


        // панель заголовок
        JPanel headpanel = new JPanel();
        headpanel.setPreferredSize(new Dimension(mainFrame.getWidth(), 30));
        JLabel headLabel = new JLabel();
        headpanel.add(headLabel);


        // панель с дтальной информацией об отчёте
        reportFieldsPanel = new ReportFieldsPanel();
        GridLayout reportGridLayout = new GridLayout();
        reportGridLayout.setColumns(2); reportGridLayout.setRows(7);
        reportFieldsPanel.setLayout(reportGridLayout);
        reportFieldsPanel.setPreferredSize(new Dimension(210,220));

        reportListPanel = new ReportListPanel();

        // инициализация контроллера
        setController(new ReportPresenter(
                            new ReportsDB("reports.cfg.xml"),
                            mainFrame)
                    );


        // панель кнопок управления списком
        JPanel btnPanel = new JPanel();
        btnPanel.setPreferredSize(new Dimension(110, 220));
        btnPanel.setLayout(new FlowLayout());
        JButton loadBtn = new JButton("загрузить");
        JButton clearBtn = new JButton("очистить");
        JButton addBtn = new JButton("добавить");
        JButton editBtn = new JButton("изменить");
        JButton delBtn = new JButton("удалить");
        loadBtn.setPreferredSize(new Dimension(100, 25));
        clearBtn.setPreferredSize(new Dimension(100, 25));
        addBtn.setPreferredSize(new Dimension(100, 25));
        editBtn.setPreferredSize(new Dimension(100, 25));
        delBtn.setPreferredSize(new Dimension(100, 25));
        btnPanel.add(loadBtn); btnPanel.add(clearBtn); btnPanel.add(addBtn);
        btnPanel.add(editBtn); btnPanel.add(delBtn);

        // панель кнопок управления отчетом (закрыть отчет)
        JPanel btnRepPanel = new JPanel();
        btnRepPanel.setPreferredSize(new Dimension(mainFrame.getWidth(), 35));
        btnRepPanel.setLayout(new FlowLayout());
        JButton closeBtn = new JButton("выйти");
        closeBtn.setPreferredSize(new Dimension(100, 25));
        btnRepPanel.add(closeBtn);

        basicPanel.add(reportFieldsPanel, BorderLayout.WEST);
        basicPanel.add(btnPanel, BorderLayout.EAST);
        basicPanel.add(reportListPanel, BorderLayout.CENTER);

        add(headpanel, BorderLayout.NORTH);
        add(basicPanel, BorderLayout.CENTER);
        add(btnRepPanel, BorderLayout.SOUTH);

 // обработчики нажатия кнопок
        closeBtn.addActionListener(e ->{
            try {
                //reportFieldsPanel.getPresenter().closeDB();
                System.exit(0);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(mainFrame, ex.getMessage(),
                        "Администрирование отчетов", JOptionPane.ERROR_MESSAGE);
            }
        });

        addBtn.addActionListener(e -> {
            try {
                reportPresenter.addreport();
                loadBtn.doClick();
                clearBtn.doClick();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(mainFrame, ex.getMessage(),
                        "Администрирование отчетов", JOptionPane.ERROR_MESSAGE);
            }
        });


        editBtn.addActionListener(e -> {
            try {
                reportPresenter.update();
                loadBtn.doClick();
                clearBtn.doClick();
                JOptionPane.showMessageDialog(mainFrame, "Данные обновлены!",
                        "Администрирование отчетов", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(mainFrame, ex.getMessage(),
                        "Администрирование отчетов", JOptionPane.ERROR_MESSAGE);
            }
        });


        loadBtn.addActionListener(e -> {
            try {
                clearBtn.doClick();
                reportPresenter.getAllReports();
            } catch (Exception ex){
                JOptionPane.showMessageDialog(mainFrame, ex.getMessage(),
                        "Администрирование отчетов", JOptionPane.ERROR_MESSAGE);
            }
        });

        clearBtn.addActionListener(e -> {
            reportFieldsPanel.clearData();
            headLabel.setText("");
            addBtn.setEnabled(true);
            editBtn.setEnabled(false);
            delBtn.setEnabled(false);
        });

        delBtn.addActionListener(e -> {
            try {
                reportPresenter.delete();
                loadBtn.doClick();
                clearBtn.doClick();
            } catch (Exception ex){
                JOptionPane.showMessageDialog(mainFrame, ex.getMessage(),
                        "Администрирование отчетов", JOptionPane.ERROR_MESSAGE);
            }
        });


        reportListPanel.getListSelectionModel().addListSelectionListener(e -> {
            reportFieldsPanel.clearData();
            changeSelectPositionList(headLabel, btnPanel);
        });
    }

    private void changeSelectPositionList(JLabel selectedReportLabel, JPanel btnPanel){
        reportFieldsPanel.clearData();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        int idx = reportListPanel.getListReports().getSelectedIndex();
        if (idx == -1) return;
        reportFieldsPanel.setTfReportName(reportList.get(idx).getRepname());
        reportFieldsPanel.setTfStatus(Integer.toString(reportList.get(idx).getStatus()));
        reportFieldsPanel.setTfStartDate(df.format(reportList.get(idx).getStartDate()));
        if (reportList.get(idx).getEndDate() != null){
            reportFieldsPanel.setTfEndDate(df.format(reportList.get(idx).getEndDate()));
            btnPanel.getComponent(2).setEnabled(false);     // add button
            btnPanel.getComponent(3).setEnabled(false);     // edit button
            btnPanel.getComponent(4).setEnabled(true);      // delete button
        } else {
            btnPanel.getComponent(2).setEnabled(false);
            btnPanel.getComponent(3).setEnabled(true);
            btnPanel.getComponent(4).setEnabled(true);
        }
        reportFieldsPanel.setTfHash(reportList.get(idx).getHash());
        reportFieldsPanel.setTfReportProgram(reportList.get(idx).getRepProg());
        reportFieldsPanel.setTfComment(reportList.get(idx).getComment());
        selectedReport = reportList.get(idx);
        selectedReportLabel.setText(selectedReport.getRepname());
    }

    public Report getSelectedReport() {
        return selectedReport;
    }

    @Override
    public void getAllReports(List<Report> item) {
        try {
            reportList = new ArrayList<>();
            reportList.addAll(item);
            Collections.sort(reportList);
            String[] reports = new String[reportList.size()];
            int idx = 0;
            for (Report report: reportList) {
                reports[idx++] = report.getRepname();
            }
            reportListPanel.setListReports(reports);
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(mainFrame, exception.getMessage(),
                    "Администрирование отчетов", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public Report add() {
        Report report;
        String fileForHash = configData.getReportsNetDir() + reportFieldsPanel.getTfReportProgram();
        if (  !reportFieldsPanel.getTfReportName().equals("") &&
                !reportFieldsPanel.getTfReportProgram().equals("")  &&
                !reportFieldsPanel.getTfStartDate().equals("")
        ) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                report = new Report(
                        0,
                        reportFieldsPanel.getTfReportName(),
                        1, // field 'status' table 'reports'
                        dateFormat.parse(reportFieldsPanel.getTfStartDate()),
                        null, // field 'end_Date'
                        reportFieldsPanel.getTfReportProgram(),
                        hashCount.getSHA256HashForFile(fileForHash),
                        reportFieldsPanel.getTfComment()
                );
            } catch (ParseException ex) {
                throw new RuntimeException(ex.getMessage() +
                        "\nФормат даты: ГГГГ-ММ-ДД");
            }
        } else {
            throw new RuntimeException("Для добавления нового отчёта не хватает данных!");
        }
        return report;
    }

    @Override
    public Report update() {
        if ( (reportFieldsPanel.getTfReportName().equals("") &&
                reportFieldsPanel.getTfReportProgram().equals("")) ||
                reportList == null
        ){
            throw new RuntimeException ("Отчёт для редактировния не выбран!");
        }

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Report report = reportList.get(reportListPanel.getListReports().getSelectedIndex());
        String fileForHash = configData.getReportsNetDir() + reportFieldsPanel.getTfReportProgram();
        try {
            report.setRepname(reportFieldsPanel.getTfReportName());
            report.setStatus(Integer.parseInt(reportFieldsPanel.getTfStatus()));
            report.setStartDate(dateFormat.parse(reportFieldsPanel.getTfStartDate()));
            report.setRepProg(reportFieldsPanel.getTfReportProgram());
            report.setHash(hashCount.getSHA256HashForFile(fileForHash));
            report.setComment(reportFieldsPanel.getTfComment());
            if (!Objects.equals(reportFieldsPanel.getTfEndDate(), "")) {
                report.setEndDate(dateFormat.parse(reportFieldsPanel.getTfEndDate()));
            }
        } catch (ParseException ep){
            JOptionPane.showMessageDialog(mainFrame, ep.getMessage() + "\nФормат даты: ГГГГ-ММ-ДД",
                    "Администрирование отчетов", JOptionPane.ERROR_MESSAGE);
        }
        return report;
    }

    @Override
    public Report delete() {
        if (getSelectedReport() == null || getSelectedReport().getId() == 0) {
            throw  new RuntimeException("Отчёт для удаления не выбран!");
        } else {
            return reportList.get(reportListPanel.getListReports().getSelectedIndex());
        }
    }

    private void setController(iReportPresenter reportPresenter){
        this.reportPresenter = reportPresenter;
    }
}
