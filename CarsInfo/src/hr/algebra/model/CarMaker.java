/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author asim2
 */
@Entity
@Table(name = "CarMaker")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CarMaker.findAll", query = "SELECT c FROM CarMaker c")
    , @NamedQuery(name = "CarMaker.findByIDCarMaker", query = "SELECT c FROM CarMaker c WHERE c.iDCarMaker = :iDCarMaker")
    , @NamedQuery(name = "CarMaker.findByCarMakerName", query = "SELECT c FROM CarMaker c WHERE c.carMakerName = :carMakerName")})
public class CarMaker implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDCarMaker")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer iDCarMaker;
    @Basic(optional = false)
    @Column(name = "CarMakerName")
    private String carMakerName;
    @Basic(optional = false)
    @Lob
    @Column(name = "Picture")
    private byte[] picture;
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "carMakerID", orphanRemoval = true) //was all
    private Collection<Car> carCollection;

    public CarMaker() {
    }
    
    public CarMaker(CarMaker data) {
        updateDetails(data);
    }

    public CarMaker(Integer iDCarMaker) {
        this.iDCarMaker = iDCarMaker;
    }

    public CarMaker(Integer iDCarMaker, String carMakerName, byte[] picture) {
        this.iDCarMaker = iDCarMaker;
        this.carMakerName = carMakerName;
        this.picture = picture;
    }

    public Integer getIDCarMaker() {
        return iDCarMaker;
    }

    public void setIDCarMaker(Integer iDCarMaker) {
        this.iDCarMaker = iDCarMaker;
    }

    public String getCarMakerName() {
        return carMakerName;
    }

    public void setCarMakerName(String carMakerName) {
        this.carMakerName = carMakerName;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    @XmlTransient
    public Collection<Car> getCarCollection() {
        return carCollection;
    }

    public void setCarCollection(Collection<Car> carCollection) {
        this.carCollection = carCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDCarMaker != null ? iDCarMaker.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CarMaker)) {
            return false;
        }
        CarMaker other = (CarMaker) object;
        if ((this.iDCarMaker == null && other.iDCarMaker != null) || (this.iDCarMaker != null && !this.iDCarMaker.equals(other.iDCarMaker))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return carMakerName;
    }

    public void updateDetails(CarMaker data) {
        this.carMakerName = data.carMakerName;
        this.picture = data.picture;
    }
    
}
