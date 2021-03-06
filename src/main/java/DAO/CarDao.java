package DAO;

import model.Car;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.TransactionException;
import org.hibernate.criterion.Restrictions;

import java.sql.SQLException;
import java.util.List;

public class CarDao {

    private Session session;

    public CarDao(Session session) {
        this.session = session;
    }

    public void clearAll() {
        session.createQuery("DELETE FROM Car").executeUpdate();
    }

    public boolean save(Car newCar) {
        boolean res = true;
        Transaction trx = session.beginTransaction();
        try {
            List<Car> allBrendCars = readByBrend(newCar.getBrand());
            if (allBrendCars.size() >= 10) {
                res = false;
            } else {
                session.save(newCar);
            }
            trx.commit();
        } catch (TransactionException e) {
            trx.rollback();
            return false;
        }
        return res;
    }

    public List<Car> readByBrend(String brand) {
        Criteria criteria = session.createCriteria(Car.class);
        return (List<Car>) criteria
                .add(Restrictions.eq("brand", brand))
                .list();
    }

    public Car byCar(Car car) {
        Criteria criteria = session.createCriteria(Car.class);
        Transaction trx = session.beginTransaction();
        Car baughtCar = null;
        try {
            baughtCar = (Car) criteria
                    .add(Restrictions.and(Restrictions.eq("brand", car.getBrand()),
                            Restrictions.eq("model", car.getModel()),
                            Restrictions.eq("licensePlate", car.getLicensePlate())))
                    .list()
                    .get(0);
            if (baughtCar != null) {
                session.delete(baughtCar);
            }
            trx.commit();
        } catch (TransactionException e) {
            trx.rollback();
            return null;
        }
        return baughtCar;
    }

    public List<Car> getAllCars() {
        Criteria criteria = session.createCriteria(Car.class);
        return (List<Car>) criteria
                .list();
    }

}
