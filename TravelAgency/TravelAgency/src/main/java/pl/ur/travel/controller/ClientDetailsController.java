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

public class ClientDetailsController {

    private UUID offerId;

    @FXML
    private Text totalField;

    @FXML
    private Text offerName;

    @FXML
    private ListView<Cost> costList;

    private ObservableList<Cost> costView;

    @FXML
    private Button closeBtn;

    private Stage stage;

    private Runnable callback;

    public void close() {
        this.stage.close();
    }

    public void initData(Stage stage, UUID offerId, String name, Runnable callback) {
        offerName.setText(name);
        this.stage = stage;
        this.offerId = offerId;
        List<Cost> costs = ServiceProvider.eS.getAllCostsForOffer(this.offerId);
        costView = FXCollections.observableList(costs);

        costList.setItems(costView);

        costList.setCellFactory(ig -> new CostListCell());

        totalField.setText(String.valueOf(
                costs.stream().map(Cost::cost).reduce(Double::sum).orElse(0.0)

        ));
        this.callback = callback;
    }

    public void reserve() {
        ServiceProvider.uS.selectOffer(this.offerId);
        callback.run();
        
    }

    private class CostListCell extends ListCell<Cost> {
        public CostListCell() {
            super();
        }

        @Override
        protected void updateItem(Cost item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setText(null);
                setGraphic(null);
            } else {
                setText(item.toString());
            }
        }
    }

}