package cn.qaii.wifibus.common.http.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import cn.qaii.wifibus.busquery.model.Bus;
import cn.qaii.wifibus.busquery.model.Route;
import cn.qaii.wifibus.busquery.model.Station;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * �������������ص�json�ı�
 * 
 */
public class Parser {

	/**
	 * ����վ���б�
	 * 
	 * @param data
	 * @return ·��ʵ���б�
	 */
	public static List<Route> parseRoutesList(String data) {
		List<Route> routes = null;
		try {
			JSONObject json = new JSONObject(data);
			String routeStr = json.getString("lines");
			if (routeStr != null)
				routes = new Gson().fromJson(routeStr, new TypeToken<List<Route>>() {
				}.getType());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return routes;
	}

	/**
	 * ������·��Ϣ
	 * 
	 * @param data
	 * @return ����Map���ֱ���վ���б����·��Ϣ
	 */
	public static ArrayList<Map<String, Object>> parseRouteInfoList(String data) {
		ArrayList<Map<String, Object>> routeInfo = new ArrayList<Map<String, Object>>();
		List<Station> stations=null;
		try {
			JSONObject json = new JSONObject(data);
			String stationStr = json.getString("stations");
			if(stationStr!=null)
			stations = new Gson().fromJson(stationStr, new TypeToken<List<Station>>() {
			}.getType());
			
			Map<String, Object> map1 = new HashMap<String, Object>();
			map1.put("stations", stations);

			Map<String, Object> map2 = new HashMap<String, Object>();
			map2.put("name", json.getString("name"));//��·����
			map2.put("start_time", json.getString("start_time"));//�װ�ʱ��
			map2.put("end_time", json.getString("end_time"));//ĩ��ʱ��
			map2.put("type", json.getString("type"));//�Ƿ�������Ʊ 0-������Ʊ
			map2.put("price", json.getString("price"));//Ʊ��

			routeInfo.add(map1);
			routeInfo.add(map2);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return routeInfo;
	}

	/**
	 * ������վ����
	 * 
	 * @param data
	 * @return ������վ����ʵ���б�
	 */
	public static List<Bus> parseComingBusList(String data) {
		List<Bus> buses = null;
		try {
			JSONObject json = new JSONObject(data);
			String routeStr = json.getString("bus");
			if(routeStr!=null)
			buses = new Gson().fromJson(routeStr, new TypeToken<List<Bus>>() {
			}.getType());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return buses;
	}

}
