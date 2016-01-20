package cn.qaii.wifibus.common.http.request;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import cn.qaii.wifibus.busquery.model.Route;
import cn.qaii.wifibus.common.http.parser.Parser;
import cn.qaii.wifibus.common.model.UserInfo;
import cn.qaii.wifibus.frame.constant.LCommands;
import cn.qaii.wifibus.frame.constant.LHandlerMsg;
import cn.qaii.wifibus.frame.http.HttpResult;
import cn.qaii.wifibus.frame.http.LHttpRequest;
import cn.qaii.wifibus.frame.http.LHttpRequest.RequestCompleteListener;

/**
 * �������������֪ͨ���߳̽�����Ӧ����
 * @author larry
 * 
 */

public class LiveRequest extends LHttpRequest implements RequestCompleteListener {

	private Handler mHandler;
	private Message mMessage;

	private Map<String, Object> param;//��������
	private String command;//�����ǩ������ʶ��������ݵ�����

	public LiveRequest(Context context, Handler handler) {
		this.mHandler = handler;
		this.mMessage = mHandler.obtainMessage();
		this.setRequestCompleteListener(this);
	}
	

	@Override
	protected void initParams() {
		super.initParams();
		mParam = param;
		mCommand = command;
	}

	/**
	 * ��ȡ�û�����
	 */
	public void getFlow() {
		param = new HashMap<String, Object>();
		param.put("user_id", UserInfo.ID);
		command = LCommands.GET_FLOW;
		execute();
	}
	
	/**
	 * ģ����ѯ��·
	 */
	public void getRoutes(String key) {
		param = new HashMap<String, Object>();
		param.put("key", key);
		command = LCommands.GET_ROUTES;
		execute();
	}
	
	/**
	 * ��ѯվ����Ϣ
	 */
	public void getStations(int routeId) {
		param = new HashMap<String, Object>();
		param.put("route_id", routeId);
		command = LCommands.GET_STATIONS;
		execute();
	}
	
	/**
	 * ��ѯվ����Ϣ
	 */
	public void getBusComing(String stationId,String routeId,String num) {
		param = new HashMap<String, Object>();
		param.put("station_id", stationId);
		param.put("route_id", routeId);
		param.put("num", num);
		command = LCommands.GET_BUS_COMING;
		execute();
	}


	/**
	 * ������Ϣ�ɹ�
	 */
	@Override
	public void requestSuccessed(String data) {
		
			if (command.equals(LCommands.GET_FLOW)) {
				mMessage.what=LHandlerMsg.MSG_GET_FLOW;
				mMessage.obj = data;
				mHandler.sendMessage(mMessage);
			}else if(command.equals(LCommands.GET_ROUTES)){
				List <Route> routes = Parser.parseRoutesList(data);
				mMessage.what=LHandlerMsg.MSG_GET_ROUTES;
				mMessage.obj = routes;
				mHandler.sendMessage(mMessage); 
			}else if(command.equals(LCommands.GET_STATIONS)){
				mMessage.what=LHandlerMsg.MSG_GET_STATIONS;
				mMessage.obj = data;
				mHandler.sendMessage(mMessage);
			}else if(command.equals(LCommands.GET_BUS_COMING)){
				mMessage.what=LHandlerMsg.MSG_GET_BUS_COMING;
				mMessage.obj = data;
				mHandler.sendMessage(mMessage);
			}
		
	}

	/**
	 * ������Ϣʧ��
	 */
	@Override
	public void requestFailed(HttpResult result) {

		mMessage.what = LHandlerMsg.MSG_FAILED;
		mHandler.sendMessage(mMessage);
	}

}
