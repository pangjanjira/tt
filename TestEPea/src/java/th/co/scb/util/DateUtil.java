package th.co.scb.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

public class DateUtil {
	
	public static String getDateTimeX() {
		Locale locale = new Locale("en", "EN");
		Calendar chkDate = Calendar.getInstance(locale); // create calendar
		SimpleDateFormat xDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", locale);
		// System.out.println(xDateFormat.format(chkDate.getTime()));
		return String.valueOf(xDateFormat.format(chkDate.getTime()));

	}
	
	public static String getDateTimeFileReturn() {
		Locale locale = new Locale("en", "EN");
		Calendar chkDate = Calendar.getInstance(locale); // create calendar
		SimpleDateFormat xDateFormat = new SimpleDateFormat("yyyyMMddHHmmss", locale);
		// System.out.println(xDateFormat.format(chkDate.getTime()));
		return String.valueOf(xDateFormat.format(chkDate.getTime()));

	}
	
	public static String getDateForFileName(int iday) {
		
        Locale locale = new Locale("en", "EN");
        Calendar chkDate = Calendar.getInstance(locale);//create calendar
        chkDate.add(Calendar.DATE, iday);
        SimpleDateFormat xDateFormat = new SimpleDateFormat("yyyyMMdd", locale);

        return String.valueOf(Integer.parseInt(xDateFormat.format(chkDate.getTime())));

    }
	
	public static String getDateTh(int iday) {
		
        Locale locale = new Locale("th", "TH");
        Calendar chkDate = Calendar.getInstance(locale);//create calendar
        chkDate.add(Calendar.DATE, iday);
        SimpleDateFormat xDateFormat = new SimpleDateFormat("dd/MM/yyyy", locale);

        return String.valueOf(xDateFormat.format(chkDate.getTime()));

    }
	
	public static String getDateFormatYYYYMMDDHHMM(final Date date) {

		String strDate = "";
		if (date != null) {
			final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
			strDate = sdf.format(date);
		}else{
			//strDate = "0000-00-00T00:00:00";
			strDate = "";
		}
		return strDate;
	}
	
	public static Calendar getSystemCalendar() {

		final Calendar cal = Calendar.getInstance(Locale.US);
		return cal;
		
	}

	public static String getThaiDateFormatYYYYMMDD(final Date date) {

		Locale locale = new Locale("en", "EN");
		
		String thaiDate = "";
		if (date != null) {
			final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", locale);
			thaiDate = sdf.format(date);
			int year = Integer.parseInt(thaiDate.substring(0, 4));
			year = year + 543;
			thaiDate = String.valueOf(year) + thaiDate.substring(4, 8);
		}
		return thaiDate;
	}
	
