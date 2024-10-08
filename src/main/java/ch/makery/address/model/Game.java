package ch.makery.address.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.IntegerProperty;

public class Game {
    private final StringProperty title;
    private final IntegerProperty year;
    private final StringProperty genre;
    private final StringProperty systemRequirements;

    public Game(String title, int year, String genre, String systemRequirements) {
        this.title = new SimpleStringProperty(title);
        this.year = new SimpleIntegerProperty(year);
        this.genre = new SimpleStringProperty(genre);
        this.systemRequirements = new SimpleStringProperty(systemRequirements);
    }

    public String getTitle() {
        return title.get();
    }

    public StringProperty titleProperty() {
        return title;
    }

    public int getYear() {
        return year.get();
    }

    public IntegerProperty yearProperty() {
        return year;
    }

    public String getGenre() {
        return genre.get();
    }

    public StringProperty genreProperty() {
        return genre;
    }

    public String getSystemRequirements() {
        return systemRequirements.get();
    }

    public StringProperty systemRequirementsProperty() {
        return systemRequirements;
    }
}
