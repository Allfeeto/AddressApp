package ch.makery.address.view;

import ch.makery.address.model.Game;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import ch.makery.address.MainApp;


public class GameOverviewController {

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

        // Очистка дополнительной информации об адресате.
        showGameDetails(null);

        // Слушаем изменения выбора, и при изменении отображаем
        // дополнительную информацию об адресате.
        gamesTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showGameDetails(newValue));
    }

    /**
     * Устанавливаем ссылку на MainApp.
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        // Добавляем данные в таблицу.
        gamesTable.setItems(mainApp.getGameData());
    }
    /**
     * Заполняет все текстовые поля, отображая подробности об адресате.
     * Если указанный адресат = null, то все текстовые поля очищаются.
     *
     * @param game — адресат типа Person или null
     */
    private void showGameDetails(Game game) {
        if (game != null) {
            // Заполняем метки информацией из объекта person.
            titleLabel.setText(game.getTitle());
            yearLabel.setText(Integer.toString(game.getYear()));
            genreLabel.setText(game.getGenre());
            systemLabel.setText(game.getSystemRequirements());

        // TODO: пока не нужен способ для перевода дня рождения в тип String, так как у меня нет таких полей
            // Если понадобится - возьму инструкцию с 3 части лабораторной.

        } else {
            // Если Person = null, то убираем весь текст.
            titleLabel.setText("");
            yearLabel.setText("");
            genreLabel.setText("");
            systemLabel.setText("");
        }
    }

    /**
     * Вызывается, когда пользователь кликает по кнопке удаления.
     */
    @FXML
    private void handleDeleteGame() {
        int selectedIndex = gamesTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            gamesTable.getItems().remove(selectedIndex);
        } else {
            // Ничего не выбрано.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Game Selected");
            alert.setContentText("Please select a game in the table.");

            alert.showAndWait();
        }
    }

    /**
     * Вызывается, когда пользователь кликает по кнопке New...
     * Открывает диалоговое окно с дополнительной информацией нового адресата.
     */
    @FXML
    private void handleNewGame() {
        Game tempGame = new Game();
        boolean okClicked = mainApp.showGameEditDialog(tempGame);
        if (okClicked) {
            mainApp.getGameData().add(tempGame);
        }
    }

    /**
     * Вызывается, когда пользователь кликает по кнопка Edit...
     * Открывает диалоговое окно для изменения выбранного адресата.
     */

    @FXML
    private void handleEditGame() {
        Game selectedGame = gamesTable.getSelectionModel().getSelectedItem();
        if (selectedGame != null) {
            boolean okClicked = mainApp.showGameEditDialog(selectedGame);
            if (okClicked) {
                showGameDetails(selectedGame);
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Game Selected");
            alert.setContentText("Please select a game in the table.");

            alert.showAndWait();
        }
    }









}
