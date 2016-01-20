package cn.qaii.wifibus.busquery.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cn.qaii.wifibus.R;
import cn.qaii.wifibus.busquery.model.Route;

public class RouteAdapter extends BaseAdapter {
	public List<Route> mList;
	private Context mContext;

	public RouteAdapter(List<Route> list, Context mContext) {
		super();
		this.mList = list;
		this.mContext = mContext;
	}
	
	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup viewGroup) {
		ViewHold viewHold = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.contacts_endoflist, null);
			viewHold = new ViewHold();
			viewHold.tvRoutes = (TextView) convertView.findViewById(R.id.text_bus_routes);
			viewHold.tvStation = (TextView) convertView.findViewById(R.id.text_bus_station);
			convertView.setTag(viewHold);
		} else {
			viewHold = (ViewHold) convertView.getTag();
		}
		try {
			Route info = mList.get(position);
			viewHold.tvRoutes.setText(info.getName());
			viewHold.tvStation.setText(info.getStart()+"-"+info.getEnd());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return convertView;
	}

	static class ViewHold {
		TextView tvRoutes;
		TextView tvStation;
	}

}
