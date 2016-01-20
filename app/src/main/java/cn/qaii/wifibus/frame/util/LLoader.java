package cn.qaii.wifibus.frame.util;

import android.content.Context;
import cn.qaii.wifibus.frame.constant.LContext;
import cn.qaii.wifibus.frame.http.HttpExecutor;


/**
 * 应用开启时先检查并初始化基础数据
 */
public class LLoader {

	public static boolean isInit = false;

	public static void initApp(Context context) {
		if (isInit) {
			return;
		}
		isInit = true;
		LContext.init(context);
		HttpExecutor.init();
		LLogger.e("数据初始化完毕");
	}
}
