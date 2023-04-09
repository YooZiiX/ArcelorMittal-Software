package fr.arcelormittal.Models;

import fr.arcelormittal.Helpers.ApplicationHelper;

import java.io.IOException;
import java.util.List;

public class Application {

    private static Application instance = null;
    private User user = null;
    private List<User> userList = null;
    private Stand stand = null;
    private List<Stand> standList = null;

    private Application() throws IOException {
        userList = ApplicationHelper.getUsers();
        standList = ApplicationHelper.getStands();
    }

    public static Application getInstance() {
        try {
            if (instance == null) {
                instance = new Application();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return instance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public Stand getStand() {
        return stand;
    }

    public void setStand(Stand stand) {
        this.stand = stand;
    }
}
