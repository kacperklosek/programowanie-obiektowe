package pl.ur.travel.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import pl.ur.travel.ServiceProvider;
import pl.ur.travel.model.dao.Cost;
import pl.ur.travel.model.dao.Offer;

import java.util.UUID;

public class ClientDetailsController {

    private UUID offerId;

    @FXML
    private Text offerName;

    @FXML
    private ListView<Cost> costList;

    private ObservableList<Cost> costView;

    public void initData(UUID offerId, String name) {
        this.offerId = offerId;
        offerName.setText(name);
    }

    @FXML
    public void initialize() {
        costView = FXCollections.observableList(ServiceProvider.eS.getAllCostsForOffer(this.offerId));
    }

    // TODO @PC go back once offers and costs are bounded by the employee
}
