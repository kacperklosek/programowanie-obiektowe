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
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pl.ur.travel.ServiceProvider;
import pl.ur.travel.model.dao.Cost;
import pl.ur.travel.model.dao.Offer;

import java.io.IOException;

public class EmployeeOfferController {
    @FXML
    private ListView<Offer> offersList;

    private ObservableList<Offer> offersView;

    @FXML
    private ListView<Cost> costList;

    private ObservableList<Cost> costView;

    @FXML
    private Button addOfferBtn;

    @FXML Button addCostBtn;

    @FXML Button closeBtn;

    private Stage stage;

    public void init(Stage stage) {
        this.stage = stage;
    }

    public void close() {
        this.stage.close();
    }

    public void addOffer() throws IOException {
        // Create a new stage
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddOffer.fxml"));
        Parent root = loader.load();
        AddOfferController controller = loader.getController();

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Add offer");
        stage.setScene(new Scene(root, Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT));

        controller.init(stage, this::initialize);

        // Show the stage
        stage.showAndWait();
    }

    public void addCost() throws IOException {
        // Create a new stage
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddCost.fxml"));
        Parent root = loader.load();
        AddCostController controller = loader.getController();

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Add cost");
        stage.setScene(new Scene(root, Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT));
        controller.initData(stage, this::initialize);

        // Show the stage
        stage.showAndWait();
    }

    @FXML
    public void initialize() {
        offersView = FXCollections.observableList(ServiceProvider.eS.getAllOffers());

        offersList.setItems(offersView);

        offersList.setCellFactory(ig -> new OfferListCell());

        costView = FXCollections.observableList(ServiceProvider.eS.getAllCosts());

        costList.setItems(costView);

        costList.setCellFactory(ig -> new CostListCell());
    }

    public class OfferListCell extends ListCell<Offer> {
        private Button detailsButton;
        private Button acceptButton;
        private Button deleteButton;
        private HBox hbox;

        public OfferListCell() {
            super();

            detailsButton = new Button("Szczegóły");
            acceptButton = new Button("Akceptuj");
            deleteButton = new Button("Usuń");

            hbox = new HBox(detailsButton, deleteButton, acceptButton);
            hbox.setSpacing(5); // Add some spacing between the buttons

            acceptButton.setOnAction(e -> {
                var offer = getItem();
                ServiceProvider.eS.markOfferAsPaid(offer.id());
                initialize();
            });

            deleteButton.setOnAction(e -> {
                var offer = getItem();
                ServiceProvider.eS.deleteOffer(offer.id());
                initialize();
            });

            detailsButton.setOnAction(event -> {
                var offer = getItem();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/OfferDetailsEmployee.fxml"));
                try {
                    Parent root = loader.load();
                    EmployeeDetailsController controller = loader.getController();
                    // Create a new stage
                    Stage stage = new Stage();
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.setTitle("Szczegóły");
                    stage.setScene(new Scene(root, Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT));
                    controller.initData(stage, offer.id(), offer.name());

                    // Show the stage
                    stage.showAndWait();
                    initialize();
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
                setGraphic(hbox);

                acceptButton.setVisible(!item.accepted());
            }
        }
    }

    private class CostListCell extends ListCell<Cost> {
        private Button deleteButton;

        public CostListCell() {
            super();

            deleteButton = new Button("Usuń");
            deleteButton.setOnAction(event -> {
                var cost = getItem();
                ServiceProvider.eS.deleteCost(cost.id());
                initialize();
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
                setGraphic(deleteButton);
            }
        }
    }

}