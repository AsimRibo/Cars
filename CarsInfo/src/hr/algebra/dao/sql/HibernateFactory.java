/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.dao.sql;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author asim2
 */
public class HibernateFactory {
    
    private static final String PERSISTENCE_UNIT = "CarsInfoPU";
    public static final String SELECT_ALL_CARS = "Car.findAll";
    public static final String SELECT_ALL_CAR_MAKERS = "CarMaker.findAll";
    public static final String SELECT_ALL_CAR_OWNERS = "CarOwner.findAll";
    
    public static final String SELECT_CARS_BY_OWNER_ID = "Car.findByCarOwnerID";
    public static final String PARAM_CAR_OWNER_ID = "CarOwnerID";
    
    public static final String SELECT_CARS_BY_MAKER_ID = "Car.findByCarMakerID";
    public static final String PARAM_CAR_MAKER_ID = "CarMakerID";
    
    
    
    
    
    
    private static final EntityManagerFactory EMF = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
    
    public static EntityManagerWrapper getEntityManager(){
        return new EntityManagerWrapper(EMF.createEntityManager());
    }
    
    public static void release(){
        EMF.close();
    }
    
}
