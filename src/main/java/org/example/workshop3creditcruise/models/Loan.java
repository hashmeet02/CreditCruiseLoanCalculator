package org.example.workshop3creditcruise.models;

import javafx.beans.property.*;
import org.example.workshop3creditcruise.enums.PaymentFrequency;

//Loan class containing all the information related to a Loan.
public class Loan {
    //default values for downPayment, interestRate, loanPeriod and paymentFrequency.
    private static final double DEFAULT_DOWN_PAYMENT = 0.00;
    private static final double DEFAULT_INTEREST_RATE = 0.05;
    private static final int DEFAULT_LOAN_PERIOD = 12;
    private static final PaymentFrequency DEFAULT_PAYMENT_FREQUENCY = PaymentFrequency.MONTHLY;


    //private class variables. contains a Vehicle object, downPayment, interestRate, loanPeriod, paymentFrequency, installmentAmount as Properties.
    private ObjectProperty<Vehicle> vehicle = new SimpleObjectProperty<>();
    private DoubleProperty downPayment = new SimpleDoubleProperty();
    private DoubleProperty interestRate = new SimpleDoubleProperty();
    private IntegerProperty loanPeriod = new SimpleIntegerProperty();
    private ObjectProperty<PaymentFrequency> paymentFrequency = new SimpleObjectProperty<>();
    private DoubleProperty installmentAmount = new SimpleDoubleProperty();


    //1 Argument constructor that takes a vehicle object but sets a default values for all other properties.
    public Loan(Vehicle vehicle){
        this.vehicle.set(vehicle);
        this.downPayment.set(DEFAULT_DOWN_PAYMENT);
        this.interestRate.set(DEFAULT_INTEREST_RATE);
        this.loanPeriod.set(DEFAULT_LOAN_PERIOD);
        this.paymentFrequency.set(DEFAULT_PAYMENT_FREQUENCY);
    }


    //multiple argument constructor that takes values of all properties except installmentAmount.
    public Loan(Vehicle vehicle, double downPayment, int loanPeriod, PaymentFrequency paymentFrequency) {
        this.vehicle.set(vehicle);
        this.downPayment.set(downPayment);
        this.loanPeriod.set(loanPeriod);
        this.updateInterestRate(loanPeriod);
        this.paymentFrequency.set(paymentFrequency);
    }


    //Copy constructor to deep copy "other" Loan object into the current object.
    public Loan(Loan other) {
        this.vehicle = new SimpleObjectProperty<>();
        this.vehicle.set(new Vehicle(other.getVehicle()));
        this.downPayment = new SimpleDoubleProperty(other.getDownPayment());
        this.interestRate = new SimpleDoubleProperty(other.getInterestRate());
        this.loanPeriod = new SimpleIntegerProperty(other.getLoanPeriod());
        this.paymentFrequency = new SimpleObjectProperty<>(other.getPaymentFrequency());
        this.installmentAmount = new SimpleDoubleProperty(other.getInstallmentAmount());
    }


    //updateInterestRate changes the interestRate offered with respect to the Time Period. This follows the rule of
    //higher interest rate with longer time period.
    public void updateInterestRate(int loanPeriod) {
        if (loanPeriod <= 12) {
            interestRate.set(0.05);
        } else if (loanPeriod <= 24) {
            interestRate.set(0.06);
        } else if (loanPeriod <= 36) {
            interestRate.set(0.07);
        } else {
            interestRate.set(0.08);
        }
    }


    //calculateInstallment function calculates the installmentAmount. it has 3 different methods to calculate the
    //installment amount based on the paymentFrequency.
    public void calculateInstallment(){
        double principal= vehicle.get().getPrice() -downPayment.get();
        double accruedAmount=principal*(1+(interestRate.get()*loanPeriod.get()));
        switch (paymentFrequency.get()){
            case MONTHLY -> installmentAmount.set(accruedAmount/loanPeriod.get());
            case BI_WEEKLY ->installmentAmount.set(accruedAmount/((loanPeriod.get()/12)*26));
            case WEEKLY -> installmentAmount.set(accruedAmount/((loanPeriod.get()/12)*52));
        }
    }


    //Vehicle property getters and setters
    public ObjectProperty<Vehicle> vehicleProperty() {
        return vehicle;
    }
    public final Vehicle getVehicle() {
        return vehicle.get();
    }
    public final void setVehicle(Vehicle vehicle) {
        this.vehicle.set(vehicle);
    }


    //downPayment property getters and setters
    public DoubleProperty downPaymentProperty() {
        return downPayment;
    }
    public final double getDownPayment() {
        return downPayment.get();
    }
    public final void setDownPayment(double downPayment) {
        this.downPayment.set(downPayment);
    }


    //interestRate property getters no setter because it is calculated based on loanPeriod
    public DoubleProperty interestRateProperty() {
        return interestRate;
    }
    public final double getInterestRate() {
        return interestRate.get();
    }


    //loanPeriod property getters and setters.
    public IntegerProperty loanPeriodProperty() {
        return loanPeriod;
    }
    public final int getLoanPeriod() {
        return loanPeriod.get();
    }
    public final void setLoanPeriod(int loanPeriod) {
        this.loanPeriod.set(loanPeriod);
        updateInterestRate(loanPeriod); //to update the interestRate whenever loanPeriod is changed.
    }


    //paymentFrequency property getters and setters
    public ObjectProperty<PaymentFrequency> paymentFrequencyProperty() {
        return paymentFrequency;
    }
    public final PaymentFrequency getPaymentFrequency() {
        return paymentFrequency.get();
    }
    public final void setPaymentFrequency(PaymentFrequency paymentFrequency) {this.paymentFrequency.set(paymentFrequency);}


    //installmentAmount getters. not setter because it is a calculated property.
    public DoubleProperty installmentAmountProperty() {
        return installmentAmount;
    }
    public final double getInstallmentAmount() {
        calculateInstallment(); //calculates installmentAmount before returning the value.
        return installmentAmount.get();
    }

}
