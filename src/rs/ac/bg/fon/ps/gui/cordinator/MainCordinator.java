package rs.ac.bg.fon.ps.gui.cordinator;

import java.util.ArrayList;
import rs.ac.bg.fon.ps.domain.User;
import rs.ac.bg.fon.ps.gui.controller.MainController;
import rs.ac.bg.fon.ps.gui.controller.SettingsController;
import rs.ac.bg.fon.ps.gui.form.FrmMain;
import rs.ac.bg.fon.ps.gui.form.FrmSettings;
import rs.ac.bg.fon.ps.models.LoggedUsersTableModel;
import rs.ac.bg.fon.ps.thread.StartServerThread;
import rs.ac.bg.fon.ps.util.FormMode;

public class MainCordinator {
    
    private static MainCordinator instance;
    private final MainController mainController;

    public MainCordinator() {
        this.mainController = new MainController(new FrmMain());
    }

    public static MainCordinator getInstance() {
        if (instance == null) {
            instance = new MainCordinator();
        }
        return instance;
    }
    
    public void openMainForm(){
        mainController.openForm();
    }
    
    public void refreshLoggedInUsers() {
        mainController.refreshUsersTableModel();
    }

    public void openSettingsForm(FormMode mode) {
        SettingsController settingsController = new SettingsController(new FrmSettings(mainController.getForm(), true));
        settingsController.openForm(mode);
    }

    public MainController getMainController() {
        return mainController;
    }
    
    
}
