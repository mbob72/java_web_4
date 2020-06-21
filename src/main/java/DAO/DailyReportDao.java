package DAO;

import model.DailyReport;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class DailyReportDao {

    private Session session;
    public static long lastId;

    public void clearAll() {
        session.createQuery("DELETE FROM DailyReport").executeUpdate();
    }

    public DailyReportDao(Session session) {
        this.session = session;
    }

    public List<DailyReport> getAllDailyReport() {
        List<DailyReport> dailyReports = session.createQuery("FROM DailyReport").list();
        return dailyReports;
    }

    public DailyReport getLastReport() {
        Criteria criteria = session.createCriteria(DailyReport.class);
        return (DailyReport) criteria
                .add(Restrictions.eq("id", lastId))
                .list().get(0);
    }

    public void addReport(DailyReport report) {
        session.save(report);
        lastId = report.getId();
    }
}
