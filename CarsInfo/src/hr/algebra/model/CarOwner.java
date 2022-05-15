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
@Table(name = "CarOwner")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CarOwner.findAll", query = "SELECT c FROM CarOwner c")
    , @NamedQuery(name = "CarOwner.findByIDCarOwner", query = "SELECT c FROM CarOwner c WHERE c.iDCarOwner = :iDCarOwner")
    , @NamedQuery(name = "CarOwner.findByFirstName", query = "SELECT c FROM CarOwner c WHERE c.firstName = :firstName")
    , @NamedQuery(name = "CarOwner.findByLastName", query = "SELECT c FROM CarOwner c WHERE c.lastName = :lastName")
    , @NamedQuery(name = "CarOwner.findByAge", query = "SELECT c FROM CarOwner c WHERE c.age = :age")
    , @NamedQuery(name = "CarOwner.findByEmail", query = "SELECT c FROM CarOwner c WHERE c.email = :email")})
public class CarOwner implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDCarOwner")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer iDCarOwner;
    @Basic(optional = false)
    @Column(name = "FirstName")
    private String firstName;
    @Basic(optional = false)
    @Column(name = "LastName")
    private String lastName;
    @Basic(optional = false)
    @Column(name = "Age")
    private int age;
    @Basic(optional = false)
    @Column(name = "Email")
    private String email;
    @Basic(optional = false)
    @Lob
    @Column(name = "Picture")
    private byte[] picture;
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "carOwnerID", orphanRemoval = true)
    private Collection<Car> carCollection;

    public CarOwner() {
    }
    
    public CarOwner(CarOwner data) {
        updateDetails(data);
    }

    public CarOwner(Integer iDCarOwner) {
        this.iDCarOwner = iDCarOwner;
    }

    public CarOwner(Integer iDCarOwner, String firstName, String lastName, int age, String email, byte[] picture) {
        this.iDCarOwner = iDCarOwner;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.picture = picture;
    }

    public Integer getIDCarOwner() {
        return iDCarOwner;
    }

    public void setIDCarOwner(Integer iDCarOwner) {
        this.iDCarOwner = iDCarOwner;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
        hash += (iDCarOwner != null ? iDCarOwner.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CarOwner)) {
            return false;
        }
        CarOwner other = (CarOwner) object;
        if ((this.iDCarOwner == null && other.iDCarOwner != null) || (this.iDCarOwner != null && !this.iDCarOwner.equals(other.iDCarOwner))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }

    public void updateDetails(CarOwner data) {
        this.firstName = data.firstName;
        this.lastName = data.lastName;
        this.age = data.age;
        this.email = data.email;
        this.picture = data.picture;
    }
    
}
