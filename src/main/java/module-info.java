module org.example.workshop3creditcruise {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.workshop3creditcruise to javafx.fxml;
    exports org.example.workshop3creditcruise;
    exports org.example.workshop3creditcruise.controllers;
    opens org.example.workshop3creditcruise.controllers to javafx.fxml;
}