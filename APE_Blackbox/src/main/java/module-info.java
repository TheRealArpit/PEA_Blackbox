module com.example.ape_blackbox {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.ape_blackbox to javafx.fxml;
    exports com.example.ape_blackbox;
}