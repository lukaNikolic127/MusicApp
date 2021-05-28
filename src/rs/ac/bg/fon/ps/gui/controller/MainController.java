package rs.ac.bg.fon.ps.gui.controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import rs.ac.bg.fon.ps.domain.User;
import rs.ac.bg.fon.ps.gui.cordinator.MainCordinator;
import rs.ac.bg.fon.ps.gui.form.FrmAbout;
import rs.ac.bg.fon.ps.gui.form.FrmMain;
import rs.ac.bg.fon.ps.models.LoggedUsersTableModel;
import rs.ac.bg.fon.ps.thread.StartServerThread;
import rs.ac.bg.fon.ps.util.FormMode;

public class MainController {

    private final FrmMain form;
    private StartServerThread serverThread;

    public MainController(FrmMain form) {
        this.form = form;
        addActionListeners();
    }

    public FrmMain getForm() {
        return form;
    }

    public void openForm() {
        prepareForm();
        form.setVisible(true);
    }

    private void addActionListeners() {

        form.addBtnStartActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (serverThread == null || !serverThread.isAlive()) {
                    serverThread = new StartServerThread(form);
                    serverThread.start();
                    form.getLblOnOff().setText("ACTIVE");
                    form.getLblOnOff().setForeground(Color.green);
                    form.getBtnStart().setEnabled(false);
                    form.getBtnStop().setEnabled(true);
                }
            }
        });

        form.addBtnStopActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (serverThread != null && serverThread.isAlive()) {
                    try {
                        serverThread.stopServer();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(form, "Stopping server failed!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    form.getLblOnOff().setText("NOT ACTIVE");
                    form.getLblOnOff().setForeground(Color.red);
                    form.getBtnStart().setEnabled(true);
                    form.getBtnStop().setEnabled(false);
                }
            }
        });

        form.addMiSettingsActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (serverThread == null || !serverThread.isAlive()) {
                    MainCordinator.getInstance().openSettingsForm(FormMode.FORM_EDIT);
                } else {
                    MainCordinator.getInstance().openSettingsForm(FormMode.FORM_VIEW);
                }
            }
        });

        form.addMiAboutActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FrmAbout aboutFrm = new FrmAbout(form, true);
                aboutFrm.setVisible(true);
            }
        });
    }

    private void prepareForm() {
        LoggedUsersTableModel model = new LoggedUsersTableModel();
        form.getTblLoggedinUsers().setModel(model);
        form.getLblOnOff().setText("NOT ACTIVE");
        form.getLblOnOff().setForeground(Color.red);
        form.getBtnStart().setEnabled(true);
        form.getBtnStop().setEnabled(false);
    }

    public void refreshUsersTableModel() {
        LoggedUsersTableModel model = (LoggedUsersTableModel) form.getTblLoggedinUsers().getModel();
        model.setUsers((ArrayList<User>) StartServerThread.clients);
    }

}
