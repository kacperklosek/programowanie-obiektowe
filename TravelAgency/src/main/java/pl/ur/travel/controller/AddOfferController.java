package pl.ur.travel.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pl.ur.travel.ServiceProvider;
import pl.ur.travel.model.dao.Cost;
import pl.ur.travel.model.dao.Offer;
import pl.ur.travel.model.dao.OfferCost;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;

public class AddOfferController {

    @FXML
    private TextField offerField;

    @FXML
    private ListView<Cost> costList;

    private ObservableList<Cost> costView;

    private Set<Cost> selected = new HashSet<>();

    @FXML
    private Button closeBtn;

    private Stage stage;

    private Runnable callback;

    public void init(Stage stage, Runnable callback) {
        this.stage = stage;
        this.callback = callback;
    }

    public void close() {
        this.stage.close();
    }

    @FXML
    public void initialize() {
        costView = FXCollections.observableList(ServiceProvider.eS.getAllCosts());

        costList.setItems(costView);

        costList.setCellFactory(ig -> new CostListCell());
    }

    private class CostListCell extends ListCell<Cost> {
        private Button selectBtn;

        public CostListCell() {
            super();

            selectBtn = new Button("Wybierz");
            selectBtn.setOnAction(event -> {
                var cost = getItem();
                selected.add(cost);
                selectBtn.setText("Wybrane");
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
                setGraphic(selectBtn);
            }
        }
    }

    public void addClick() {
        String name = offerField.getText();
        var offer = new Offer(UUID.randomUUID(), name, false);
        ServiceProvider.eS.addOffer(offer);
        selected.stream()
                .map(Cost::id)
                .forEach(id -> ServiceProvider.eS.addCostToOffer(offer.id(), id));

        callback.run();

        stage.close();
    }
}
