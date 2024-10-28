package ch.makery.address;

import ch.makery.address.model.Game;
import ch.makery.address.view.GameEditDialogController;
import ch.makery.address.view.RootLayoutController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import ch.makery.address.model.Game;
import ch.makery.address.model.GameListWrapper;



import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.prefs.Preferences;

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
        InputStream iconStream = getClass().getResourceAsStream("/ch/makery/address/view/game_icon.png");
        if (iconStream != null) {
            this.primaryStage.getIcons().add(new Image(iconStream));
        } else {
            System.out.println("Icon resource not found!");
        }



        initRootLayout();
        showGamesOverview();
    }

    /**
     * Инициализирует корневой макет и пытается загрузить последний открытый
     * файл с адресатами.
     */
    public void initRootLayout() {
        try {
            // Загружаем корневой макет из fxml файла.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/ch/makery/address/view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Отображаем сцену, содержащую корневой макет.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);

            // Даём контроллеру доступ к главному прилодению.
            RootLayoutController controller = loader.getController();
            controller.setMainApp(this);

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Пытается загрузить последний открытый файл с адресатами.
        File file = getGameFilePath();
        if (file != null) {
            loadGameDataFromFile(file);
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

    /**
     * Возвращает preference файла адресатов, то есть, последний открытый файл.
     * Этот preference считывается из реестра, специфичного для конкретной
     * операционной системы. Если preference не был найден, то возвращается null.
     *
     * @return
     */
    public File getGameFilePath() {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        String filePath = prefs.get("filePath", null);
        if (filePath != null) {
            return new File(filePath);
        } else {
            return null;
        }
    }

    /**
     * Задаёт путь текущему загруженному файлу. Этот путь сохраняется
     * в реестре, специфичном для конкретной операционной системы.
     *
     * @param file - файл или null, чтобы удалить путь
     */
    public void setGameFilePath(File file) {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        if (file != null) {
            prefs.put("filePath", file.getPath());

            // Обновление заглавия сцены.
            primaryStage.setTitle("GamesApp - " + file.getName());
        } else {
            prefs.remove("filePath");

            // Обновление заглавия сцены.
            primaryStage.setTitle("GamesApp");
        }
    }


    /**
     * Загружает информацию об адресатах из указанного файла.
     * Текущая информация об адресатах будет заменена.
     *
     * @param file
     */
    public void loadGameDataFromFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(GameListWrapper.class);
            Unmarshaller um = context.createUnmarshaller();

            // Чтение XML из файла и демаршализация.
            GameListWrapper wrapper = (GameListWrapper) um.unmarshal(file);

            gameData.clear();
            gameData.addAll(wrapper.getGames());

            // Сохраняем путь к файлу в реестре.
            setGameFilePath(file);

        } catch (Exception e) { // catches ANY exception
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not load data");
            alert.setContentText("Could not load data from file:\n" + file.getPath());

            alert.showAndWait();
        }
    }

    /**
     * Сохраняет текущую информацию об адресатах в указанном файле.
     *
     * @param file
     */
    public void saveGameDataToFile(File file) {
        try {
            JAXBContext context = JAXBContext.newInstance(GameListWrapper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // Обёртываем наши данные об адресатах.
            GameListWrapper wrapper = new GameListWrapper();
            wrapper.setGames(gameData);

            // Маршаллируем и сохраняем XML в файл.
            m.marshal(wrapper, file);

            // Сохраняем путь к файлу в реестре.
            setGameFilePath(file);
        } catch (Exception e) { // catches ANY exception
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not save data");
            alert.setContentText("Could not save data to file:\n" + file.getPath());

            alert.showAndWait();
            // Log the exception stack trace
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
