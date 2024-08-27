package org.example.workshop3creditcruise.enums;

//Vehicle Age ENUM with only two predefined values.
public enum VehicleAge {
    NEW,
    USED
    ;

    // Overridden toString function so that enum values are returned in user readable format.
    @Override
    public String toString() {
        return switch (this.ordinal()) {
            case 0 -> "New";
            case 1 -> "Used";
            default -> null;
        };
    }
}
