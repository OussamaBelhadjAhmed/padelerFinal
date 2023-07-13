package gui.comptes;

import entite.User;

public class SessionContext {
    private static SessionContext instance;
    private User loggedInUser;

    private SessionContext() {

    }

    public static synchronized SessionContext getInstance() {
        if (instance == null) {
            instance = new SessionContext();
        }
        return instance;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(User user) {
        loggedInUser = user;
    }

    public void clearSession() {
        loggedInUser = null;
    }

    public boolean isLoggedIn() {
        return loggedInUser != null;
    }
}
