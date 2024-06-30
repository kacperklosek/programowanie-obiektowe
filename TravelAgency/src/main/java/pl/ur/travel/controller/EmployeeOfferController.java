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
import javafx.stage.Modality;
import javafx.stage.Stage;
import pl.ur.travel.ServiceProvider;
import pl.ur.travel.model.dao.Offer;

import java.io.IOException;

public class EmployeeOfferController {
    @FXML
    private ListView<Offer> offersList;

    private ObservableList<Offer> offersView;

    @FXML
    private Button addOfferBtn;

    @FXML Button addCostBtn;


    public void addOffer() throws IOException {
        // Create a new stage
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddOffer.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Add offer");
        stage.setScene(new Scene(root, Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT));

        // Show the stage
        stage.showAndWait();
    }

    public void addCost() throws IOException {
        // Create a new stage
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddCost.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Add cost");
        stage.setScene(new Scene(root, Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT));

        // Show the stage
        stage.showAndWait();
    }

    @FXML
    public void initialize() {
        offersView = FXCollections.observableList(ServiceProvider.eS.getAllOffers());

        offersList.setItems(offersView);

        offersList.setCellFactory(listView -> new OfferListCell());
    }

    private static class OfferListCell extends ListCell<Offer> {
        private Button detailsButton;

        public OfferListCell() {
            super();

            detailsButton = new Button("Details");
            detailsButton.setOnAction(event -> {
                var offer = getItem();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/OfferDetailsEmployee.fxml"));
                try {
                    Parent root = loader.load();
                    EmployeeDetailsController controller = loader.getController();
                    controller.initData(offer.id(), offer.name());
                    // Create a new stage
                    Stage stage = new Stage();
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.setTitle("Details Window");
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