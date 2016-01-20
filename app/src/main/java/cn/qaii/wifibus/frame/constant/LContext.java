package cn.qaii.wifibus.frame.constant;

import android.content.Context;

public class LContext {

	/*********** ȫ�ֱ��� **************/
	public static Context context;
	public static String CLIENT_TYPE = "2000";///////////////////////////////////////////////
	public static String TOKEN = "";// ��¼�û�����֤����

	public static void init(Context context) {
		if (context == null) {
			return;
		}
		LContext.context = context;
	}
}
