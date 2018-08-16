package app.com.esenatenigeria.utils;


import android.text.TextUtils;
import android.util.Patterns;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Consts {
    public static final String FILE_NAME = "file_name";
    // bottom bar
    public static final int FRAG_NULL = 0;
    public static final int FRAG_DASHBOARD = 1;
    public static final int FRAG_BILLS = 2;
    public static final int FRAG_COMMITTEE = 3;
    public static final int FRAG_ACTS = 4;
    public static final int FRAG_LIVE = 5;

    // navigation bar
    public static final int FRAG_SENATE_LEADERSHIP = 11;
    public static final int FRAG_RECENT_ACTIVITIES = 12;
    public static final int FRAG_ABOUT_SENATE = 13;
    public static final int FRAG_SENATOR_DETAILS = 14;
    public static final int FRAG_CONSTITUTIONAL_ROLES = 15;
    public static final int FRAG_COMPOSITIONS = 16;
    public static final int FRAG_PROFILE = 21;

    public static final int PRESIDENT = 0;
    public static final int DEPUTY = 1;
    public static final String BILLS = "BILLS";
    public static final String ACTS = "ACTS";

    public static final String DETAIL_ID = "detail_id";
    public static final String PDF_URL = "pdf_url";
    public static final String ACT_ID = "act_id";
    public static final String BILL_ID = "bill_id";
    public static final int ABOUT_INFO = 1;
    public static final int CONSTITUTIONAL_ROLES_INFO = 2;
    public static final int COMPOSITION_INFO = 3;
    public static final String RECENT_DATA = "recent_date";

    public static final String YOUTUBE_KEY = "AIzaSyBNrx8_dcWgwViHed0CU8G0erB_rAhHw3U";
    public static final String YOUTUBE_CHANNEL_ID = "UCDGwg0flJkk3LNjd_6lQUjA";

    public static boolean NO_INTERNET = false;
    public static int NONE_SELECTED_FILTER = 0;

    public static SimpleDateFormat mFilterShowDob = new SimpleDateFormat("yyyy/MM/dd", Locale.US);
    public static String YOUTUBE_URL = "https://www.googleapis.com/youtube/v3/search?part=snippet&channelId=" + Consts.YOUTUBE_CHANNEL_ID + "&maxResults=" + "25" + "&key=" + Consts.YOUTUBE_KEY;

    public static long getUtcTime(long time) {
        long utcTime = 0;
        String dateValue = "";
        try {
            SimpleDateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            utcFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            Calendar calUtc = Calendar.getInstance();

            SimpleDateFormat localFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            localFormat.setTimeZone(TimeZone.getDefault());
            Calendar calLocal = Calendar.getInstance();

            calLocal.setTimeInMillis(time);
            dateValue = localFormat.format(calLocal.getTime());
            Date value = localFormat.parse(dateValue);

            dateValue = utcFormat.format(value);
            calUtc.setTime(utcFormat.parse(dateValue));
            utcTime = calUtc.getTimeInMillis();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return utcTime;
    }

    public static long getLocalTime(long time) {
        long localTime = 0;
        String dateValue = "";
        try {
            SimpleDateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            utcFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            Calendar calUtc = Calendar.getInstance();

            SimpleDateFormat localFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            localFormat.setTimeZone(TimeZone.getDefault());
            Calendar calLocal = Calendar.getInstance();

            calUtc.setTimeInMillis(time);
            dateValue = utcFormat.format(calUtc.getTime());
            Date value = utcFormat.parse(dateValue);

            dateValue = localFormat.format(value);
            Date localvalue = localFormat.parse(dateValue);
            localTime = localvalue.getTime();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return localTime;
    }

    public static String convertDate(String endDate) throws ParseException {
        try {
            SimpleDateFormat displayFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.US);
            SimpleDateFormat parseFormat = new SimpleDateFormat("yyyy-mm-ddt00:00:00.000z", Locale.US);
            Date date = parseFormat.parse(endDate);
            System.out.println(parseFormat.format(date) + " = " + displayFormat.format(date));

            return displayFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String timeFormatChange(String dateString) {
// String input = "2011/02/14 00:00:00.000 -0800";
        DateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        utcFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        Date date = null;
        try {
            date = utcFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
//        DateFormat pstFormat = new SimpleDateFormat("dd MMM,yyyy");
        DateFormat pstFormat = new SimpleDateFormat("yyyy-MM-dd");
        pstFormat.setTimeZone(TimeZone.getTimeZone("PST"));

        return pstFormat.format(date);
    }

    public static String timeFilterChange(String dateString) {
        // String input = "2011/02/14 00:00:00.000 -0800";
        DateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd");
        utcFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        Date date = null;
        try {
            date = utcFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }

        DateFormat pstFormat = new SimpleDateFormat("dd MMM, yyyy");
        pstFormat.setTimeZone(TimeZone.getTimeZone("PST"));

        return pstFormat.format(date);
    }

    public static Date convertStringToDate(String s) {

        // String input = "2011/02/14 00:00:00.000 -0800";
        DateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        utcFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        Date date = null;
        try {
            date = utcFormat.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }


    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
}