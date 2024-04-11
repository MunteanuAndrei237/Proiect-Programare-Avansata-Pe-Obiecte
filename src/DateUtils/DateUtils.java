package DateUtils;
import java.sql.Date;
import java.util.Calendar;

public class DateUtils {

    public static Date addOneWeekToDate(Date date) {
        // Convert SQL Date to Calendar
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        // Add one week (7 days) to the date
        calendar.add(Calendar.DAY_OF_MONTH, 7);

        // Convert Calendar back to Date
        Date newDate = new Date(calendar.getTimeInMillis());

        return newDate;
    }

    public static Date addThreeYearsToDate(Date date) {
        // Convert SQL Date to Calendar
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        // Add three years to the date
        calendar.add(Calendar.YEAR, 3);

        // Convert Calendar back to Date
        Date newDate = new Date(calendar.getTimeInMillis());

        return newDate;
    }
}