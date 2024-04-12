package DateUtils;

import java.sql.Date;
import java.util.Calendar;
import java.time.LocalTime;

public class DateUtils {

    public static Date addOneWeekToDate(Date date) {
        // Convert SQL Date to Calendar
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        calendar.add(Calendar.DAY_OF_MONTH, 7);

        return new Date(calendar.getTimeInMillis());
    }

    public static Date addThreeYearsToDate(Date date) {
        // Convert SQL Date to Calendar
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        // Add three years to the date
        calendar.add(Calendar.YEAR, 3);

        return new Date(calendar.getTimeInMillis());
    }

    public static boolean isFirstWithinSecond(String timeRange1, String timeRange2) {
        // Parse the time ranges into LocalTime objects
        String[] time1Parts = timeRange1.split("-");
        String[] time2Parts = timeRange2.split("-");

        String startTime1 = time1Parts[0];
        String endTime1 = time1Parts[1];

        String startTime2 = time2Parts[0];
        String endTime2 = time2Parts[1];

        // Parse start and end times of both ranges
        LocalTime start1 = LocalTime.parse(startTime1);
        LocalTime end1 = LocalTime.parse(endTime1);
        LocalTime start2 = LocalTime.parse(startTime2);
        LocalTime end2 = LocalTime.parse(endTime2);

        // Check if the first range is entirely within the second range
        return start1.isAfter(start2) && end1.isBefore(end2);
    }
}