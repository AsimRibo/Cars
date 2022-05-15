/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.dao.sql;

import hr.algebra.dao.Repository;
import hr.algebra.model.Car;
import hr.algebra.model.CarMaker;
import hr.algebra.model.CarOwner;
import java.util.List;
import javax.persistence.EntityManager;

public class HibernateRepository<T> implements Repository {

    @Override
    public int addCarMaker(CarMaker carMakerData) throws Exception {
        try (EntityManagerWrapper wrapper = HibernateFactory.getEntityManager()) {
            EntityManager em = wrapper.getEntityManager();
            em.getTransaction().begin();

            CarMaker carMaker = new CarMaker(carMakerData);
            em.persist(carMaker);

            em.getTransaction().commit();

            return carMaker.getIDCarMaker();
        }
    }

    @Override
    public void updateCarMaker(CarMaker carMaker) throws Exception {
        try (EntityManagerWrapper wrapper = HibernateFactory.getEntityManager()) {
            EntityManager em = wrapper.getEntityManager();
            em.getTransaction().begin();

            em.find(CarMaker.class, carMaker.getIDCarMaker()).updateDetails(carMaker);

            em.getTransaction().commit();

        }
    }

    @Override
    public CarMaker getCarMaker(int idCarMaker) throws Exception {
        try (EntityManagerWrapper wrapper = HibernateFactory.getEntityManager()) {
            EntityManager em = wrapper.getEntityManager();

            return em.find(CarMaker.class, idCarMaker);
        }
    }

    @Override
    public void deleteCarMaker(CarMaker carMaker) throws Exception {
        try (EntityManagerWrapper wrapper = HibernateFactory.getEntityManager()) {
            EntityManager em = wrapper.getEntityManager();
            em.getTransaction().begin();

            em.remove(em.contains(carMaker) ? carMaker : em.merge(carMaker));

            em.getTransaction().commit();

        }
    }

    @Override
    public List<CarMaker> getCarMakers() throws Exception {
        try (EntityManagerWrapper wrapper = HibernateFactory.getEntityManager()) {
            EntityManager em = wrapper.getEntityManager();

            return em.createNamedQuery(HibernateFactory.SELECT_ALL_CAR_MAKERS).getResultList();
        }
    }

    @Override
    public boolean isSafeToDeleteMaker(int idMaker) throws Exception {
        try (EntityManagerWrapper wrapper = HibernateFactory.getEntityManager()) {
            EntityManager em = wrapper.getEntityManager();

            List result = em.createNamedQuery(HibernateFactory.SELECT_CARS_BY_MAKER_ID).setParameter(HibernateFactory.PARAM_CAR_MAKER_ID, idMaker).getResultList();
            return result.isEmpty();
        }
    }

    @Override
    public void release() throws Exception {
        HibernateFactory.release();
    }

    @Override
    public int addCarOwner(CarOwner carOwnerData) throws Exception {
        try (EntityManagerWrapper wrapper = HibernateFactory.getEntityManager()) {
            EntityManager em = wrapper.getEntityManager();
            em.getTransaction().begin();

            CarOwner carOwner = new CarOwner(carOwnerData);
            em.persist(carOwner);

            em.getTransaction().commit();

            return carOwner.getIDCarOwner();
        }
    }

    @Override
    public void updateCarOwner(CarOwner carOwner) throws Exception {
        try (EntityManagerWrapper wrapper = HibernateFactory.getEntityManager()) {
            EntityManager em = wrapper.getEntityManager();
            em.getTransaction().begin();

            em.find(CarOwner.class, carOwner.getIDCarOwner()).updateDetails(carOwner);

            em.getTransaction().commit();

        }
    }

    @Override
    public CarOwner getCarOwner(int idCarOwner) throws Exception {
        try (EntityManagerWrapper wrapper = HibernateFactory.getEntityManager()) {
            EntityManager em = wrapper.getEntityManager();

            return em.find(CarOwner.class, idCarOwner);
        }
    }

    @Override
    public void deleteCarOwner(CarOwner carOwner) throws Exception {
        try (EntityManagerWrapper wrapper = HibernateFactory.getEntityManager()) {
            EntityManager em = wrapper.getEntityManager();
            em.getTransaction().begin();

            em.remove(em.contains(carOwner) ? carOwner : em.merge(carOwner));

            em.getTransaction().commit();

        }
    }

    @Override
    public List<CarOwner> getCarOwners() throws Exception {
        try (EntityManagerWrapper wrapper = HibernateFactory.getEntityManager()) {
            EntityManager em = wrapper.getEntityManager();

            return em.createNamedQuery(HibernateFactory.SELECT_ALL_CAR_OWNERS).getResultList();
        }
    }

    @Override
    public boolean isSafeToDeleteOwner(int idOwner) throws Exception {
        try (EntityManagerWrapper wrapper = HibernateFactory.getEntityManager()) {
            EntityManager em = wrapper.getEntityManager();

            List result = em.createNamedQuery(HibernateFactory.SELECT_CARS_BY_OWNER_ID).setParameter(HibernateFactory.PARAM_CAR_OWNER_ID, idOwner).getResultList();
            return result.isEmpty();
        }
    }

    @Override
    public int addCar(Car carData) throws Exception {
        try (EntityManagerWrapper wrapper = HibernateFactory.getEntityManager()) {
            EntityManager em = wrapper.getEntityManager();
            em.getTransaction().begin();

            Car car = new Car(carData);
            em.persist(car);

            em.getTransaction().commit();

            return car.getIDCar();
        }
    }

    @Override
    public void updateCar(Car car) throws Exception {
        try (EntityManagerWrapper wrapper = HibernateFactory.getEntityManager()) {
            EntityManager em = wrapper.getEntityManager();
            em.getTransaction().begin();

            em.find(Car.class, car.getIDCar()).updateDetails(car);

            em.getTransaction().commit();

        }
    }

    @Override
    public Car getCar(int idCar) throws Exception {
        try (EntityManagerWrapper wrapper = HibernateFactory.getEntityManager()) {
            EntityManager em = wrapper.getEntityManager();

            return em.find(Car.class, idCar);
        }
    }

    @Override
    public void deleteCar(Car car) throws Exception {
        try (EntityManagerWrapper wrapper = HibernateFactory.getEntityManager()) {
            EntityManager em = wrapper.getEntityManager();
            em.getTransaction().begin();

            em.remove(em.contains(car) ? car : em.merge(car));

            em.getTransaction().commit();

        }
    }

    @Override
    public List<Car> getCars() throws Exception {
        try (EntityManagerWrapper wrapper = HibernateFactory.getEntityManager()) {
            EntityManager em = wrapper.getEntityManager();

            return em.createNamedQuery(HibernateFactory.SELECT_ALL_CARS).getResultList();
        }
    }
}
