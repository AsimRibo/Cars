/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.viewmodel;

import hr.algebra.model.CarMaker;
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
public class CarMakerViewModel {
    private final CarMaker carMaker;

    public CarMakerViewModel(CarMaker carMaker) {
        if (carMaker == null) {
            carMaker = new CarMaker(0, "", null);
        }
        this.carMaker = carMaker;
    }

    public CarMaker getCarMaker() {
        return carMaker;
    }
    
    public StringProperty getCarMakerNameProperty() {
        return new SimpleStringProperty(carMaker.getCarMakerName());
    }
    
    public IntegerProperty getCarMakerIdProperty() {
        return new SimpleIntegerProperty(carMaker.getIDCarMaker());
    }
    
    public ObjectProperty<byte[]> getPictureProperty() {
        return new SimpleObjectProperty<>(carMaker.getPicture());
    }

    @Override
    public String toString() {
        return getCarMakerNameProperty().get();
    }
    
    
    
}
