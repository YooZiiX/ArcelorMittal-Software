package fr.arcelormittal.Managers;

import fr.arcelormittal.Models.Password;
import fr.arcelormittal.Models.Stand;
import fr.arcelormittal.Models.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DAOManager {

    private static DAOManager instance = null;
    private Connection connection = null;
    private final static Logger LOGGER = LoggerFactory.getLogger(DAOManager.class);

    private DAOManager() throws IOException {
        Properties props = new Properties();
        props.load(new FileReader("db.properties"));
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection c = DriverManager.getConnection(props.getProperty("url"),props.getProperty("user"),props.getProperty("password"));
            this.connection = c;
        } catch (Exception e){
            LOGGER.error("ERROR : {}", e.getCause());
        }
    }

    public static DAOManager getInstance() throws IOException {
        if (instance == null) instance = new DAOManager();
        return instance;
    }

    public Connection getConnection(){
        return this.connection;
    }

    public void addUser(String name, String email, String role){
        try {
            PreparedStatement pStmt = connection.prepareStatement("INSERT INTO USERS (userName, userMail, hPwd, userRole)" +
                    "VALUES (?,?,?,(SELECT roleID FROM USERROLES WHERE roleLabel LIKE ?));");
            pStmt.setString(1, name);
            pStmt.setString(2, email);
            pStmt.setString(3, Password.doHashing(Password.getRandomPassword()));
            pStmt.setString(4,role);
            pStmt.executeUpdate();
            pStmt.close();
        } catch (Exception e) {
            LOGGER.error("ERROR : {}", e.getCause());
        }
    }

    public void deleteUser(int id) {
        try {
            PreparedStatement pStmt = connection.prepareStatement("DELETE FROM users WHERE userID = ?");
            pStmt.setInt(1,id);
            pStmt.executeUpdate();
            pStmt.close();
        } catch (Exception e) {
            LOGGER.error("ERROR : {}", e.getCause());
        }
    }

    public void updateUser(int id, String name, String mail, String password, String role) {
        try {
            PreparedStatement pStmt = connection.prepareStatement("UPDATE users SET userName = ?," +
                    " userMail = ?, hPwd = ?, userRole = " +
                    "(SELECT roleID FROM userroles WHERE roleLabel LIKE ?)" +
                    " WHERE userID = ?");
            pStmt.setString(1, name);
            pStmt.setString(2,mail);
            pStmt.setString(3,Password.doHashing(password));
            pStmt.setString(4,role);
            pStmt.setInt(5,id);
            pStmt.executeUpdate();
            pStmt.close();
        } catch (Exception e) {
            LOGGER.error("ERROR : {}", e.getCause());
        }
    }

    public List<User> getUsers(){
        List<User> returnList = new ArrayList<User>();
        try {
            PreparedStatement pStmt = connection.prepareStatement("SELECT userID, userName, userMail, hPwd, userRole FROM users;");
            ResultSet result = pStmt.executeQuery();
            boolean next = result.next();
            while (next) {
                    returnList.add(new User(result.getInt("userID"),
                            result.getString("userName"),
                            result.getString("userMail"),
                            result.getString("hPwd"),
                            result.getInt("userRole")));
                    next = result.next();
            }
            pStmt.close();
        } catch (Exception e) {
            LOGGER.error("ERROR : {}", e.getCause());
        }
        return returnList;
    }

    public Stand getStand(int id) {
        Stand returnStand = null;
        try {
            PreparedStatement pStmt = connection.prepareStatement("SELECT isEnable FROM stands WHERE standID = ?;");
            pStmt.setInt(1,id);
            ResultSet result = pStmt.executeQuery();
            boolean next = result.next();
            while(next) {
                returnStand = new Stand(id, result.getBoolean("isEnable"));
                next = result.next();
            }
            pStmt.close();
        } catch (Exception e) {
            LOGGER.error("ERROR : {}", e.getCause());
        }
        return returnStand;
    }

    public void updateStand(int id, boolean state){
        try {
            PreparedStatement pStmt = connection.prepareStatement("UPDATE stands SET isEnable = ? WHERE standID = ?;");
            pStmt.setBoolean(1,state);
            pStmt.setInt(2,id);
            pStmt.executeUpdate();
            pStmt.close();
        } catch (Exception e) {
            LOGGER.error("ERROR : {}", e.getCause());
        }
    }

    public List<Stand> getStands() {
        List<Stand> returnList = new ArrayList<Stand>();
        try {
            PreparedStatement pStmt = connection.prepareStatement("SELECT standID, isEnable FROM stands;");
            ResultSet result = pStmt.executeQuery();
            boolean next = result.next();
            while (next) {
                returnList.add(new Stand(result.getInt("standID"), result.getBoolean("isEnable")));
                next = result.next();
            }
            pStmt.close();
        } catch (Exception e) {
            LOGGER.error("ERROR : {}", e.getCause());
        }
        return returnList;
    }

}
