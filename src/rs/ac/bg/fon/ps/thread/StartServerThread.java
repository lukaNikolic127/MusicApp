package rs.ac.bg.fon.ps.thread;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import rs.ac.bg.fon.ps.domain.User;
import rs.ac.bg.fon.ps.gui.form.FrmMain;

public class StartServerThread extends Thread {

    private FrmMain frmMain;
    private Socket socket;
    private ServerSocket serverSocket;
    private boolean end = false;
    public static List<User> clients = new ArrayList<>();
    private List<HandleClientThread> clientThreads = new ArrayList<>();

    public StartServerThread(FrmMain frmMain) {
        this.frmMain = frmMain;
    }

    @Override
    public void run() {

        try {
            serverSocket = new ServerSocket(9000);
            System.out.println("Server started!");
            LoggedinUsersThread loggedinUsersThread = new LoggedinUsersThread(this.frmMain);
            loggedinUsersThread.start();
            while (!end) {

                System.out.println("Waiting for users ...");
                socket = serverSocket.accept();
                System.out.println("Client connected!");

                HandleClientThread clientThread = new HandleClientThread(socket);
                clientThreads.add(clientThread);
                clientThread.start();

            }
        } catch (Exception e) {
            System.out.println("Server has been stopped! (Server socket has been closed)");
        }
    }

    public void stopServer() throws Exception {
        serverSocket.close();
        clientThreads.forEach(ct -> ct.stopThread());
    }
}
