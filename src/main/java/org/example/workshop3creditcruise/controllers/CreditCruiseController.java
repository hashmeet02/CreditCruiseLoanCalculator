package org.example.workshop3creditcruise.controllers;

import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.util.converter.NumberStringConverter;
import org.example.workshop3creditcruise.enums.*;
import org.example.workshop3creditcruise.models.*;
import org.example.workshop3creditcruise.views.LoanView;

import java.util.ArrayList;
import java.util.List;

public class CreditCruiseController {

    //declare Loan, LoanView, savedLoans list member variables to be used by the application.
    private Loan loan;
    private LoanView loanView;
    private List<Loan> savedLoans=new ArrayList<>();

    //declare the necessary ui-components to be used by the user.
    @FXML
    private HBox mainPanel;
    @FXML
    private ComboBox<VehicleType> vehicleType;
    @FXML
    private ComboBox<VehicleAge>  vehicleAge;
    @FXML
    private ComboBox<PaymentFrequency> loanPayFreq ;
    @FXML
    private TextField loanDownPay, loanInterestRate, vehiclePrice;
    @FXML
    private Slider loanTimePeriod;
    @FXML
    private Label loanDetails, infoBox;
    @FXML
    private Button calcBtn, clearBtn, showRateBtn, saveRateBtn;

    //loan and loan view setters
    public void setLoan(Loan loan) {
        this.loan=loan;
    }
    public void setLoanView(LoanView loanView){this.loanView=loanView;}

    //initialize function calls the functions necessary to set up the application with a clean slate.
    public void initialize(){
        setupLoanAndVehicle();
        setupActions();
    }

    //sets up the loan and loanView objects to be used by the application. calls the binding function and sets up the combo boxes.
    private void setupLoanAndVehicle(){
        Vehicle vehicle= new Vehicle();
        this.loan=new Loan(vehicle);
        this.loanView= new LoanView();

        bindUIComponents();

        vehicleType.setItems(FXCollections.observableArrayList(VehicleType.values()));
        vehicleAge.setItems(FXCollections.observableArrayList(VehicleAge.values()));
        loanPayFreq.setItems(FXCollections.observableArrayList(PaymentFrequency.values()));
    }

    //sets up the new bidirectional bindings for the ui components by removing the old ones and adding the new ones.
    private void bindUIComponents(){
        removeListeners();

        vehicleType.valueProperty().bindBidirectional(loan.getVehicle().typeProperty());
        vehicleAge.valueProperty().bindBidirectional(loan.getVehicle().ageProperty());
        vehiclePrice.textProperty().bindBidirectional(loan.getVehicle().priceProperty(), new NumberStringConverter());
        loanInterestRate.textProperty().bind(Bindings.createStringBinding(() ->
                        String.format("%.2f%%", loan.interestRateProperty().get() * 100),
                loan.interestRateProperty()));
        loanDownPay.textProperty().bindBidirectional(loan.downPaymentProperty(), new NumberStringConverter());
        loanTimePeriod.valueProperty().bindBidirectional(loan.loanPeriodProperty());
        loanPayFreq.valueProperty().bindBidirectional(loan.paymentFrequencyProperty());

        addListeners();
    }

    // removes all existing listeners bound with properties.
    private void removeListeners(){
        vehicleType.valueProperty().removeListener(vehicleTypeListener);
        vehicleAge.valueProperty().removeListener(vehicleAgeListener);
        vehiclePrice.textProperty().removeListener(vehiclePriceListener);
        loanDownPay.textProperty().removeListener(loanDownPayListener);
        loanTimePeriod.valueProperty().removeListener(loanTimePeriodListener);
        loanPayFreq.valueProperty().removeListener(loanPayFreqListener);
    }

    // adds the new listeners.
    private void addListeners(){
        vehicleType.valueProperty().addListener(vehicleTypeListener);
        vehicleAge.valueProperty().addListener(vehicleAgeListener);
        vehiclePrice.textProperty().addListener(vehiclePriceListener);
        loanDownPay.textProperty().addListener(loanDownPayListener);
        loanTimePeriod.valueProperty().addListener(loanTimePeriodListener);
        loanPayFreq.valueProperty().addListener(loanPayFreqListener);
    }

    // sets up the functions to be called when any of the buttons are pressed.
    private void setupActions(){
        calcBtn.setOnAction(event -> calculateDetails());
        clearBtn.setOnAction(event-> clearFrom());
        saveRateBtn.setOnAction(event -> saveRate());
        showRateBtn.setOnAction(event -> showSavedRates());
    }

    //function called when the clear button is pressed. Sets everything to default.
    private void clearFrom() {
        vehicleType.getSelectionModel().clearSelection();
        vehicleAge.getSelectionModel().clearSelection();
        loanPayFreq.getSelectionModel().clearSelection();
        setupLoanAndVehicle();
        infoBox.setText("Form Cleared");
        loanDetails.setText("...");
    }

    //change listener for vehicleType combobox.
    private final ChangeListener<VehicleType> vehicleTypeListener = (obs, oldVal, newVal)->{
        if (newVal!=null){
            loan.getVehicle().setType(newVal);
            infoBox.setText("Vehicle Type selected: " + loan.getVehicle().getType());
        }
    };

    //change listener for vehicleAge combobox.
    private final ChangeListener<VehicleAge> vehicleAgeListener = (obs, oldVal, newVal)->{
        if (newVal!=null){
            loan.getVehicle().setAge(newVal);
            infoBox.setText("Vehicle Age selected: " + loan.getVehicle().getAge());
        }
    };

