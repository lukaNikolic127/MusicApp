package rs.ac.bg.fon.ps.thread;

import java.io.IOException;
import java.net.Socket;
import rs.ac.bg.fon.ps.communication.Receiver;
import rs.ac.bg.fon.ps.communication.Request;
import rs.ac.bg.fon.ps.communication.Response;
import rs.ac.bg.fon.ps.communication.Sender;
import rs.ac.bg.fon.ps.controller.Controller;
import rs.ac.bg.fon.ps.domain.Album;
import rs.ac.bg.fon.ps.domain.Performer;
import rs.ac.bg.fon.ps.domain.User;

public class HandleClientThread extends Thread {

    private Socket socket;
    private Sender sender;
    private Receiver receiver;
    private User user;
    private boolean end = false;

    public HandleClientThread(Socket socket) {
        this.socket = socket;
        sender = new Sender(socket);
        receiver = new Receiver(socket);
    }

    public void stopThread() {
        end = true;
        try {
            socket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (!socket.isClosed() && !end) {
                Request request = (Request) receiver.receive();
                Response response = new Response();

                switch (request.getOperation()) {
                    case LOG_IN:
                        try {
                            User userTry = (User) request.getUser();
                            if (StartServerThread.clients.contains(userTry)) {
                                throw new Exception("User " + request.getUser().getUsername() + " is already logged in!");
                            }
                            User user = Controller.getInstance().getUser(request.getUser());
                            if (!userTry.getPassword().equals(user.getPassword())) {
                                throw new Exception("Wrong password!");
                            }
                            
                            response.setResult(user);
                            this.user = user;
                            StartServerThread.clients.add(this.user);

                        } catch (Exception ex) {
                            response.setException(ex);
                        }
                        break;
                    case RETURN_ALL_PERFORMERS:
                        try {
                            response.setResult(Controller.getInstance().getAllPerformers((Performer) request.getArgument()));
                        } catch (Exception ex) {
                            response.setException(ex);
                        }
                        break;
                    case RETURN_PERFORMER:
                        try {
                            response.setResult(Controller.getInstance().getPerformer((Performer) request.getArgument()));
                        } catch (Exception ex) {
                            response.setException(ex);
                        }
                        break;
                    case RETURN_ALL_COUNTRIES:
                        try {
                            response.setResult(Controller.getInstance().getAllCountries());
                        } catch (Exception ex) {
                            response.setException(ex);
                        }
                        break;
                    case RETURN_ALL_GENRES:
                        try {
                            response.setResult(Controller.getInstance().getAllGenres());
                        } catch (Exception ex) {
                            response.setException(ex);
                        }
                        break;
                    case RETURN_ALL_USERS:
                        try {
                            response.setResult(Controller.getInstance().getAllUsers());
                        } catch (Exception ex) {
                            response.setException(ex);
                        }
                        break;
                    case DELETE_PERFORMER:
                        try {
                            Controller.getInstance().removePerformer((Performer) request.getArgument());
                        } catch (Exception e) {
                            response.setException(e);
                        }
                        break;
                    case ADD_PERFORMER:
                        try {
                            Performer performer = (Performer) request.getArgument();
                            performer.setUser(request.getUser());
                            Controller.getInstance().savePerformer(performer);
                        } catch (Exception e) {
                            response.setException(e);
                        }
                        break;
                    case ADD_ALBUM:
                        try {
                            Controller.getInstance().saveAlbum((Album) request.getArgument());
                        } catch (Exception e) {
                            response.setException(e);
                        }
                        break;
                    case UPDATE_ALBUM:
                        try {
                            Controller.getInstance().updateAlbum((Album) request.getArgument());
                        } catch (Exception e) {
                            response.setException(e);
                        }
                        break;
                    case RETURN_ALL_ALBUMS:
                        try {
                            response.setResult(Controller.getInstance().getAllAlbums());
                        } catch (Exception ex) {
                            response.setException(ex);
                        }
                        break;
                    case RETURN_ALBUM:
                        try {
                            response.setResult(Controller.getInstance().getAlbum((Album) request.getArgument()));
                        } catch (Exception ex) {
                            response.setException(ex);
                        }
                        break;
                    case SEARCH_PERFORMERS:
                        try {
                            response.setResult(Controller.getInstance().searchPerformers((Performer) request.getArgument()));
                        } catch (Exception ex) {
                            response.setException(ex);
                        }
                        break;
                    case SEARCH_ALBUMS:
                        try {
                            response.setResult(Controller.getInstance().searchAlbums((Album) request.getArgument()));
                        } catch (Exception ex) {
                            response.setException(ex);
                        }
                        break;
                    case DELETE_ALBUM:
                        try {
                            Controller.getInstance().removeAlbum((Album) request.getArgument());
                        } catch (Exception ex) {
                            response.setException(ex);
                        }
                        break;
                }
                sender.send(response);
            }
        } catch (Exception e) {
            StartServerThread.clients.remove(this.user);
            System.out.println("Client disconnected!");
        }
    }

}
