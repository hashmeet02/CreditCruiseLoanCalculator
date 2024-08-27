package org.example.workshop3creditcruise.enums;

//Payment Frequency ENUM with only 3 predefined values.
public enum PaymentFrequency {
    WEEKLY,
    BI_WEEKLY,
    MONTHLY
    ;

    //Overridden toString function so the values are returned in user-readable format.
    @Override
    public String toString() {
        return switch (this.ordinal()) {
            case 0 -> "Weekly";
            case 1 -> "Bi-weekly";
            case 2 -> "Monthly";
            default -> null;
        };
    }
}
