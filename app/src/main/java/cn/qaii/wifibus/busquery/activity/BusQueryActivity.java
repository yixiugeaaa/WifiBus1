package cn.qaii.wifibus.busquery.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import cn.qaii.wifibus.R;
import cn.qaii.wifibus.busquery.adapter.RouteAdapter;
import cn.qaii.wifibus.busquery.model.Route;
import cn.qaii.wifibus.busquery.util.Search;
import cn.qaii.wifibus.common.http.request.LiveRequest;
import cn.qaii.wifibus.common.view.ActivityWithOptions;
import cn.qaii.wifibus.frame.constant.LHandlerMsg;

/**
 * ������ѯ��������·
 *
 */

public class BusQueryActivity extends ActivityWithOptions implements Search.SearchActionListener {

	private LinearLayout searchingDialog,notFound;
	private TextView isLoading;
	private ListView routesListView;
	private Search searchContext;
	private List<Route> routes = null;
	private int routeId;

	private Handler uiChangeHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case LHandlerMsg.MSG_GET_ROUTES:
				routes = (List<Route>) msg.obj;
				if (!searchContext.getSearchText().equals("")) {//������Ϊ�գ��������пؼ�
					if (routes.isEmpty()) {//û������ƥ����·
						notFound.setVisibility(View.VISIBLE);
						routesListView.setVisibility(View.INVISIBLE);
					} else {//��ʾƥ����·�б�
						notFound.setVisibility(View.INVISIBLE);
						RouteAdapter ra = new RouteAdapter(routes, BusQueryActivity.this);
						routesListView.setAdapter(ra);
						routesListView.setVisibility(View.VISIBLE);
						routesListView.invalidate();
						routesListView.requestLayout();
						routesListView.forceLayout();
					}
				}else{
					routesListView.setVisibility(View.INVISIBLE);
					notFound.setVisibility(View.INVISIBLE);
				}
				searchingDialog.setVisibility(View.INVISIBLE);
				isLoading.setVisibility(View.INVISIBLE);
				break;

			case LHandlerMsg.MSG_GET_STATIONS:
				String result = (String) msg.obj;
				Intent intent = new Intent(BusQueryActivity.this, StationsActivity.class);
				intent.putExtra("route_info", result);
				intent.putExtra("route_id", routeId);
				startActivity(intent);
				break;
			default:
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bus_query);

		searchingDialog = ((LinearLayout) findViewById(R.id.myrooms_contacts_searching));
		searchingDialog.setVisibility(View.INVISIBLE);
		notFound = ((LinearLayout) findViewById(R.id.route_empty));
		notFound.setVisibility(View.GONE);
		isLoading = (TextView) findViewById(R.id.isdowning);
		isLoading.setVisibility(View.INVISIBLE);
		routesListView = ((ListView) findViewById(R.id.myrooms_contacts_list));
		
		/**ʵ���������ܣ���������ؿؼ��ı仯*/
		searchContext = new Search(BusQueryActivity.this, BusQueryActivity.this, R.id.searchField, R.id.searchMagnifyIcon, R.id.searchClearIcon,
				uiChangeHandler);

		routesListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				searchContext.setSearchTextFocus(false);
				new LiveRequest(BusQueryActivity.this, uiChangeHandler).getStations(routes.get(arg2).getId());//����ѡ����·����ϸ��Ϣ
				routeId = routes.get(arg2).getId();
			}
		});

		super.setBackActivity(true);//ʹ�ܺ��˰�ť
		super.onCreatePost();//��ʼ��ActionBar
		this.actionBar.setTitle(R.string.bus_query);//���ñ���
	}

	/** ����ʧ��ʱ����*/
	@Override
	public void searchFailed(int paramInt) {
		String str = getString(paramInt);
		if (str != null) {
			showMessage(str);
			return;
		}
	}

	/** ����״̬�����仯ʱ����*/
	@Override
	public void searchStateChange(boolean paramBoolean) {
		if (paramBoolean) {
			searchingDialog.setVisibility(View.VISIBLE);
			isLoading.setVisibility(View.VISIBLE);
			return;
		}
		searchingDialog.setVisibility(View.INVISIBLE);
		isLoading.setVisibility(View.INVISIBLE);
		routesListView.setVisibility(View.INVISIBLE);
		notFound.setVisibility(View.INVISIBLE);
	}

	/** �������ݷ����仯ʱ����*/
	@Override
	public void searchTextChanged(boolean paramBoolean) {
		if (!paramBoolean)
			return;
		if (searchContext.getSearchText().length() > 0) {
			notFound.setVisibility(View.INVISIBLE);
			routesListView.setVisibility(View.INVISIBLE);
			searchingDialog.setVisibility(View.VISIBLE);
			isLoading.setVisibility(View.VISIBLE);
		}
	}
}
