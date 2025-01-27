package pl.ur.travel.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class TravelProjectController {

    @FXML
    private Button employeeSelectionBtn;

    @FXML
    private Button clientSelectionBtn;

    @FXML
    private Button closeBtn;

    private Stage stage;

    public void init(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void employeeSelected() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/OfferEmployee.fxml"));
            Parent root = fxmlLoader.load();

           EmployeeOfferController controller = fxmlLoader.getController();

            Stage stage = new Stage();
            stage.setTitle("Podgląd ofert - pracownik.");
            stage.setScene(new Scene(root, Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT));
            stage.show();

            controller.init(stage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void clientSelected() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/OfferClient.fxml"));
            Parent root = fxmlLoader.load();

            ClientOfferController controller = fxmlLoader.getController();

            Stage stage = new Stage();
            stage.setTitle("Podgląd ofert - klient.");
            stage.setScene(new Scene(root, Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT));
            stage.show();

            controller.init(stage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        this.stage.close();
    }
}
