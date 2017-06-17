package th.co.scb.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MqMessageUtils {
	
	public static String generateRqUID(){
		Date d = new Date();
		Timestamp t = new Timestamp(d.getTime());
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss.SSS", Locale.US);
		return format.format(new Date())+(t.getNanos()/1000000);
	}
	
	public static String formatDate(Date date){
		if(date == null){
			return "";
		}
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
		return format.format(date);
	}
	
	public static String formatDateYYYYMMDD(Date date){
		if(date == null){
			return "";
		}
		DateFormat format = new SimpleDateFormat("yyyyMMdd", Locale.US);
		return format.format(date);
	}
	
	public static String formatTime(Date date){
		if(date == null){
			return "";
		}
		DateFormat format = new SimpleDateFormat("HH:mm:ss", Locale.US);
		return format.format(date);
	}
	
	public static String formatTime2(Date date){
		if(date == null){
			return "";
		}
		DateFormat format = new SimpleDateFormat("HH.mm.ss", Locale.US);
		return format.format(date);
	}
	
	public static String formatTimeHHMMSS(Date date){
		if(date == null){
			return "";
		}
		DateFormat format = new SimpleDateFormat("HHmmss", Locale.US);
		return format.format(date);
	}
	
	public static String fillZeroAfter(String str, int length){
		
		if(str == null){
			str = new String();
		}
		if(str.length() >= length){
			str = cutString(str, length);
			return str;
		}else{
			int tmpLength = length - str.length();
			for(int i = 0; i < tmpLength; i++){
				str = str + "0";
			}
			return str;
		}
	}
	
	public static String fillZeroBefore(String str, int length){
		if(str == null){
			str = new String();
		}
		if(str.length() >= length){
			str = cutString(str, length);
			return str;
		}else{
			int tmpLength = length - str.length();
			for(int i = 0; i < tmpLength; i++){
				str = "0"+str;
			}
			return str;
		}
	}
	
	/*public static String fillBlankBefore(String str, int length){
		if(str == null){
			str = new String();
		}
		if(str.length() >= length){
			return str;
		}else{
			int tmpLength = length - str.length();
			for(int i = 0; i < tmpLength; i++){
				str = " "+str;
			}
			return str;
		}
	}*/
	
	public static String fillBlankAfter(String str, int length){
		if(str == null){
			str = new String();
		}
		if(str.length() >= length){
			str = cutString(str, length);
			return str;
		}else{
			int tmpLength = length - str.length();
			for(int i = 0; i < tmpLength; i++){
				str = str+" ";
			}
			return str;
		}
	}
	
	public static String formatDecimal(String amt, int numberLength , int decimalLength){
		
		if(amt == null){
			amt = "0";
		}
		
		String[] str = amt.split("\\.");
		
		//String number = str[0];
		String number = fillZeroBefore(str[0], numberLength);
		String decimal = "";
		if(str.length == 1){
			decimal = fillZeroAfter("", decimalLength);
		}else{
			decimal = str[1];
			decimal = fillZeroAfter(decimal, decimalLength);
		}
		
		//System.out.println("number : " + number);
		//System.out.println("decemal : " + decimal);
		
		return number+decimal;
	}
	
	public static String cutString(String str, int length){
		if(str == null){
			str = new String();
		}
		if(str.length() > length){
			return str.substring(0, length);
		}else{
			return str;
		}
	}
	
	
	
	public static void main(String[] args) {
		String str = fillZeroBefore("COMPANYUATAAAAAAAAAABBBBBBBBBBCCCCCCCCCCDDDDDDDDDDEEEEEEEEEE", 40);
		System.out.println("fill&cut : " + str);
		System.out.println("str length : " + str.length());
	}
}
