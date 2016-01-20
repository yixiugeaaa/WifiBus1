package cn.qaii.wifibus.common.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import cn.qaii.wifibus.R;
import cn.qaii.wifibus.busquery.activity.BusQueryActivity;
import cn.qaii.wifibus.common.adapter.MyGridAdapter;
import cn.qaii.wifibus.common.view.ActionBarHandler;
import cn.qaii.wifibus.common.view.ActivityWithOptions;
import cn.qaii.wifibus.common.view.MyGridView;
import cn.qaii.wifibus.localres.activity.LocalResActivity;

/**
 * @Description:���ܵ�����ҳ
 */

public class MainActivity extends ActivityWithOptions {
	private MyGridView gridview;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		super.onCreatePost();//��ʼ��actionbar
		
		initView();
		
		gridview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				MainActivity.super.hideMenuOption(1);
				switch(arg2){
				case(0):
					Intent localResIntent = new Intent(MainActivity.this,
							LocalResActivity.class);
					startActivity(localResIntent);//��ת�����������Դ
					break;
				case(1):
					Intent busQuery = new Intent(MainActivity.this,
							BusQueryActivity.class);
					startActivity(busQuery);//��ת������ѯ����
					break;
				}
			}
		});
	}

	/** ��������˵����ִ�������״̬����ϵͳ�����̨ */
	public void onBackPressed() {
		if (this.actionBar.onBackHandler())
			System.exit(0);
	}

	/** ��ʼ������*/
	private void initView() {
        
		gridview = (MyGridView) findViewById(R.id.gridview);
		gridview.setAdapter(new MyGridAdapter(this));
		
	}
}
