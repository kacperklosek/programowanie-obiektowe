package pl.ur.travel;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.ur.travel.controller.Config;
import pl.ur.travel.controller.TravelProjectController;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Config.LANDING_PAGE_URI));
        Parent root = loader.load();

        TravelProjectController tpc = loader.getController();
        tpc.init(primaryStage);

        primaryStage.setTitle("Biuro podróżnicze");
        primaryStage.setScene(new Scene(root, Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
