package org.example.workshop3creditcruise.enums;

//Vehicle Type ENUM with only 3 predefined values.
public enum VehicleType{
    CAR,
    TRUCK,
    FAMILY_VAN;

    //Overridden toString function so enum values are returned in user-readable format.
    @Override
    public String toString() {
        return switch (this.ordinal()) {
            case 0 -> "Car";
            case 1 -> "Truck";
            case 2 -> "Family Van";
            default -> null;
        };
    }
}
