module ch.makery.address {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.prefs;
    requires java.xml.bind;
    requires java.datatransfer;

    opens ch.makery.address.view to javafx.fxml;
    exports ch.makery.address;
    exports ch.makery.address.model;
}