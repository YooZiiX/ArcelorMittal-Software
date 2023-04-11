package fr.arcelormittal.Controllers;

import fr.arcelormittal.Models.Application;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class WorkerController implements Initializable {

    @FXML
    private Button computeButton;
    private boolean isComputing = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @FXML
    private void onComputeClick(MouseEvent mouseEvent) {
        isComputing = !isComputing;
        if (isComputing) {
            computeButton.setText("Stop");
            Application.getInstance().startTask();
        } else if (!isComputing) {
            computeButton.setText("Compute");
            Application.getInstance().endTask();
        }
    }
}
