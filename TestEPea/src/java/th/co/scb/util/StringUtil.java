package th.co.scb.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import org.apache.commons.lang3.StringEscapeUtils;


public class StringUtil {

	public static String nullToBlank(final String str) {

		if (str == null) {
			return "";
		}
		return str.trim();
	}
	
	public static String nullToZero(final String str) {

		if (str == null || str.length() == 0) {
			return "0";
		}
		return str.trim();
	}
	
	public static boolean isNotBlank(final String str){
		if (str == null) {
			return false;
		}
		return true;
	}
	
	public static boolean isBlank(final String str){
		if (str == null || str.length() == 0) {
			return true;
		}
		return false;
	}
	
	public static String fillterComma(final String str) {

		String nStr = "";
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == ',') {
				continue;
			}
			nStr += str.charAt(i);
		}
		return nStr;
	}
	
	// convert string to int
	public static int stringToInt(final String str) {

		int istr = 0;
		if (!(str == null || str.equals(""))) {
			try {
				istr = Integer.parseInt(fillterComma(str.trim()));
			} catch (final NumberFormatException nfe) {
				istr = 0;
			}
		}
		return istr;
	}
	
	// trim by check not null
	public static String trim(final String str) {

		if (str != null) {
			return str.trim();
		} else {
			return "";
		}
	}
	
	public static String nullToBlankRightTrim(String str) {

		if (str == null) {
			return "";
		}
		// String leftSpace;
		for (int i = 0; i < str.length(); i++) {
			if (Character.isWhitespace(str.charAt(i))) {
			} else {
				str = str.substring(0, i) + str.substring(i, str.length()).trim();
				break;
			}
		}
		return str.trim();
	}
	
	public static String asciiToUnicode(String str) {
		if (!isBlank(str)) {
			return aciiToutf(str, "ISO-8859-1", "UTF-8");
		}
		return str;
	}
	
    public static String aciiToutf(String strAcii, String aciiEncode, String utfEncode) {
    	try{
    		byte[] aciibyte = strAcii.getBytes(aciiEncode);
    		String btfstring = new String(aciibyte,utfEncode);
    		return btfstring;
        } catch (Exception ex) {
    		return null;
    	}
    }    
	
	public static String Ascii2Unicode(String ascii) {
        // Cast null
        ascii = StringUtil.nullToBlank(ascii);
        StringBuffer unicode = new StringBuffer(ascii);
        int code;
        //  Do convert
        for (int i = 0; i < ascii.length(); i++) {
            code = (int) ascii.charAt(i);
            if ((0xA1 <= code) && (code <= 0xFB)) {
                unicode.setCharAt(i, (char) (code + 0xD60));
            }
        }

        return unicode.toString();
    }
	
	public static String convertStreamToString(InputStream is)   throws IOException {
		 /*
		  * To convert the InputStream to String we use the
		  * Reader.read(char[] buffer) method. We iterate until the
		  * Reader return -1 which means there's no more data to
		  * read. We use the StringWriter class to produce the string.
		  */
		 if (is != null) {
		     Writer writer = new StringWriter();
		
		     char[] buffer = new char[1024];
		     try {
		         Reader reader = new BufferedReader(
		                 //new InputStreamReader(is, "UTF-8"));
		         		 new InputStreamReader(is));
		         int n;
		         while ((n = reader.read(buffer)) != -1) {
		             writer.write(buffer, 0, n);
		         }
		     } finally {
		         is.close();
		     }
		     return writer.toString();
		 } else {        
		     return "";
		 }
	 }
	
	public static String subString(String str, int length) {
		String str2 = "";
		if(str == null){
			return "";
		}
		if(str.length() < length){
			return str;
		}
		str2 = str.substring(0, length);
		
		return str2;
	}
	
    //added 20150807 EscapeXML to fix IM
    public static String nullToBlankEscapeXML(final String str) {
        if (str == null) {
            return "";
        }
        return StringEscapeUtils.escapeXml(str.trim());
    }

}
