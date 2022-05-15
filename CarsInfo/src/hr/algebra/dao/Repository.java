/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.dao;

import hr.algebra.model.Car;
import hr.algebra.model.CarMaker;
import hr.algebra.model.CarOwner;
import java.util.List;

/**
 *
 * @author asim2
 */
public interface Repository {
    
    int addCarMaker(CarMaker carMakerData) throws Exception;
    void updateCarMaker(CarMaker carMaker) throws Exception;
    CarMaker getCarMaker(int idCarMaker) throws Exception;
    void deleteCarMaker(CarMaker carMaker) throws Exception;
    List<CarMaker> getCarMakers() throws Exception;
    boolean isSafeToDeleteMaker(int idMaker) throws Exception;
    
    int addCarOwner(CarOwner carOwnerData) throws Exception;
    void updateCarOwner(CarOwner carOwner) throws Exception;
    CarOwner getCarOwner(int idCarOwner) throws Exception;
    void deleteCarOwner(CarOwner carOwner) throws Exception;
    List<CarOwner> getCarOwners() throws Exception;
    boolean isSafeToDeleteOwner(int idOwner) throws Exception;
    
    int addCar(Car carData) throws Exception;
    void updateCar(Car car) throws Exception;
    Car getCar(int idCar) throws Exception;
    void deleteCar(Car car) throws Exception;
    List<Car> getCars() throws Exception;
    
    
    
    default void release() throws Exception{};
    
}
