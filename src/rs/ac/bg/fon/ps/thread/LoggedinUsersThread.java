package rs.ac.bg.fon.ps.thread;

import java.util.logging.Level;
import java.util.logging.Logger;
import rs.ac.bg.fon.ps.gui.cordinator.MainCordinator;
import rs.ac.bg.fon.ps.gui.form.FrmMain;

public class LoggedinUsersThread extends Thread {

    private FrmMain frmMain;
    
    public LoggedinUsersThread(FrmMain frmMain) {
        this.frmMain = frmMain;
    }
    
    @Override
    public void run() {
        while(true){
            MainCordinator.getInstance().refreshLoggedInUsers();
            try {
                sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(LoggedinUsersThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
