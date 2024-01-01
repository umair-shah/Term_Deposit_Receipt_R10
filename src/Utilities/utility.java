package Utilities;
import java.util.Calendar;
import java.util.Date;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

 public final class utility {
	
	public static java.sql.Date toDate(String NEW_FORMAT,String date) throws ParseException
	{
		// August 12, 2010
		Date dt = new SimpleDateFormat(NEW_FORMAT).parse(date);
		return  new java.sql.Date(dt.getTime());
	}
	public static Connection db_conn()
	{
		try 
		{
			Class.forName("COM.ibm.db2.jdbc.app.DB2Driver");
		} 
		catch (ClassNotFoundException e1) 
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		java.sql.Connection lcl_conn = null;
		try
		{
			 //lcl_conn = java.sql.DriverManager.getConnection("jdbc:db2://10.51.41.100:50000/US27501", "db2admin", "admin123/?");
			lcl_conn = java.sql.DriverManager.getConnection("jdbc:db2:US27501", "db2admin", "admin123/?");
			if(lcl_conn != null)
			{
				return lcl_conn;
			}
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return null;
	}
    public static String lpad(String s, char c, int n) {
        if (s.length() >= n) {
            return s;
        }

        StringBuilder result = new StringBuilder();
        for (int i = s.length(); i < n; i++) {
            result.append(c);
        }

        result.append(s);
        return result.toString();
    }
    public static Object[] calculateDateDifference(String startDateStr, int monthsToAdd, String endDateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date startDate = sdf.parse(startDateStr);
            Date endDate = sdf.parse(endDateStr);

            Calendar calendar = Calendar.getInstance();

            // Add months to startDate
            calendar.setTime(startDate);
            calendar.add(Calendar.MONTH, monthsToAdd);
            Date newDate = calendar.getTime();

            // Set calendar to newDate and get its year, month, and day
            calendar.setTime(newDate);
            int newYear = calendar.get(Calendar.YEAR);
            int newMonth = calendar.get(Calendar.MONTH);
            int newDay = calendar.get(Calendar.DAY_OF_MONTH);

            // Set calendar to endDate and get its year, month, and day
            calendar.setTime(endDate);
            int endYear = calendar.get(Calendar.YEAR);
            int endMonth = calendar.get(Calendar.MONTH);
            int endDay = calendar.get(Calendar.DAY_OF_MONTH);

            // Calculate difference in months and days
            int diffYear = endYear - newYear;
            int diffMonth = endMonth - newMonth;
            int diffDay = endDay - newDay;

            if (diffDay < 0) {
                diffMonth--;
                calendar.add(Calendar.MONTH, -1);
                diffDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH) + diffDay;
            }

            return new Object[] {diffYear * 12 + diffMonth, diffDay};

        } catch (ParseException e) {
            e.printStackTrace();
            return new  Object[] {0, 0}; // or handle error appropriately
        }
    }


    }


