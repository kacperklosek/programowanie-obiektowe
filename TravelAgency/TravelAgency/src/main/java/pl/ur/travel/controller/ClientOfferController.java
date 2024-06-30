package pl.ur.travel.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pl.ur.travel.ServiceProvider;
import pl.ur.travel.model.dao.Cost;
import pl.ur.travel.model.dao.Offer;

import java.io.IOException;
import java.util.Collection;

public class ClientOfferController {

    @FXML
    private ListView<Offer> offersList;

    private ObservableList<Offer> offersView;

    @FXML
    private Text totalToBePaid;

    @FXML
    private Button closeBtn;

    private Stage stage;

    public void init(Stage stage) {
        this.stage = stage;
    }

    public void close() {
        this.stage.close();
    }

    @FXML
    public void initialize() {
        offersView = FXCollections.observableList(ServiceProvider.eS.getAllOffers());

        offersList.setItems(offersView);

        offersList.setCellFactory(listView -> new OfferListCell());

        totalToBePaid.setText(
                String.valueOf(ServiceProvider.uS.getAllOffers()
                        .stream()
                        .filter(offer -> !offer.accepted())
                        .map(Offer::id)
                        .map(ServiceProvider.uS::getAllCostsForOffer)
                        .flatMap(Collection::stream)
                        .map(Cost::cost)
                        .reduce(Double::sum)
                        .orElse(0.0)
                )
        );
    }

    private class OfferListCell extends ListCell<Offer> {
        private Button detailsButton;

        public OfferListCell() {
            super();

            detailsButton = new Button("Szczegóły");

            detailsButton.setOnAction(event -> {
                var offer = getItem();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/OfferDetailsClient.fxml"));
                try {
                    Parent root = loader.load();
                    ClientDetailsController controller = loader.getController();
                    Stage stage = new Stage();
                    controller.initData(stage, offer.id(), offer.name(), ClientOfferController.this::initialize);

                    // Create a new stage
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.setTitle("Szczegóły");
                    stage.setScene(new Scene(root, Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT));

                    // Show the stage
                    stage.showAndWait();
                } catch (IOException e) {
                    throw new RuntimeException(e); // TODO
                }
            });
        }

        @Override
        protected void updateItem(Offer item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setText(null);
                setGraphic(null);
            } else {
                setText(item.toString());
                setGraphic(detailsButton);
            }
        }
    }
}