	public static String getDateFormatENYYYYMMDD(final Date date) {

		String strDate = "";
		if (date != null) {
			final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.US);
			strDate = sdf.format(date);
		}
		return strDate;
	}
	
	public static String getDateFormatYYYYMMDD(final Date date) {

		String strDate = "";
		if (date != null) {
			final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
			strDate = sdf.format(date);
		}else{
			strDate = "0000-00-00";
		}
		return strDate;
	}
	
	//from dd/MM/YYYY HH:mm:ss
	//to yyyy-mm-dd
	public static String convertFormatDateString(String dateStr){
		
		//System.out.println("dateStr : " + dateStr);
		String newDateStr = "";
		if(dateStr != null && dateStr.length() > 0){
			if(dateStr.length() > 10){
				
				dateStr = StringUtil.subString(dateStr, 10);
				
				
				final String str_dd = dateStr.substring(0, 2);
				final String str_mm = dateStr.substring(3, 5);
				final String str_yy = dateStr.substring(6, dateStr.length());
				
				newDateStr = str_yy + "-"+ str_mm + "-"+ str_dd;
				//System.out.println("newDateStr : " + newDateStr);
			}
			
		}
		
		
		return newDateStr;
		
		
	}
	
	//UR58120031 Phase 2 (send extend request to ALS, send SMS, send mail)
	//from yyyy-MM-dd
	//to yyyyMMdd
	public static String convertFormatDateStringToYYYYMMDD(String dateStr){
		//System.out.println("dateStr : " + dateStr);
		String newDateStr = "";
		if(dateStr != null && dateStr.length() > 0){
			if(dateStr.length() >= 10){
				dateStr = StringUtil.subString(dateStr, 10);
				final String str_yy = dateStr.substring(0, 4);
				final String str_mm = dateStr.substring(5, 7);
				final String str_dd = dateStr.substring(8, dateStr.length());
				newDateStr = str_yy + str_mm + str_dd;
				//System.out.println("newDateStr : " + newDateStr);
			}
		}
		return newDateStr;
	}
	
	public static Date addYear(final Date date, final int i) {

		final Calendar cal = Calendar.getInstance(Locale.US);
		cal.setTime(date);
		cal.add(Calendar.YEAR, i);
		return cal.getTime();
	}
	
	public static Date addMonth(final Date date, final int i) {

		final Calendar cal = Calendar.getInstance(Locale.US);
		cal.setTime(date);
		cal.add(Calendar.MONTH, i);
		return cal.getTime();
	}
	
	// DateUtil.stringToDate("2001-01-21")
	// format yyyy-mm-dd
	public static Date stringToDate(final String str) {

		if (StringUtil.nullToBlank(str).equals("")) {
			return null;
		} else {

			final String str_yy = str.substring(0, 4);
			final String str_mm = str.substring(5, 7);
			final String str_dd = str.substring(8, str.length());
			
			 //System.out.println("dd : " + str_dd + " ,  mm : " + str_mm
			 //+ ", yy : " + str_yy);
			return stringToDate(str_dd, str_mm, str_yy);
		}
	}

	public static Date stringToDate(final String dd, final String mm, final String yy) { // format

		// dd/mm/yyyy
		if (dd == null || mm == null || yy == null) {
			return null;
		}
		final Calendar calendar = Calendar.getInstance(Locale.US);
		calendar.set(Integer.parseInt(yy), Integer.parseInt(mm) - 1, Integer.parseInt(dd));
		final Date ret_date = calendar.getTime();
		return ret_date;
	}
	
	public static XMLGregorianCalendar genXMLGregorianCalendar(Date date){
		
		XMLGregorianCalendar xmlDate = null;
		
		try{
			if(date != null){
				Locale local = new Locale("th", "TH");
				GregorianCalendar calendar = new GregorianCalendar(TimeZone.getDefault(), local);
				calendar.setTime(date);
				xmlDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
				xmlDate.setTimezone(DatatypeConstants.FIELD_UNDEFINED);
				xmlDate.setMillisecond(DatatypeConstants.FIELD_UNDEFINED);
			}
		}catch (Exception e) {
			// --: handle exception
			e.printStackTrace();
		}
		

		return xmlDate;
	}

	public static XMLGregorianCalendar convertDateStrToXMLGregorianCalendar(String dateXMLGregorianCalendar){
		XMLGregorianCalendar xmlDate = null;
		
		try{
			if(dateXMLGregorianCalendar != null && dateXMLGregorianCalendar.length() > 0){
				xmlDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(dateXMLGregorianCalendar);
				xmlDate.setTimezone(DatatypeConstants.FIELD_UNDEFINED);
				xmlDate.setMillisecond(DatatypeConstants.FIELD_UNDEFINED);
			}
		}catch (Exception e) {
			// --: handle exception
			e.printStackTrace();
		}
		
		return xmlDate;
	}
	
	
	
	
	public static void main(String[] args) {
		
		//System.out.println(getDateFormatYYYYMMDD(new Date()));
		//System.out.println(addYear(stringToDate("29/02/2012"), 1));
		
		//System.out.println(stringToDate("2012-02-29"));
		System.out.println(new String("CAM054_Action20130211192041780-TH0041010353980000000010001T5_GAAA5602A0270_TH0031010248070000000000001T4.xml").length());
		
	}
	
	
	
}
