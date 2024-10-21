package ch.makery.address;

import ch.makery.address.model.Game;
import ch.makery.address.view.GameEditDialogController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import java.awt.*;
import java.io.IOException;

import ch.makery.address.view.GameOverviewController;


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

        // Устанавливаем иконку приложения.
        this.primaryStage.getIcons().add(new Image("file:resources/ch/makery/address/view/game_icon.png"));

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
            loader.setLocation(MainApp.class.getResource("/ch/makery/address/view/GameOverview.fxml"));
            AnchorPane gamesOverview = (AnchorPane) loader.load();

            rootLayout.setCenter(gamesOverview);

            // Даем контроллеру доступ к mainApp.
            GameOverviewController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Открывает диалоговое окно для изменения деталей указанного адресата.
     * Если пользователь кликнул OK, то изменения сохраняются в предоставленном
     * объекте адресата и возвращается значение true.
     *
     * @param game - объект адресата, который надо изменить
     * @return true, если пользователь кликнул OK, в противном случае false.
     */
    public boolean showGameEditDialog(Game game) {
        try {
            // Загружаем fxml-файл и создаём новую сцену
            // для всплывающего диалогового окна.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/ch/makery/address/view/GameEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Создаём диалоговое окно Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Person");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Передаём адресата в контроллер.
            GameEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setGame(game);


            // Отображаем диалоговое окно и ждём, пока пользователь его не закроет
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }



    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
