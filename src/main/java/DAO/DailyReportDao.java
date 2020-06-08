package DAO;

import model.Car;
import model.DailyReport;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class DailyReportDao {

    private Session session;
    public static long lastId;

    public void clearAll() {
        Transaction trx = session.beginTransaction();
        final List<?> instances = session.createCriteria(DailyReport.class).list();
        for (Object obj : instances) {
            session.delete(obj);
        }
        session.getTransaction().commit();
    }

    public DailyReportDao(Session session) {
        this.session = session;
    }

    public List<DailyReport> getAllDailyReport() {
        Transaction transaction = session.beginTransaction();
        List<DailyReport> dailyReports = session.createQuery("FROM DailyReport").list();
        transaction.commit();
        session.close();
        return dailyReports;
    }

    public DailyReport getLastReport() {
        Criteria criteria = session.createCriteria(DailyReport.class);
        return (DailyReport) criteria
                .add(Restrictions.eq("id", lastId))
                .list().get(0);
    }

    public void addReport(DailyReport report) {
        boolean res = true;
        Transaction trx = session.beginTransaction();
        session.save(report);
        lastId = report.getId();
        trx.commit();
        session.close();
    }
}
