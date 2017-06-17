package th.co.scb.util;

import java.util.regex.Pattern;

import org.apache.commons.lang3.StringEscapeUtils;


/**
 *
 * @author s51486
 *
 */
public final class XMLUtil {
	
	public static String findFirstTag(String xml){

		int first = xml.indexOf("<") + 1;
		int last = xml.indexOf(">", first);
		if (first == 0 || last == -1)
			return null;
		String tag = xml.substring(first, last);
		if (xml.indexOf("</" + tag + ">") == -1)
			return null;
		return tag;
	}
	
	public static String getTagValue(String tag,String xml){
		String s = null;
		String start = "<"+tag+">";
		String end = "</"+tag+">";
		try{
			int a = xml.indexOf(start);
			int b = xml.indexOf(end);
			s = xml.substring(a+start.length(),b);
		}catch (Exception e) {
			// --: handle exception
			s = null;
		}
		return s;
	}
	
	public static String removeFirstTag(String tag,String xml){
		String start = "<"+tag+">";
		String end = "</"+tag+">";
		int a = xml.indexOf(start);
		int b = xml.indexOf(end,a+1);
		String s = xml.substring(b+end.length());
		return s;
	}
	
	public static String addCDATA(String msg) {
		if (msg.equals(""))
			return "";
		msg = StringEscapeUtils.unescapeXml(msg);
		return "<![CDATA[" + msg + "]]>";
	}

	/**
	 * Delete xml header ( <?xml ......... ?>)
	 * @param xml source xml
	 * @return xml without head
	 */
	public static String removeXmlHeader(String xml){
		StringBuilder str = new StringBuilder("");
		Pattern pattern = Pattern.compile("<\\?[\\W\\w]*\\?>");
		String[] ss = pattern.split(xml);
		for(String s:ss){
			if(false==s.trim().equals(""))str.append(s);
		}
		return str.toString().trim();
	}	
}