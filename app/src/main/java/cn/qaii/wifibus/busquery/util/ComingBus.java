package cn.qaii.wifibus.busquery.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.qaii.wifibus.busquery.model.Bus;
import cn.qaii.wifibus.busquery.model.Station;
/**
 * �õ�������վ��Ϣ
 * 
 * @param stations ��վ��Ϣ�б�
 * @param buses ������վ������������Ϣ
 * @return ������վ������Ϣ�б�
 */
public class ComingBus {
	
	public static List<Bus> getComingBus(List<Station> stations,List<Bus> buses,int curSort){
		for(int i=0;i<buses.size();i++){
			for(int j=0;j<stations.size();j++){
				if(stations.get(j).getId()==Integer.parseInt(buses.get(i).getId())){
					buses.get(i).setDistance(curSort-stations.get(j).getSort());//ѡ�г�վ�೵����ǰ��վ��վ����
					buses.get(i).setName(stations.get(j).getName());//��ǰ��վ�ĳ�վ����
					SimpleDateFormat sdf =   new SimpleDateFormat( "HH:mm:ss" );
					try {
						Date date=sdf.parse(buses.get(i).getTime());
						Date curDate=new Date();
						long minTime=curDate.getMinutes()-date.getMinutes();
						if(curDate.getHours()-date.getHours()>0)
							minTime+=60;
						String timeStr=String.valueOf(minTime);
						buses.get(i).setArrTime(timeStr);//��ǰʱ���뵽վʱ���ʱ���
						
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return buses;
	}
}
