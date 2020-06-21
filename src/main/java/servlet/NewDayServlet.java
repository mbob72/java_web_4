package servlet;

import model.Car;
import model.DailyReport;
import service.CarService;
import service.DailyReportService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class NewDayServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            CarService carService = CarService.getInstance();
            long soldCars = carService.getDayBurguns().size();
            Optional<Long> sum = carService.getDayBurguns()
                    .stream()
                    .map(car -> car.getPrice())
                    .reduce((x, y)-> x + y);
            DailyReportService
                    .getInstance()
                    .makeDayReport(new DailyReport(sum.orElse(0L), soldCars));
            carService.resetDay();
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }
}
