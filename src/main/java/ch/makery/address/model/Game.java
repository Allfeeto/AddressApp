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
    public Game() {
        this.title = new SimpleStringProperty("");
        this.year = new SimpleIntegerProperty(0);
        this.genre = new SimpleStringProperty("");
        this.systemRequirements = new SimpleStringProperty("");
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

    public void setTitle(String title) {
        this.title.set(title);
    }

    public void setYear(int year) {
        this.year.set(year);
    }

    public void setGenre(String genre) {
        this.genre.set(genre);
    }

    public void setSystemRequirements(String systemRequirements) {
        this.systemRequirements.set(systemRequirements);
    }

}

