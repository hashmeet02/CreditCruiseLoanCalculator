package org.example.workshop3creditcruise.models;

import javafx.beans.property.*;
import org.example.workshop3creditcruise.enums.VehicleAge;
import org.example.workshop3creditcruise.enums.VehicleType;

//Vehicle class to store all information about the Vehicle.
public class Vehicle {
    //default constant values for vehicle type, age and price.
    private static final VehicleType DEFAULT_VEHICLE_TYPE = VehicleType.CAR;
    private static final VehicleAge DEFAULT_VEHICLE_AGE = VehicleAge.NEW;
    private static final double DEFAULT_VEHICLE_PRICE = 0.0;

    //class member variables set as properties.
    private ObjectProperty<VehicleType> type = new SimpleObjectProperty<>();
    private ObjectProperty<VehicleAge> age = new SimpleObjectProperty<>();
    private DoubleProperty price = new SimpleDoubleProperty();

    //no argument constructor for Vehicle.
    public Vehicle(){
        this.type.set(DEFAULT_VEHICLE_TYPE);
        this.age.set(DEFAULT_VEHICLE_AGE);
        this.price.set(DEFAULT_VEHICLE_PRICE);
    }

    // all argument constructor for Vehicle.
    public Vehicle(VehicleType type, VehicleAge age, double price){
        this.type.set(type);
        this.age.set(age);
        this.price.set(price);
    }

    //copy constructor for Vehicle object.
    public Vehicle(Vehicle other) {
        this.type = new SimpleObjectProperty<>(other.getType());
        this.age = new SimpleObjectProperty<>(other.getAge());
        this.price = new SimpleDoubleProperty(other.getPrice());
    }

    //type property getters and setters.
    public ObjectProperty<VehicleType> typeProperty() {
        return this.type;
    }
    public VehicleType getType() {return type.get();}
    public void setType(VehicleType type) {this.type.set(type);}


    //age property getters and setters
    public ObjectProperty<VehicleAge> ageProperty() {return this.age;}
    public VehicleAge getAge() {
        return age.get();
    }
    public void setAge(VehicleAge age) {
        this.age.set(age);
    }


    //price property getters and setters.
    public DoubleProperty priceProperty() {return this.price;}
    public double getPrice() {
        return price.get();
    }
    public void setPrice(double price) {
        this.price.set(price);
    }
}
