package cn.qaii.wifibus.frame.constant;

import android.os.Environment;

import java.io.File;

public class LConstants {

	// =========================本地相关============================
		
		public static final String APP_TAG = "cx_investor"; // 项目关键�
		public static final String APP_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + APP_TAG + File.separator; // 项目路径
		static final String APP_PATH_TEST = APP_TAG + File.separator; // 项目路径
		public static final String LOG_PATH = APP_PATH + "log" + File.separator + "cx_investor.log"; // 日志文件路径
		public static final String CRASH_PATH = APP_PATH + "crash" + File.separator; // 日志文件路径
		public static final String SOUND_PATH = APP_PATH + "sound" + File.separator; // 录音文件路径
		public static final String TEMP_PATH = APP_PATH + "temp" + File.separator; // 临时文件路径,比如缓存图片
		public static final String DOWNLOAD_APP_PATH = APP_PATH + "download" + File.separator; // 临时文件路径,比如缓存图片
		public static final String TEMP_PATH_TEST = APP_PATH_TEST + "temp" + File.separator; // 临时文件路径,比如缓存图片
		public static final String CHARSET = "UTF-8";//字符编码
		
//		public static final String SOUND_NAME = "longau.amr";//
//		public static final String IMAGE_NAME = "longau.png";//
		public static final String IMAGE_NAME = "longau_" + System.currentTimeMillis() + ".jpg";//
		
		// 新业务数据�?�
//		public static String SERVER_NET_CHANNEL1 = "1,210.14.147.154,8066";//测试
		public static String SERVER_NET_CHANNEL1 = "1,211.155.90.20,8066";
		// 老业务数据�?�
//		public static final String SERVER_NET_CHANNEL2 = "1,192.168.1.119,9999";
		public static String SERVER_NET_CHANNEL2 = "1,113.31.87.135,9999";//测试
//		public static String SERVER_NET_CHANNEL2 = "1,211.155.85.151,9999";
		// 音频服务器�?�
		public static final String SERVER_NET_CHANNEL3 = "0,210.14.147.132,6001";

		public static String VOICE_IP = "211.155.90.21";
//		public static String VOICE_IP = "192.168.1.49";

		public static int VOICE_PORT = 1500;
		
		//==========================联网相关=============================
		public static String BASE_URL = "http://co.longau.com/req.aspx";//网络访问地址
//		public static String BASE_URL = "http://113.31.87.135:8585/req.aspx";//测试网络地址
		
		//快讯地址
		public static final String BASE_NEWS_URL = "http://leida.longau.com/ajax/liveNews/json";
		
//		public static String FEEDBACK_URL = "http://113.31.87.135:9090/page/wap/feedBack/";//反馈测试地址
		public static String FEEDBACK_URL = "http://www.caixinim.com/page/wap/feedBack/";//反馈网络地址
//		public static String HELP_URL = "http://113.31.87.135:9090/page/wap/help/0/";//帮助测试地址
		public static String HELP_URL = "http://www.caixinim.com/page/wap/help/0/";//帮助网络地址
//		public static String ABOUT_URL = "http://113.31.87.135:9090/page/wap/about/0/";//关于测试地址
		public static String ABOUT_URL = "http://www.caixinim.com/page/wap/about/0/";//关于网络地址
		
		public static final String BASE_KIND = "kind";
		public static final String BASE_CYCLE = "cycle";
		public static final String BASE_INDEX = "index";
		

		public static final String LOGIN_PREFERENCE="login";
		public static final String USER_TEL="user_tel";







	//andBase的常量
	public static final boolean DEBUG = true;
	public static final String sharePath = "andbase_share";
	public static final String USERSID = "user";
	//页面默认显示南京，登陆后显示注册用户的城市
	public static final String CITYID = "cityId";
	public static final String CITYNAME = "cityName";
	public static final String DEFAULTCITYID = "1001";
	public static final String DEFAULTCITYNAME = "青岛";

	//cookies
	public static final String USERNAMECOOKIE = "cookieName";
	public static final String USERPASSWORDCOOKIE = "cookiePassword";
	public static final String USERPASSWORDREMEMBERCOOKIE = "cookieRemember";
	public static final String ISFIRSTSTART = "isfirstStart";

	// 连接超时
	public static final int timeOut = 12000;
	// 建立连接
	public static final int connectOut = 12000;
	// 获取数据
	public static final int getOut = 60000;

	//1表示已下载完成
	public static final int downloadComplete = 1;
	//1表示未开始下载
	public static final int undownLoad = 0;
	//2表示已开始下载
	public static final int downInProgress = 2;
	//3表示下载暂停
	public static final int downLoadPause = 3;

	public static final String BASEURL = "http://www.amsoft.cn/";

	//应用的key
	//1512528
	public final static String APPID = "1512528";

	//百度地图 jfa97P4HIhjxrAgfUdq1NoKC
	public final static String APIKEY = "jfa97P4HIhjxrAgfUdq1NoKC";

	//消息服务
	public static final String xmppHost = "192.168.1.106";
	public static final int xmppPort = 5222;




	//自定义的常量
	public static final String SERVER_PATH="http://121.42.49.35/wifi/index.php";
		
		public static String QUERY_FLOW_URL = SERVER_PATH+"/home/user/update/id/";//获取用户剩余流量
		public static String CHANGE_FLOW_URL = SERVER_PATH+"/home/user/update/id/";//更改用户剩余流量
		public static String GET_ROUTES_URL = SERVER_PATH+"/home/line/search/key/";//获取线路列表
		public static String GET_STATIONS_URL = SERVER_PATH+"/home/line/read/id/";//获取站点信息列表
		public static String GET_BUS_COMING_URL = SERVER_PATH+"/home/line/station/lid/";//获取当前到站车辆信息
	}
