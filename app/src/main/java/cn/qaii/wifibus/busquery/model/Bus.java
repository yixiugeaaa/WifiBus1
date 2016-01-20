package cn.qaii.wifibus.busquery.model;
/**
 * ������Ϣģ����
 * 
 */
public class Bus {
	
	public String no = null;//���ƺ�

	public String station_id = null;//��ǰ��վվ��

	public String time = null;//��վʱ��
	
	public int distance;//��ǰѡ�г�վ�͵�ǰ��վ����վ����
	
	public String name;//��ǰ��վ��վ����
	
	public String arrTime;//��վʱ���뵱ǰʱ��ļ��

	public String getNo() {
		return this.no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getId() {
		return this.station_id;
	}

	public void setId(String id) {
		this.station_id = id;
	}

	public String getTime() {
		return this.time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
	public int getDistance() {
		return this.distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void setArrTime(String arrTime){
		this.arrTime=arrTime;
	}
	
	public String getArrTime(){
		return this.arrTime;
	}

}
