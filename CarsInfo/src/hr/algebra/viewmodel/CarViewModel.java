/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.viewmodel;

import hr.algebra.model.Car;
import hr.algebra.model.CarMaker;
import hr.algebra.model.CarOwner;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author asim2
 */
public class CarViewModel {
    private final Car car;

    public CarViewModel(Car car) {
        if (car == null) {
            car = new Car(0, "", 0, 0, null);
        }
        this.car = car;
    }

    public Car getCar() {
        return car;
    }
    
    public StringProperty getCarNameProperty() {
        return new SimpleStringProperty(car.getCarName());
    }
    
    public IntegerProperty getCarPowerProperty() {
        return new SimpleIntegerProperty(car.getPowerInKw());
    }
    
    public IntegerProperty getCarYearProperty() {
        return new SimpleIntegerProperty(car.getYearOfMaking());
    }
    
    public ObjectProperty<byte[]> getPictureProperty() {
        return new SimpleObjectProperty<>(car.getPicture());
    }
    
    public ObjectProperty<CarOwner> getOwnerProperty() {
        return new SimpleObjectProperty<>(car.getCarOwnerID());
    }
    
    public ObjectProperty<CarMaker> getMakerProperty() {
        return new SimpleObjectProperty<>(car.getCarMakerID());
    }
}
