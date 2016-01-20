package cn.qaii.wifibus.common.http.request;

import android.content.Context;
import android.net.TrafficStats;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Timer;
import java.util.TimerTask;

import cn.qaii.wifibus.common.model.UserInfo;
import cn.qaii.wifibus.frame.constant.LCommands;
import cn.qaii.wifibus.frame.constant.LConstants;
import cn.qaii.wifibus.frame.util.LLogger;
import cn.qaii.wifibus.localres.activity.LocalResActivity;
/**
 * ��ʱ�����û�ʣ������
 *
 */
public class ScheduleRun {
	int uId;
	Timer timer;
	int delaytime;
	ScheduleRunTask refTask = new ScheduleRunTask();
	Handler handler;
	long beforFlowW,beforSelfFlow,realFlow;
	Context icontext;
	

	public ScheduleRun(int delaytime,Context icontext,Handler handler,long totalFlow,long selfFlow,int uId) {
		timer = new Timer();
		timer.schedule(refTask, 0, delaytime * 100);// ����ˢ�¼��ʱ��
		this.icontext=icontext;
		this.delaytime = delaytime;
		this.handler=handler;
		this.beforFlowW=totalFlow;
		this.beforSelfFlow=selfFlow;
		this.uId=uId;
	}

	/** ֹͣ��ʱ�� */
	public void stop() {
		timer.cancel();
	}

	class ScheduleRunTask extends TimerTask {

		
		@Override
		public void run() {
			boolean isWifi=LocalResActivity.isWifiActive(icontext);
			
			long mobileRecv=TrafficStats.getMobileRxBytes();
			long mobileSend=TrafficStats.getMobileTxBytes(); 
			long mobileFlow=mobileRecv+mobileSend;
			
			long totalRecv=TrafficStats.getTotalRxBytes();
			long totalSend=TrafficStats.getTotalTxBytes();
			long totalFlow=totalRecv+totalSend;
			
			Long curFlowW=totalFlow-mobileFlow;
			
			long usedFlowW=curFlowW-beforFlowW;//��ǰʱ��ε�Wifi����
			beforFlowW=curFlowW;
			
			long selfrecv=TrafficStats.getUidRxBytes(uId);
			long selfsend=TrafficStats.getUidTxBytes(uId);
			long curSelfFlow=selfrecv+selfsend;
			
			long usedSelfFlow=curSelfFlow-beforSelfFlow;//��ǰʱ��εı�Ӧ������
			beforSelfFlow=curSelfFlow;
			
			if(isWifi){
				String usedFlow=String.valueOf((usedFlowW-usedSelfFlow)/1024);
				realFlow+=(usedFlowW-usedSelfFlow);//��ǰʱ��ε�Wifi�������ų��˱�Ӧ�õ�����
				int rf=(int)(realFlow/1024);
				HttpClient hc = new DefaultHttpClient();
				String urlStr=LConstants.CHANGE_FLOW_URL+UserInfo.ID+"/command_id/"+LCommands.RESIZE_FLOW+"/num/"+usedFlow;
				LLogger.e(urlStr);
				HttpGet hg = new HttpGet(urlStr);
				try {
					HttpResponse hr = hc.execute(hg);
					if (hr.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
						HttpEntity entity = hr.getEntity();
						InputStream is = entity.getContent();
						BufferedReader br = new BufferedReader(
								new InputStreamReader(is));
						String recvStr=null;
						if((recvStr=br.readLine())!=null){
							System.out.println("true");
						}else{
							Toast.makeText(icontext, "��������ʧ�ܣ�",Toast.LENGTH_SHORT );	
						}
						is.close();
					} else {
						Toast.makeText(icontext, "ͨ��ʧ�ܣ�",Toast.LENGTH_SHORT );
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			int rf=(int)(realFlow/1024);
			Message msg = new Message();
			String[] result =new String [5];
			result[0]=String.valueOf(rf);
			result[1]=String.valueOf(usedFlowW/1024);
			result[2]=String.valueOf(usedSelfFlow /1024);
			result[3]=String.valueOf(curSelfFlow/1024);
			
			msg.obj = result;
			handler.sendMessage(msg);
			
		}
	}
}
