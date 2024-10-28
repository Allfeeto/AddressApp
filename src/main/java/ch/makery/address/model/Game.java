package ch.makery.address.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "game")
public class Game {

    private String title;
    private int year;
    private String genre;
    private String systemRequirements;

    public Game(String title, int year, String genre, String systemRequirements) {
        this.title = title;
        this.year = year;
        this.genre = genre;
        this.systemRequirements = systemRequirements;
    }

    public Game() {
        this.title = "";
        this.year = 0;
        this.genre = "";
        this.systemRequirements = "";
    }

    @XmlElement
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @XmlElement
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @XmlElement
    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @XmlElement
    public String getSystemRequirements() {
        return systemRequirements;
    }

    public void setSystemRequirements(String systemRequirements) {
        this.systemRequirements = systemRequirements;
    }
}
