package fr.arcelormittal.Models;

import fr.arcelormittal.Helpers.ApplicationHelper;
import fr.arcelormittal.Managers.DAOManager;
import fr.arcelormittal.Managers.FileManager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Application {

    private static Application instance = null;
    private User user = null;
    private List<User> userList = null;
    private Stand stand = null;
    private List<Stand> standList = null;
    private Timer timer = null;
    private TimerTask task = null;
    private int count = 0;

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

    public void startTask(){
        this.timer = new Timer();
        this.task = new TimerTask() {
            private int count = 0;

            @Override
            public void run() {
                try {
                    if (count == 5) {
                        count = 0;
                        ApplicationHelper.computeMean();
                        System.out.println("Mean!");
                    }
                    count++;
                    FileManager.getInstance().initCompute();
                    ApplicationHelper.orowanCompute();
                    FileManager.getInstance().readOutput();
                } catch (IOException | SQLException e) {
                    e.printStackTrace();
                }
            }
        };
        //timer.schedule(task,1000);
        timer.scheduleAtFixedRate(task,0,200);
    }

    public void endTask(){
        timer.cancel();
        this.timer = null;
        this.task = null;
    }
}