    //change listener for vehiclePrice textBox.
    private final ChangeListener<String> vehiclePriceListener= (obs, oldVal, newVal)->{
        newVal=newVal.trim();
        if(!newVal.isEmpty()){  //only take actions if text box has some content
            try{
                double price= Double.parseDouble(newVal);
                loan.getVehicle().setPrice(price);
                infoBox.setText("Car price set to: $" + loan.getVehicle().getPrice());   //tell user that price has been changed.
            }catch(NumberFormatException e){
                System.err.println("Error: Vehicle price must be a non-negative number.");
                vehiclePrice.setText(oldVal);
                infoBox.setText("Error: Vehicle price must be a non-negative number."); //tell user when text is not a number.
            }
        }
    };


    //change listener for loanDownPayment.
    private final ChangeListener<String> loanDownPayListener= (obs, oldVal, newVal)->{
        newVal=newVal.trim();
        if(!newVal.isEmpty()){
            try{
                double downPay= Double.parseDouble(newVal);
                double vehiclePriceValue=Double.parseDouble(vehiclePrice.getText().trim());
                if(downPay>vehiclePriceValue){  // down payment can't be greater than the price of the car.
                    loanDownPay.setText(oldVal);
                    infoBox.setText("Invalid input: Down Payment can't be more than the vehicle price.");
                }else{
                    loan.setDownPayment(downPay);
                    infoBox.setText("Down Payment set to: $" + loan.getDownPayment());
                }
            }catch(NumberFormatException e){    //down payment must be a valid number.
                System.err.println("Invalid input: Down Payment must be a non-negative number.");
                loanDownPay.setText(oldVal);
                infoBox.setText("Invalid input: Down Payment must be a non-negative number.");
            }
        }
    };

    //change listener fo timePeriod slider.
    private final ChangeListener<Number> loanTimePeriodListener = (obs, oldVal, newVal)->{
        int loanPeriod= newVal.intValue();
        loan.setLoanPeriod(loanPeriod);
        infoBox.setText("Loan payment period set to: "+ loan.getLoanPeriod()+ " months");
    };

    //change listener for loanPayFreq.
    private final ChangeListener<PaymentFrequency> loanPayFreqListener = (obs, oldVal, newVal)->{
        if (newVal!=null){
            loan.setPaymentFrequency(newVal);
            infoBox.setText("Payment Frequency selected: "+loan.getPaymentFrequency());
        }
    };

    //function called when calculate button is pressed. Display the loan details to the user.
    private void calculateDetails() {
        double principal= loan.getVehicle().getPrice()-loan.getDownPayment();
        String result=loanView.displayLoanInfo(loan.getVehicle().getType().toString(), loan.getVehicle().getAge().toString(),principal, loan.getPaymentFrequency().toString(), loan.getInstallmentAmount(), loan.getLoanPeriod(), loan.getInterestRate());
        loanDetails.setText(result);
        infoBox.setText("Loan Details have been successfully calculated.");
    }

    //function called when save rate button is pressed it also calculates all the info and then saves the data into list.
    private void saveRate(){
        calculateDetails();
        Loan loanCopy= new Loan(this.loan); // copying to prevent storing of reference of single object multiple times.
        savedLoans.add(loanCopy);
        infoBox.setText("Rate Saved!");
    }

    //function called when show rates button is called
    private void showSavedRates(){
        //setting up the new dialog box to be displayed.
        Dialog<ButtonType> dialog= new Dialog<>();
        dialog.initOwner(mainPanel.getScene().getWindow());
        dialog.setTitle("Saved Rates");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK);

        //setting and populating the list view.
        ListView<String> listView =new ListView<>();
        ObservableList<String> rates= FXCollections.observableArrayList();
        int ord = 1;
        for (Loan loan1 : savedLoans) {
            rates.add(ord++ + ": " + String.format("%.2f%%", loan1.getInterestRate()*100));
        }
        listView.setItems(rates);

        //setting up the text area that will display the information of the selected Interest Rate.
        TextArea loanDetailsArea=new TextArea();
        loanDetailsArea.setText("NO SAVED RATES TO SHOW YET!");
        loanDetailsArea.setEditable(false);
        loanDetailsArea.setWrapText(true);

        listView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal)->{
            int index=listView.getSelectionModel().getSelectedIndex();
            if(index>=0){
                Loan selectedLoan= savedLoans.get(index);
                double principal= selectedLoan.getVehicle().getPrice()-selectedLoan.getDownPayment();
                String loanDetails = new LoanView().displayLoanInfo(
                        selectedLoan.getVehicle().getType().toString(),
                        selectedLoan.getVehicle().getAge().toString(),
                        principal,
                        selectedLoan.getPaymentFrequency().toString(),
                        selectedLoan.getInstallmentAmount(),
                        selectedLoan.getLoanPeriod(),
                        selectedLoan.getInterestRate()
                );
                loanDetailsArea.setText(loanDetails);

            }
        });
        if(!rates.isEmpty()){
            listView.getSelectionModel().select(0);
        }

        //Populating and formatting the dialog box.
        HBox layout=new HBox(5);
        layout.getChildren().addAll(listView, loanDetailsArea);
        layout.setPrefSize(400,300);
        layout.setStyle("-fx-background-color:  rgba(0, 77, 70, 1)");
        dialog.getDialogPane().setContent(layout);
        dialog.showAndWait();
    }

}
