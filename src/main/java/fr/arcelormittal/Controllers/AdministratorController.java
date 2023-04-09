package fr.arcelormittal.Controllers;

import fr.arcelormittal.Helpers.ApplicationHelper;
import fr.arcelormittal.Managers.DAOManager;
import fr.arcelormittal.Models.Application;
import fr.arcelormittal.Models.User;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdministratorController implements Initializable {

    @FXML
    private VBox updateVBox;
    @FXML
    private ChoiceBox<String> roleChoiceBox, updateChoiceBox;
    @FXML
    private TextField nameField, emailField, updateName, updateEmail, updatePassword;
    @FXML
    private ListView<User> usersListView;
    @FXML
    private Label idLabel;

    private User currentUser = null;
    private String[] role = {"Worker", "Process Engineer", "Administrator"};

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        roleChoiceBox.getItems().addAll(role);
        updateChoiceBox.getItems().addAll(role);
        updateVBox.setVisible(false);
        updateVBox.setDisable(true);
        setListView();
    }

    @FXML
    private void onAjouterClick(ActionEvent actionEvent) throws IOException {
        String name = nameField.getText();
        String email = emailField.getText();
        String role = roleChoiceBox.getValue();
        DAOManager.getInstance().addUser(name,email,role);
        setListView();
    }

    @FXML
    private void onListItemsClick(MouseEvent mouseEvent) {
        this.currentUser = usersListView.getSelectionModel().getSelectedItem();

        updateVBox.setDisable(false);
        idLabel.setText(String.valueOf(currentUser.getId()));
        updateName.setText(currentUser.getName());
        updateEmail.setText(currentUser.getEmail());
        updatePassword.setText(currentUser.gethPassword());
        updateChoiceBox.setValue(currentUser.getRole());
        updateVBox.setVisible(true);
    }

    @FXML
    private void onUpdateClick(ActionEvent actionEvent) {
        try {
            currentUser.setName(updateName.getText());
            currentUser.setEmail(updateEmail.getText());
            currentUser.setRole(updateChoiceBox.getValue());
            currentUser.sethPassword(updatePassword.getText());
            ApplicationHelper.updateUser(currentUser);
            setListView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onDeleteClick(ActionEvent actionEvent) {
        try {
            if (! currentUser.getRole().equals("Administrateur")) {
                ApplicationHelper.deleteUser(currentUser);
            }
            setListView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setListView() {
        try {
            usersListView.setItems(FXCollections.observableArrayList(ApplicationHelper.getUsers()));
            updateVBox.setVisible(false);
            updateVBox.setDisable(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}