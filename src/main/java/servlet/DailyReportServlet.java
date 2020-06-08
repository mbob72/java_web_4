package servlet;

import DAO.DailyReportDao;
import com.google.gson.Gson;
import service.CarService;
import service.DailyReportService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DailyReportServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new Gson();
        if (req.getPathInfo().contains("all")) {
            resp.getWriter().write(gson.toJson(DailyReportService.getInstance().getAllDailyReports()));
            resp.setStatus(200);
        } else if (req.getPathInfo().contains("last")) {
            resp.getWriter().write(gson.toJson(DailyReportService.getInstance().getLastReport()));
            resp.setStatus(200);
        } else {
            resp.setStatus(403);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DailyReportService.getInstance().clearAll();
        CarService.resetDay();
        DailyReportDao.lastId = 0L;
        CarService.getInstance().clearAll();
        resp.setStatus(200);
    }
}
