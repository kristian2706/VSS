module com.vss.vss {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.vss.vss to javafx.fxml;
    exports com.vss;
}