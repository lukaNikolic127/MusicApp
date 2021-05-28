package rs.ac.bg.fon.ps.gui.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import javax.swing.JOptionPane;
import rs.ac.bg.fon.ps.gui.cordinator.MainCordinator;
import rs.ac.bg.fon.ps.gui.form.FrmSettings;
import rs.ac.bg.fon.ps.util.FormMode;

public class SettingsController {

    private final FrmSettings form;
    private String urlDef;
    private String usernameDef;
    private String passwordDef;

    public SettingsController(FrmSettings form) {
        this.form = form;
    }

    public void openForm(FormMode mode) {
        try {
            prepareForm(mode);
            addActionListeners();
            form.setLocationRelativeTo(MainCordinator.getInstance().getMainController().getForm());
            form.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(form, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void prepareForm(FormMode mode) throws Exception {

        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("config/dbconfig.properties"));
        } catch (FileNotFoundException ex) {
            throw new Exception("Connecting to database failed!\nConfiguration file not found.");
        }
        urlDef = properties.getProperty("url");
        usernameDef = properties.getProperty("username");
        passwordDef = properties.getProperty("password");

        form.getTxtUrl().setText(urlDef);
        form.getTxtUsername().setText(usernameDef);
        form.getTxtPassword().setText(passwordDef);

        if (mode.equals(FormMode.FORM_EDIT)) {
            form.getTxtUrl().setEditable(true);
            form.getTxtUsername().setEditable(true);
            form.getTxtPassword().setEditable(true);
            form.getBtnSave().setEnabled(true);
        } else if (mode.equals(FormMode.FORM_VIEW)) {
            form.getTxtUrl().setEditable(false);
            form.getTxtUsername().setEditable(false);
            form.getTxtPassword().setEditable(false);
            form.getBtnSave().setEnabled(false);
        }
    }

    private void addActionListeners() {
        form.addBtnSaveActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                Properties p = new Properties();
                try {
                    OutputStream out = new FileOutputStream("config/dbconfig.properties");
                    String url = form.getTxtUrl().getText();
                    String username = form.getTxtUsername().getText();
                    String password = String.copyValueOf(form.getTxtPassword().getPassword());
                    try {
                        testConnection(url, username, password);
                        p.setProperty("url", url);
                        p.setProperty("username", username);
                        p.setProperty("password", password);
                        p.store(out, null);
                        JOptionPane.showMessageDialog(form, "Connection to the database established!", "Connection established", JOptionPane.INFORMATION_MESSAGE);
                    } catch (SQLException sqlEx) {
                        p.setProperty("url", urlDef);
                        p.setProperty("username", usernameDef);
                        p.setProperty("password", passwordDef);
                        p.store(out, null);
                        form.getTxtUrl().setText(urlDef);
                        form.getTxtUsername().setText(usernameDef);
                        form.getTxtPassword().setText(passwordDef);
                        JOptionPane.showMessageDialog(form, "Connecting to database failed!\nDefault settings applied.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(form, "Loading properties file failed!", "Error", JOptionPane.ERROR_MESSAGE);
                }

            }

            private void testConnection(String url, String username, String password) throws SQLException {
                Connection conn = DriverManager.getConnection(url, username, password);
            }
        });
    }

}
