package cn.qaii.wifibus.frame.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * �ַ�����������
 * @author larry
 *
 */
public class StringUtil {
	
	public static final String STRING_YES = "Y";
	public static final String STRING_NO = "N";

	/**
	 * �Ƿ�Ϊ���ַ���
	 * @param str
	 * @return
	 */
	public static final boolean isNull(String str) {
		return str == null || "".equals(str.trim());
	}
	
	public static final boolean isEqual(String str1, String str2){
		if(str1 == null || str2 == null){
			return false;
		}
		
		return str1.equals(str2);
	}

	public static boolean ynToBoolean(String str) {
		return STRING_YES.equals(str) ? true : false;
	}

	public static Boolean ynToBooleanObject(String str) {
		if(STRING_YES.equals(str)) {
			return true;
		} else if(STRING_NO.equals(str)) {
			return false;
		} else {
			return null;
		}
	}
	
	/**
	 * �ж��Ƿ��ǵ�������
	 * @param date
	 * @return
	 */
	public static boolean isToday(Date date){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String today = format.format(new Date(System.currentTimeMillis()));
		String dateStr = format.format(date);
		if(today.equals(dateStr)){
			return true;
		}
		return false;
	}
	
	/**
	 * �ж��ַ����ǲ�������
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}
	
	/**
	 * ɾ��ָ���ַ�
	 * @param str
	 * @return
	 */
	public static String removeChar(String paramString, char paramChar) {
		String str = "";
		for (int i = 0; i < paramString.length(); i++) {
			if (paramString.charAt(i) == paramChar)
				continue;
			str = str + paramString.charAt(i);
		}
		return str;
	}
	
	/**
	 * ��ָ���ַ��������ַ�
	 * @param str
	 * @return
	 */
	public static String replaceCharWihString(String paramString1, char paramChar, String paramString2) {

		String str = "";
		for (int i = 0; i < paramString1.length(); i++) {
			if (paramString1.charAt(i) == paramChar) {
				str = str + paramString2;
				continue;
			}
			str = str + paramString1.charAt(i);
		}
		return str;
	}
	
	/**
	 * ɾ���ո�
	 * @param str
	 * @return
	 */
	public static String removeLeadingSpaces(String paramString) {
		return paramString.replaceAll("^\\s+", "");
	}
	
	/**
	 * �ж��û����Ƿ�Ϸ�
	 * */
	public static boolean accountIsLegal(String str) {
		try {
			Pattern pattern = Pattern.compile("^[a-zA-Z][a-zA-Z0-9_-]*$");
			return pattern.matcher(str).matches();
		} catch (Exception e) {

		}
		return false;
	}
	
	/**
	 * �ж��Ƿ��ǺϷ��ֻ���
	 */
	public static boolean isMobileNO(String mobiles) {
		try {
			Pattern p = Pattern.compile("^1\\d{10}$");
			return p.matcher(mobiles).matches();
		} catch (Exception e) {

		}
		return false;
	}

	/**
	 * ��֤����������ʽ�Ƿ����
	 */
	public static boolean isEmail(String email) {
		try {
			String emailPattern = "[a-zA-Z0-9][a-zA-Z0-9._-]{2,16}[a-zA-Z0-9]@[a-zA-Z0-9]+.[a-zA-Z0-9]+";
			return Pattern.matches(emailPattern, email);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
}

