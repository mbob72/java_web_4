package service;

import DAO.DailyReportDao;
import model.DailyReport;
import org.hibernate.SessionFactory;
import util.DBHelper;

import java.util.List;

public class DailyReportService {

    private static DailyReportService dailyReportService;

    private SessionFactory sessionFactory;

    public void clearAll() {
        try (DBHelper.CloseableSession session = new DBHelper.CloseableSession(sessionFactory.openSession())) {
            new DailyReportDao(session.getSession()).clearAll();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private DailyReportService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public static DailyReportService getInstance() {
        if (dailyReportService == null) {
            dailyReportService = new DailyReportService(DBHelper.getSessionFactory());
        }
        return dailyReportService;
    }

    public List<DailyReport> getAllDailyReports() {
        return new DailyReportDao(sessionFactory.openSession()).getAllDailyReport();
    }


    public DailyReport getLastReport() {
        return new DailyReportDao(sessionFactory.openSession()).getLastReport();
    }

    public void makeDayReport(DailyReport dailyReport) {
        new DailyReportDao(sessionFactory.openSession()).addReport(dailyReport);
    }
}
