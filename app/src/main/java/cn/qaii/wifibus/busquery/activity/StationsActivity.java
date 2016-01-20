package cn.qaii.wifibus.busquery.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.qaii.wifibus.R;
import cn.qaii.wifibus.busquery.adapter.StationsAdapter;
import cn.qaii.wifibus.busquery.model.Bus;
import cn.qaii.wifibus.busquery.model.Station;
import cn.qaii.wifibus.busquery.util.ComingBus;
import cn.qaii.wifibus.common.http.parser.Parser;
import cn.qaii.wifibus.common.http.request.LiveRequest;
import cn.qaii.wifibus.common.view.ActivityWithOptions;
import cn.qaii.wifibus.frame.constant.LHandlerMsg;

/**
 * ��վ��Ϣ�������ȡ������վ��Ϣ
 * 
 */

public class StationsActivity extends ActivityWithOptions {
	private ProgressBar progressBar;
	private TextView isloading;
	private ExpandableListView listView;
	private StationsAdapter adapter;
	private TextView routeNum, terminal, forwardTime, price;
	private int beforgroupPosition = 1000;// ��¼�ϴ�ѡ�е�listview��ѡ��
	private int routeId, expandId;
	private List<Station> stations;// ��վ�б�
	private LinearLayout notFound;
	private int curSort;// ��ǰѡ�г�վ��sortֵ
	public static List<Bus> buses = new ArrayList<Bus>();// ��վ�����б�

	private final Handler myHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what != LHandlerMsg.MSG_GET_BUS_COMING)
				return;
			String result = (String) msg.obj;
			buses = Parser.parseComingBusList(result);
			if (!buses.isEmpty()) {
				listView.expandGroup(expandId);
				notFound.setVisibility(View.INVISIBLE);
				buses = ComingBus.getComingBus(stations, buses, curSort);
			} else {
				notFound.setVisibility(View.VISIBLE);
			}
			progressBar.setVisibility(View.INVISIBLE);
			isloading.setVisibility(View.INVISIBLE);
			((BaseExpandableListAdapter) adapter).notifyDataSetChanged();// ˢ��listview
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stations);

		listView = (ExpandableListView) findViewById(R.id.expandableListView);
		routeNum = (TextView) findViewById(R.id.textView_route_num);
		terminal = (TextView) findViewById(R.id.textView_terminal);
		forwardTime = (TextView) findViewById(R.id.textView_forward_time);
		price = (TextView) findViewById(R.id.textView_price);
		progressBar = (ProgressBar) findViewById(R.id.ProgressBar_station_query);
		isloading = (TextView) findViewById(R.id.station_isloading);
		notFound = (LinearLayout) findViewById(R.id.coming_bus_empty);
		Intent intent = getIntent();
		String routeStr = intent.getStringExtra("route_info");
		routeId = intent.getIntExtra("route_id", 0);
		ArrayList<Map<String, Object>> routeInfo = Parser.parseRouteInfoList(routeStr);
		stations = (List<Station>) routeInfo.get(0).get("stations");

		Map<String, Object> map = routeInfo.get(1);
		terminal.setText(stations.get(0).getName() + "-" + stations.get(stations.size() - 1).getName());// ʼ��վ���յ�վ
		routeNum.setText((String) map.get("name"));// ��·��
		forwardTime.setText((String) map.get("start_time") + "-" + (String) map.get("end_time"));// �װ೵��ĩ�೵ʱ��

		/** �Ƿ��˹���Ʊ��Ʊ�� */
		if (((String) map.get("type")).equals("0")) {
			price.setText("������Ʊ  Ʊ��" + (String) map.get("price") + "Ԫ");
		} else {
			price.setText("������Ʊ ȫ��Ʊ�� " + (String) map.get("price") + "Ԫ");
		}

		adapter = new StationsAdapter(this, stations);
		listView.setAdapter(adapter);

		/** ֻ����һ��group����չ��״̬ */
		listView.setOnGroupExpandListener(new OnGroupExpandListener() {
			@Override
			public void onGroupExpand(int groupPosition) {
				expandId = groupPosition;

				for (int i = 0; i < adapter.getGroupCount(); i++) {
					if (groupPosition != i) {
						listView.collapseGroup(i);
					}
				}
			}
		});

		/** �Ƿ��˹���Ʊ��Ʊ�� */
		listView.setOnGroupClickListener(new OnGroupClickListener() {
			@Override
			public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
				curSort = stations.get(groupPosition).getSort();
				buses.clear();
				progressBar.setVisibility(View.VISIBLE);
				isloading.setVisibility(View.VISIBLE);

				new LiveRequest(StationsActivity.this, myHandler).getBusComing(String.valueOf(stations.get(groupPosition).getId()),
						String.valueOf(routeId), "100");// ����վ��Ϣ

				/** �����κ��ϴ�ѡ�����ͬһ��group��������ɣ����򷵻�true���������� */
				if (beforgroupPosition != groupPosition) {
					beforgroupPosition = groupPosition;
					return false;
				} else {
					beforgroupPosition = groupPosition;
					return true;
				}
			}
		});
		listView.setGroupIndicator(null);// ����������ͷͼ��
		super.setBackActivity(true);// ʹ�ܷ��ذ�ť
		super.onCreatePost();// ��ʼ��ActionBar
		this.actionBar.setTitle(R.string.stations_query);// ���ñ���
	}

}