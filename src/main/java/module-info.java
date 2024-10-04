module ch.makery.address.addressapp {
    requires javafx.controls;
    requires javafx.fxml;


    opens ch.makery.address.addressapp to javafx.fxml;
    exports ch.makery.address.addressapp;
}