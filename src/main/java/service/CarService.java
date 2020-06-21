package service;

import DAO.CarDao;
import model.Car;
import org.hibernate.SessionFactory;
import util.DBHelper;


import java.util.ArrayList;
import java.util.List;

public class CarService {

    private static CarService carService;

    private SessionFactory sessionFactory;

    private static List<Car> dayBurguns = new ArrayList<>();
    public static List<Car> getDayBurguns() {
        return dayBurguns;
    }
    public static void resetDay() {
        dayBurguns = new ArrayList<>();
    }

    public void clearAll() {
        try (DBHelper.CloseableSession session = new DBHelper.CloseableSession(sessionFactory.openSession())) {
            new CarDao(session.getSession()).clearAll();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private CarService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public static CarService getInstance() {
        if (carService == null) {
            carService = new CarService(DBHelper.getSessionFactory());
        }
        return carService;
    }

    public boolean addCar(Car car) {
        try (DBHelper.CloseableSession session = new DBHelper.CloseableSession( sessionFactory.openSession())) {
            return new CarDao(session.getSession()).save(car);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean byCar(Car car) {
        try (DBHelper.CloseableSession session = new DBHelper.CloseableSession( sessionFactory.openSession())) {
            Car baughtCar = new CarDao(session.getSession()).byCar(car);
            if (baughtCar == null) {
                return false;
            }
            dayBurguns.add(baughtCar);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public List<Car> getAllCars() {
        try (DBHelper.CloseableSession session = new DBHelper.CloseableSession( sessionFactory.openSession())) {
            return new CarDao(session.getSession()).getAllCars();
        } catch (Exception e) {
            return null;
        }
    }
}


