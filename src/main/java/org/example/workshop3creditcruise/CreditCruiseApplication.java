/**********************************************
 Workshop 3
 Course:APD-545 - semester-5
 Last Name:Saini
 First Name:Hashmeet Singh
 ID:153070214
 Section:NAA
 This assignment represents my own work in accordance with Seneca Academic Policy.
 Hashmeet Singh Saini
 Date: March 1st 2024
 **********************************************/

package org.example.workshop3creditcruise;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.workshop3creditcruise.controllers.CreditCruiseController;
import org.example.workshop3creditcruise.models.Loan;
import org.example.workshop3creditcruise.models.Vehicle;

import java.io.IOException;

public class CreditCruiseApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CreditCruiseApplication.class.getResource("creditCruise.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 436,728 );
        stage.setTitle("Credit Cruise");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }


    public static void main(String[] args) { launch();}
}