package cn.qaii.wifibus.busquery.model;
/**
 * ��·��Ϣģ����
 * 
 */
public class Route {

	public int id = 0;//��·ID

	public String name = null;//��·����

	public String start_station = null;//ʼ��վ

	public String end_station = null;//�յ�վ

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStart() {
		return this.start_station;
	}

	public void setStart(String start) {
		this.start_station = start;
	}

	public String getEnd() {
		return this.end_station;
	}

	public void setEnd(String end) {
		this.end_station = end;
	}

}
