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
        try (DBHelper.CloseableSession session = new DBHelper.CloseableSession(sessionFactory.openSession())) {
            return new DailyReportDao(session.getSession()).getAllDailyReport();
        } catch (Exception e) {
            throw e;
        }
    }


    public DailyReport getLastReport() {
        try (DBHelper.CloseableSession session = new DBHelper.CloseableSession(sessionFactory.openSession())) {
            return new DailyReportDao(session.getSession()).getLastReport();
        } catch (Exception e) {
            throw e;
        }
    }

    public void makeDayReport(DailyReport dailyReport) {
        try (DBHelper.CloseableSession session = new DBHelper.CloseableSession(sessionFactory.openSession())) {
            new DailyReportDao(session.getSession()).addReport(dailyReport);
        } catch(Exception e) {
            throw e;
        }
    }
}
