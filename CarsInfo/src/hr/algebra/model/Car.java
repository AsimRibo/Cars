/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author asim2
 */
@Entity
@Table(name = "Car")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Car.findAll", query = "SELECT c FROM Car c")
    , @NamedQuery(name = "Car.findByIDCar", query = "SELECT c FROM Car c WHERE c.iDCar = :iDCar")
    , @NamedQuery(name = "Car.findByCarName", query = "SELECT c FROM Car c WHERE c.carName = :carName")
    , @NamedQuery(name = "Car.findByPowerInKw", query = "SELECT c FROM Car c WHERE c.powerInKw = :powerInKw")
    , @NamedQuery(name = "Car.findByCarMakerID", query = "SELECT c FROM Car c WHERE c.carMakerID.iDCarMaker = :CarMakerID") //manually added
    , @NamedQuery(name = "Car.findByCarOwnerID", query = "SELECT c FROM Car c WHERE c.carOwnerID.iDCarOwner = :CarOwnerID") //manually added
    , @NamedQuery(name = "Car.findByYearOfMaking", query = "SELECT c FROM Car c WHERE c.yearOfMaking = :yearOfMaking")})
public class Car implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDCar")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer iDCar;
    @Basic(optional = false)
    @Column(name = "CarName")
    private String carName;
    @Basic(optional = false)
    @Column(name = "PowerInKw")
    private int powerInKw;
    @Basic(optional = false)
    @Column(name = "YearOfMaking")
    private int yearOfMaking;
    @Basic(optional = false)
    @Lob
    @Column(name = "Picture")
    private byte[] picture;
    @JoinColumn(name = "CarMakerID", referencedColumnName = "IDCarMaker")
    @ManyToOne(optional = false)
    private CarMaker carMakerID; //should be renamed to more appropriate term like carMaker, but right now it would take a lot of refactoring
    @JoinColumn(name = "CarOwnerID", referencedColumnName = "IDCarOwner")
    @ManyToOne(optional = false)
    private CarOwner carOwnerID; //should be renamed to more appropriate term like carOwner, but right now it would take a lot of refactoring

    public Car() {
    }
    
    public Car(Car data) {
        updateDetails(data);
    }

    public Car(Integer iDCar) {
        this.iDCar = iDCar;
    }

    public Car(Integer iDCar, String carName, int powerInKw, int yearOfMaking, byte[] picture) {
        this.iDCar = iDCar;
        this.carName = carName;
        this.powerInKw = powerInKw;
        this.yearOfMaking = yearOfMaking;
        this.picture = picture;
    }

    public Integer getIDCar() {
        return iDCar;
    }

    public void setIDCar(Integer iDCar) {
        this.iDCar = iDCar;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public int getPowerInKw() {
        return powerInKw;
    }

    public void setPowerInKw(int powerInKw) {
        this.powerInKw = powerInKw;
    }

    public int getYearOfMaking() {
        return yearOfMaking;
    }

    public void setYearOfMaking(int yearOfMaking) {
        this.yearOfMaking = yearOfMaking;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public CarMaker getCarMakerID() {
        return carMakerID;
    }

    public void setCarMakerID(CarMaker carMakerID) {
        this.carMakerID = carMakerID;
    }

    public CarOwner getCarOwnerID() {
        return carOwnerID;
    }

    public void setCarOwnerID(CarOwner carOwnerID) {
        this.carOwnerID = carOwnerID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDCar != null ? iDCar.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Car)) {
            return false;
        }
        Car other = (Car) object;
        if ((this.iDCar == null && other.iDCar != null) || (this.iDCar != null && !this.iDCar.equals(other.iDCar))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "hr.algebra.model.Car[ iDCar=" + iDCar + " ]";
    }

    public void updateDetails(Car data) {
        this.carName = data.getCarName();
        this.powerInKw = data.getPowerInKw();
        this.yearOfMaking = data.getYearOfMaking();
        this.carMakerID = data.getCarMakerID();
        this.carOwnerID = data.getCarOwnerID();
        this.picture = data.getPicture();
    }
    
}
