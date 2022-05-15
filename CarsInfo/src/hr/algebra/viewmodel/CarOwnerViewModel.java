/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.viewmodel;

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
public class CarOwnerViewModel {
    private final CarOwner carOwner;

    public CarOwnerViewModel(CarOwner carOwner) {
        if (carOwner == null) {
            carOwner = new CarOwner(0, "", "", 0, "", null);
        }
        this.carOwner = carOwner;
    }

    public CarOwner getCarOwner() {
        return carOwner;
    }
    
    public StringProperty getCarOwnerFirstNameProperty() {
        return new SimpleStringProperty(carOwner.getFirstName());
    }
    
    public StringProperty getCarOwnerLastNameProperty() {
        return new SimpleStringProperty(carOwner.getLastName());
    }
    
    public IntegerProperty getCarOwnerIdProperty() {
        return new SimpleIntegerProperty(carOwner.getIDCarOwner());
    }
    
    public IntegerProperty getCarOwnerAgeProperty() {
        return new SimpleIntegerProperty(carOwner.getAge());
    }
    
    public StringProperty getCarOwnerEmailProperty() {
        return new SimpleStringProperty(carOwner.getEmail());
    }
    
    public ObjectProperty<byte[]> getPictureProperty() {
        return new SimpleObjectProperty<>(carOwner.getPicture());
    }

    @Override
    public String toString() {
        return getCarOwnerFirstNameProperty().get() + " " + getCarOwnerLastNameProperty().get();
    }
    
    
}
