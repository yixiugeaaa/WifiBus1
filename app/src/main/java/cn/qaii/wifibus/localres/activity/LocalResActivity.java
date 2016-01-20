package cn.qaii.wifibus.localres.activity;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.TrafficStats;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cn.qaii.wifibus.R;
import cn.qaii.wifibus.common.http.request.ScheduleRun;
import cn.qaii.wifibus.common.view.ActivityWithOptions;

/**
 * @Description:����WebView���·����������Դ
 * @author yxg
 */
public class LocalResActivity extends ActivityWithOptions {
	private ProgressBar progressBar;
	private Button button1, button2, button3, button4, button5;
	private WebView webView;
	private TextView flowTv, tv1, tv2, tv3, tv4;// ������
	private Handler flowHandler;
	private int uId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_local_res);

		super.setBackActivity(true);// ʹ�ܺ��˰�ť
		super.onCreatePost();// ��ʼ��ActionBar
		this.actionBar.setTitle(R.string.local_resource);// ���ñ���

		// ��ȡ��ǰӦ�õ�Uid��������ر�Ӧ�õ�����
		ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		ApplicationInfo appinfo = getApplicationInfo();
		List<RunningAppProcessInfo> run = am.getRunningAppProcesses();
		for (RunningAppProcessInfo runningProcess : run) {
			if ((runningProcess.processName != null) && runningProcess.processName.equals(appinfo.processName)) {
				uId = runningProcess.uid;
				break;
			}
		}

		// �ж��Ƿ���wifi����
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if (!networkInfo.isConnected()) {
			Toast.makeText(LocalResActivity.this, "��ǰ������Wifi��������鿴���Ƿ����ӳ���wifi��", Toast.LENGTH_SHORT).show();
		}

		// ��ȡ���
		progressBar = (ProgressBar) findViewById(R.id.progressBar1);
		button1 = (Button) findViewById(R.id.button1);
		button2 = (Button) findViewById(R.id.button2);
		button3 = (Button) findViewById(R.id.button3);
		button4 = (Button) findViewById(R.id.button4);
		button5 = (Button) findViewById(R.id.button5);
		webView = (WebView) findViewById(R.id.webView1);
		flowTv = (TextView) findViewById(R.id.flow_count);
		tv1 = (TextView) findViewById(R.id.textView1);
		tv2 = (TextView) findViewById(R.id.textView2);
		tv3 = (TextView) findViewById(R.id.textView3);
		tv4 = (TextView) findViewById(R.id.textView4);
		flowTv.setVisibility(View.GONE);
		tv1.setVisibility(View.GONE);
		tv2.setVisibility(View.GONE);
		tv3.setVisibility(View.GONE);
		tv4.setVisibility(View.GONE);

		// �������߳�UI
		flowHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				String[] result = (String[]) msg.obj;
				flowTv.setText(result[0] + "KB");
				tv1.setText(result[1] + "KB  ");
				tv2.setText(result[2] + "KB  ");
				tv3.setText(result[3] + "KB  ");
				tv4.setText(result[4] + "KB  ");
			}
		};

		long mobileRecv = TrafficStats.getMobileRxBytes();
		long mobileSend = TrafficStats.getMobileTxBytes();
		long totalRecv = TrafficStats.getTotalRxBytes();
		long totaleSend = TrafficStats.getTotalTxBytes();
		long selfrecv = TrafficStats.getUidRxBytes(uId);
		long selfsend = TrafficStats.getUidTxBytes(uId);
		long beforSelfFlow = selfrecv + selfsend;// ����Ӧ��ǰ�ı�Ӧ�õ�����
		long beforFlowW = totalRecv + totaleSend - mobileRecv - mobileSend;// ����Ӧ��ǰ������Wifi����
		// ������ʱ��������̣߳���һ�������Ĺ������趨ˢ��ʱ�䣬��λ��0.1s
		new ScheduleRun(100, LocalResActivity.this, flowHandler, beforFlowW, beforSelfFlow, uId);//�����첽�̣߳���ʱ�����û�ʣ������

		// ����JavaScript���ã����Ҵ���JavaScript�ĵ�����
		webView.getSettings().setJavaScriptEnabled(true);
		// ���ü��ؽ�����
		webView.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				super.onProgressChanged(view, newProgress);
				if (newProgress == 100) {
					progressBar.setVisibility(View.INVISIBLE);
				} else {
					if (View.INVISIBLE == progressBar.getVisibility()) {
						progressBar.setVisibility(View.VISIBLE);
					}
					progressBar.setProgress(newProgress);
				}
			}
		});
		webView.setWebViewClient(new WebViewClient());// ��ʹ�����������

		webView.loadUrl("http://m.hao123.com");

		// ǰ����ť
		button1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				webView.stopLoading();
				webView.goForward();
			}

		});
		// ���˰�ť
		button2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				webView.stopLoading();
				webView.goBack();
			}

		});
		// ˢ�°�ť
		button3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				webView.stopLoading();
				webView.reload();
			}

		});
		// ֹͣ��ť
		button4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				webView.stopLoading();
			}
		});
	}

	/** ��������˵����ִ�������״̬����ϵͳ�����̨ */
	public void onBackPressed() {
		if (this.actionBar.onBackHandler())
			System.exit(0);
	}

	/**
	 * �жϵ�ǰ�Ƿ���Wifi���������״̬
	 */
	public static boolean isWifiActive(Context icontext) {
		Context context = icontext.getApplicationContext();
		ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo[] info;
		if (connectivity != null) {
			info = connectivity.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getTypeName().equals("WIFI") && info[i].isConnected()) {
						return true;
					}
				}
			}
		}
		return false;
	}
}
