package pl.ur.travel.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import pl.ur.travel.ServiceProvider;
import pl.ur.travel.model.dao.Cost;

import java.util.List;
import java.util.UUID;

public class EmployeeDetailsController {

    private UUID offerId;

    @FXML
    private Text offerName;

    @FXML
    private Text totalField;

    @FXML
    private ListView<Cost> costList;

    private ObservableList<Cost> costView;

    @FXML
    private ListView<Cost> costList2;

    private ObservableList<Cost> costView2;

    @FXML
    private Button closeBtn;

    private Stage stage;

    public void close() {
        this.stage.close();
    }

    public void initData(Stage stage, UUID offerId, String name) {
        offerName.setText(name);
        this.stage = stage;
        this.offerId = offerId;
        reload();
    }

    private void reload() {
        List<Cost> costs = ServiceProvider.eS.getAllCostsForOffer(this.offerId);

        costView = FXCollections.observableList(costs);

        costList.setItems(costView);

        costList.setCellFactory(ig -> new CostListCell());

        List<Cost> costs2 = ServiceProvider.eS.getAllCosts();

        costView2 = FXCollections.observableList(costs2);

        costList2.setItems(costView2);

        costList2.setCellFactory(ig -> new CostListCell2());

        totalField.setText(String.valueOf(
                costs.stream().map(Cost::cost).reduce(Double::sum).orElse(0.0)

        ));
    }

    private class CostListCell extends ListCell<Cost> {

        private Button delete;

        public CostListCell() {
            super();
            delete = new Button("Usuń");
            delete.setOnAction(e -> {
                var cost = getItem();
                ServiceProvider.eS.deleteCostFromOffer(offerId, cost.id());
                reload();
            });
        }

        @Override
        protected void updateItem(Cost item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setText(null);
                setGraphic(null);
            } else {
                setText(item.toString());
                setGraphic(delete);
            }
        }
    }

    private class CostListCell2 extends ListCell<Cost> {
        private Button addButton;

        public CostListCell2() {
            super();

            addButton = new Button("Dodaj");
            addButton.setOnAction(event -> {
                var cost = getItem();
                ServiceProvider.eS.addCostToOffer(offerId, cost.id());
                reload();
            });
        }

        @Override
        protected void updateItem(Cost item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setText(null);
                setGraphic(null);
            } else {
                setText(item.toString());
                setGraphic(addButton);
            }
        }
    }
}
