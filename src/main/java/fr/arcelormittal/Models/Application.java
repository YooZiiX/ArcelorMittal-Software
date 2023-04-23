package fr.arcelormittal.Models;

import fr.arcelormittal.Helpers.ApplicationHelper;
import fr.arcelormittal.Managers.FileManager;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;

import java.io.IOException;
import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;
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
    private int time = 0;

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

    public Stand getStand() {
        return stand;
    }

    public void setStand(Stand stand) {
        this.stand = stand;
    }

    public void startTask(Label computeLabel, LineChart frictionChart, LineChart sigmaChart, LineChart rollSpeedChart){
        this.timer = new Timer();
        XYChart.Series frictionSeries = new XYChart.Series();
        XYChart.Series sigmaSeries = new XYChart.Series();
        XYChart.Series rollSpeedSeries = new XYChart.Series();
        this.task = new TimerTask() {
            private int count = 0;
            Instant start = Instant.now();
            @Override
            public void run() {
                try {
                    if (count == 5) {
                        count = 0;
                        time ++;
                        double[] values = ApplicationHelper.computeMean();
                        frictionSeries.getData().add(new XYChart.Data(time,values[0]));
                        frictionChart.getData().add(frictionSeries);
                        sigmaSeries.getData().add(new XYChart.Data(time,values[1]));
                        sigmaChart.getData().add(sigmaSeries);
                        rollSpeedSeries.getData().add(new XYChart.Data(time,values[2]));
                        rollSpeedChart.getData().add(rollSpeedSeries);
                        System.out.println("Mean!");
                    }
                    count++;
                    Instant end = Instant.now();
                    Duration time = Duration.between(start, end);
                    FileManager.getInstance().initCompute();
                    ApplicationHelper.orowanCompute();
                    FileManager.getInstance().readOutput();
                } catch (IOException | SQLException e) {
                    e.printStackTrace();
                }
            }
        };
        timer.scheduleAtFixedRate(task,0,200);
    }

    public void endTask(){
        timer.cancel();
        this.timer = null;
        this.task = null;
    }
}
