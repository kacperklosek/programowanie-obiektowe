package pl.ur.travel.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pl.ur.travel.ServiceProvider;
import pl.ur.travel.model.dao.Cost;
import pl.ur.travel.model.dao.CostType;

import java.util.Objects;
import java.util.UUID;
import java.util.function.Consumer;

public class AddCostController {

    private Stage stage;

    @FXML
    private TextField nameField;

    @FXML
    private TextField costField;

    @FXML
    private RadioButton accRBtn;

    @FXML
    private RadioButton resRBtn;

    @FXML
    private RadioButton ticketRBtn;

    @FXML
    private Button addBtn;

    @FXML
    private Button cancelBtn;

    private Runnable callback;

    public void initData(Stage stage, Runnable callback) {
        this.stage = stage;
        this.callback = callback;
    }

    public void accClick() {
        resRBtn.setSelected(false);
        ticketRBtn.setSelected(false);
    }

    public void ticketClick() {
        accRBtn.setSelected(false);
        resRBtn.setSelected(false);
    }

    public void resClick() {
        ticketRBtn.setSelected(false);
        accRBtn.setSelected(false);
    }

    public void close() {
        this.stage.close();
    }

    public void addClick() {
        // TODO Kacper, tu nie ma żadnych walidacji
        //  - nie wpisuj głupot bo będą się dziać dziwne rzeczy
        String name = nameField.getText();
        Double cost = Double.valueOf(costField.getText()); // !!! wpiszesz "pies" i leci wyjątek

        CostType type = null;
        if (accRBtn.isSelected()) {
            type = CostType.ACCOMMODATION;
        }

        if (ticketRBtn.isSelected()) {
            type = CostType.TICKET;
        }

        if (resRBtn.isSelected()) {
            type = CostType.RESERVATION;
        }

        if (type == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Uwaga!");
            alert.setHeaderText(null);
            alert.setContentText("Typ wydatku jest wymagany.");

            alert.showAndWait();
        } else {
            ServiceProvider.eS.addCost(new Cost(UUID.randomUUID(), type, cost, name));
            this.callback.run();
            this.stage.close();
        }

    }
}
