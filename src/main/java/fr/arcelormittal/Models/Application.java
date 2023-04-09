package fr.arcelormittal.Models;

import fr.arcelormittal.Helpers.ApplicationHelper;

import java.io.IOException;
import java.util.List;

public class Application {

    private static Application instance = null;
    private User user = null;
    private List<User> userList = null;

    private Application() throws IOException {
        userList = ApplicationHelper.getUsers();
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
}
