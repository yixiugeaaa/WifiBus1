package cn.qaii.wifibus.app;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import cn.qaii.wifibus.common.model.UserInfo;
import cn.qaii.wifibus.frame.constant.LConstants;
import cn.qaii.wifibus.frame.util.LLoader;
import cn.qaii.wifibus.frame.util.LLogger;

public class BaseApplication extends Application {
	// 登录用户
	public UserInfo mUser = null;

	public String cityid = LConstants.DEFAULTCITYID;
	public String cityName = LConstants.DEFAULTCITYNAME;
	public boolean userPasswordRemember = false;
	public boolean ad = false;
	public boolean isFirstStart = true;
	public boolean isLogin = false;
	public SharedPreferences mSharedPreferences = null;

	/** 默认城市 */
	public String province;
	public String city;
	public double longitude;
	public double latitude;
	public String address;
	
	@Override
	public void onCreate() {
		super.onCreate();
		initLoginParams();
		LLogger.isDebug = true;//false-不允许调试 true-允许调试
		LLoader.initApp(getApplicationContext());
	}

	/**
	 * 上次登录参数
	 */
	private void initLoginParams() {
		String userName = mSharedPreferences.getString(LConstants.USERNAMECOOKIE, null);
		String userPwd = mSharedPreferences.getString(LConstants.USERPASSWORDCOOKIE,
				null);
		Boolean userPwdRemember = mSharedPreferences.getBoolean(
				LConstants.USERPASSWORDREMEMBERCOOKIE, false);
		mUser = new UserInfo();
		mUser.setUserName(userName);
		mUser.setPassword(userPwd);
		userPasswordRemember = userPwdRemember;
	}

	//更新登录信息
	public void updateLoginParams(UserInfo user) {
		mUser = user;
		if (userPasswordRemember) {
			Editor editor = mSharedPreferences.edit();
			editor.putString(LConstants.USERNAMECOOKIE, user.getUserName());
			editor.putString(LConstants.USERPASSWORDCOOKIE, user.getPassword());
			editor.putBoolean(LConstants.ISFIRSTSTART, false);
			editor.commit();
		} else {
			Editor editor = mSharedPreferences.edit();
			editor.putBoolean(LConstants.ISFIRSTSTART, false);
			editor.commit();
		}
		isFirstStart = false;
		isLogin = true;
	}

	/**
	 * 清空上次登录参数
	 */
	public void clearLoginParams() {
		Editor editor = mSharedPreferences.edit();
		editor.clear();
		editor.commit();
		mUser = null;
		isLogin = false;
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
	}

}
