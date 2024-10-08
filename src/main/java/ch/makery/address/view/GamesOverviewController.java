package ch.makery.address.view;

import ch.makery.address.model.Game;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import ch.makery.address.MainApp;


public class GamesOverviewController {

    @FXML
    private TableView<Game> gamesTable;
    @FXML
    private TableColumn<Game, String> titleColumn;
    @FXML
    private TableColumn<Game, Integer> yearColumn;

    @FXML
    private Label titleLabel;

    @FXML
    private Label yearLabel;

    @FXML
    private Label genreLabel;

    @FXML
    private Label systemLabel;


    // Ссылка на основной класс приложения.
    private MainApp mainApp;

    /**
     * Инициализация таблицы.
     */
    @FXML
    private void initialize() {
        // Инициализируем таблицу с двумя колонками.
        titleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
        yearColumn.setCellValueFactory(cellData -> cellData.getValue().yearProperty().asObject());

        // Пример, как можно обновлять метки, если у вас есть выбранный элемент
        gamesTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                titleLabel.setText(newValue.getTitle());
                yearLabel.setText(String.valueOf(newValue.getYear()));
                genreLabel.setText(newValue.getGenre());
                systemLabel.setText(newValue.getSystemRequirements());
            }
        });
    }

    /**
     * Устанавливаем ссылку на MainApp.
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        // Добавляем данные в таблицу.
        gamesTable.setItems(mainApp.getGameData());
    }
}
