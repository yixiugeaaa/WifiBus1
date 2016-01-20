package cn.qaii.wifibus.busquery.model;
/**
 * ��վ��Ϣģ����
 * 
 */
public class Station {

	public int id = 0;//��վid

	public String name = null;//��վ����

	public int sort;//��վ�ڵ�ǰ��·�е�˳����

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

	public int getSort() {
		return this.sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}
}
