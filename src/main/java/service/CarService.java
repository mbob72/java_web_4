package service;

import DAO.CarDao;
import DAO.DailyReportDao;
import model.Car;
import model.DailyReport;
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
        dayBurguns = new ArrayList<Car>();
    }

    public void clearAll() {
        new CarDao(sessionFactory.openSession()).clearAll();
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
        return new CarDao(sessionFactory.openSession()).save(car);
    }

    public boolean byCar(Car car) {
        Car baughtCar = new CarDao(sessionFactory.openSession()).byCar(car);
        if (baughtCar == null) {
            return false;
        }
        dayBurguns.add(baughtCar);
        return true;
    }

    public List<Car> getAllCars() {
        return new CarDao(sessionFactory.openSession()).getAllCars();
    }


}
