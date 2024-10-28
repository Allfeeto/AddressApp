module ch.makery.address {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.prefs;
    requires java.xml.bind;
    requires java.datatransfer;

    opens ch.makery.address.view to javafx.fxml;
    opens ch.makery.address.model to java.xml.bind; // Добавили открытие для JAXB

    exports ch.makery.address;
    exports ch.makery.address.model;
}