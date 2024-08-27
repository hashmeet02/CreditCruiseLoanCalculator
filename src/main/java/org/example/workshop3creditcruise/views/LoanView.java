package org.example.workshop3creditcruise.views;

import java.text.NumberFormat;
import java.util.Locale;

//LoanView class that defines how the information is formatted and displayed to the user.
public class LoanView {
    public String displayLoanInfo(String vehicleType, String vehicleAge, double principal, String paymentFrequency, double installmentAmount, int loanPeriod, double interestRate){
        // define currency and percent formats.
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.getDefault());
        NumberFormat percentFormat= NumberFormat.getPercentInstance(Locale.getDefault());
        percentFormat.setMinimumFractionDigits(1);

        //use the currency and percent format to format corresponding variables.
        String formattedPrincipal= currencyFormat.format(principal);
        String formattedInstallmentAmount= currencyFormat.format(installmentAmount);
        String formattedInterestRate= percentFormat.format(interestRate);

        //return the formatted string.
        return "For your " + vehicleAge
                + " " + vehicleType
                + " you are applying for a loan of " + formattedPrincipal
                + " and  will be paying a " + paymentFrequency
                + " installment of " +  formattedInstallmentAmount
                + " for the next " + loanPeriod
                + " months at a fixed interest rate of " + formattedInterestRate
                + ".";
    }
}
