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

    private User selectedUSer = null;
    private String[] roles = {"Worker", "Process Engineer", "Administrator"};

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        roleChoiceBox.getItems().addAll(roles);
        updateChoiceBox.getItems().addAll(roles);
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
        this.selectedUSer = usersListView.getSelectionModel().getSelectedItem();

        updateVBox.setDisable(false);
        idLabel.setText(String.valueOf(selectedUSer.getId()));
        updateName.setText(selectedUSer.getName());
        updateEmail.setText(selectedUSer.getEmail());
        updatePassword.setText(selectedUSer.getPassword());
        updateChoiceBox.setValue(selectedUSer.getRole());
        updateVBox.setVisible(true);
    }

    @FXML
    private void onUpdateClick(ActionEvent actionEvent) {
        try {
            selectedUSer.setName(updateName.getText());
            selectedUSer.setEmail(updateEmail.getText());
            selectedUSer.setRole(updateChoiceBox.getValue());
            selectedUSer.setPassword(updatePassword.getText());
            ApplicationHelper.updateUser(selectedUSer);
            setListView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onDeleteClick(ActionEvent actionEvent) {
        try {
            if (! selectedUSer.getRole().equals("Administrateur")) {
                ApplicationHelper.deleteUser(selectedUSer);
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