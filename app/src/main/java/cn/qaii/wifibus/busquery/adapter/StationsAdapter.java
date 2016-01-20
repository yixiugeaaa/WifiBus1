package cn.qaii.wifibus.busquery.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.List;

import cn.qaii.wifibus.R;
import cn.qaii.wifibus.busquery.activity.StationsActivity;
import cn.qaii.wifibus.busquery.model.Bus;
import cn.qaii.wifibus.busquery.model.Station;
/**
 * expandableListView������
 *
 */
public class StationsAdapter extends BaseExpandableListAdapter {
	private Context context;
	private List<Station> group;

	public StationsAdapter(Context context, List<Station> group) {
		this.context = context;
		this.group = group;
	}

	@Override
	public int getGroupCount() {
		return group.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return StationsActivity.buses.size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return group.get(groupPosition).getName();
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return StationsActivity.buses.get(childPosition).getName();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}
	
	/**
	 * ��ʾ��group
	 */
	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.group_list_item, null);
			holder = new ViewHolder();
			holder.textView1 = (TextView) convertView
					.findViewById(R.id.textView_stations_name);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.textView1.setText(String.valueOf(groupPosition + 1) + " " + group.get(groupPosition).getName());
		holder.textView1.setTextSize(25);
		return convertView;
	}
	
	/**
	 * ��ʾ��child
	 */
	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.child_list_item, null);
			holder = new ViewHolder();
			holder.textView1 = (TextView) convertView
					.findViewById(R.id.textView_several_stations);
			holder.textView2 = (TextView) convertView
					.findViewById(R.id.textView_which_station);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		
		if (!StationsActivity.buses.isEmpty()) {
			Bus bus = StationsActivity.buses.get(childPosition);
			holder.textView1.setText("����" + bus.getDistance() + "վ");
			if(!bus.getArrTime().equals("0"))
			holder.textView2.setText(bus.getName() + " ��" + bus.getArrTime() + "����ǰ���");
			else
				holder.textView2.setText(bus.getName() + " ���ո� ���");
			String date=bus.getTime();
		}else{
			holder.textView1.setText("");
			holder.textView2.setText("");
		}
		
		return convertView;
	}

	class ViewHolder {
		TextView textView1;
		TextView textView2;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return false;
	}

}
