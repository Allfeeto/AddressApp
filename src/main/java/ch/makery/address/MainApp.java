package ch.makery.address;

import ch.makery.address.model.Game;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

import ch.makery.address.view.GamesOverviewController;


public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;

    // Наблюдаемый список игр.
    private ObservableList<Game> gameData = FXCollections.observableArrayList();

    /**
     * Конструктор
     */
    public MainApp() {
        // Добавляем тестовые данные для игр.
        gameData.add(new Game("Cyberpunk 2077", 2020, "RPG", "High-end PC"));
        gameData.add(new Game("The Witcher 3", 2015, "RPG", "Medium PC"));
        gameData.add(new Game("Minecraft", 2011, "Sandbox", "Low-end PC"));
        // Можно добавить больше тестовых данных.
    }

    /**
     * Возвращает список игр.
     */
    public ObservableList<Game> getGameData() {
        return gameData;
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("GamesApp");

        initRootLayout();
        showGamesOverview();
    }

    /**
     * Инициализирует корневой макет.
     */
    public void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/ch/makery/address/view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Показывает экран со списком игр.
     */
    public void showGamesOverview() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/ch/makery/address/view/GamesOverview.fxml"));
            AnchorPane gamesOverview = (AnchorPane) loader.load();

            rootLayout.setCenter(gamesOverview);

            // Даем контроллеру доступ к mainApp.
            GamesOverviewController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
