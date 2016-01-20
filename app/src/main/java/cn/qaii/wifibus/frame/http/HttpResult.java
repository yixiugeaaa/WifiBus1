package cn.qaii.wifibus.frame.http;

public class HttpResult {
	
	public static final String RETURN_CODE = "Code";
	public static final String RETURN_DES = "Des";
	public static final String RETURN_TYPE = "Type";
	public static final String RETURN_DATA = "Data";
	
	//��ҵ���߼�������Ӧ��С��0���ɹ�Ϊ0��ϵͳҵ���߼����������0
	public static final int RESULT_CODE_SUCCESS = 0;//����ɹ�
	public static final int RESULT_CODE_SERVER_EXCEPTION = -1;//�������쳣
	public static final int RESULT_CODE_UNKNOW_EXCEPTION = -2;//��������ֵ���쳣
	public static final int RESULT_CODE_CONNECTION_EXCEPTION = -999;//�����쳣
	
	public static final String RESULT_CODE_SERVER_EXCEPTION_VALUE = "����������ֵ�쳣";//�������쳣����
	public static final String RESULT_CODE_UNKNOW_EXCEPTION_VALUE = "��������ֵʱ�����쳣";//��������ֵ���쳣����
	public static final String RESULT_CODE_CONNECTION_EXCEPTION_VALUE = "���Ӳ������������������쳣�������������û�����";//�����쳣����
	
	private boolean isNewInterf;//�ж��ǲ����½ӿ�
	
	private boolean isHttpSuccess;//�ж������Ƿ�ɹ�

	private int resultCode;
	
	private String resultMessage;
	
	private String resultData;

	public boolean isNewInterf() {
		return isNewInterf;
	}

	public void setNewInterf(boolean isNewInterf) {
		this.isNewInterf = isNewInterf;
	}

	public boolean isHttpSuccess() {
		return isHttpSuccess;
	}

	public void setHttpSuccess(boolean isHttpSuccess) {
		this.isHttpSuccess = isHttpSuccess;
	}
	
	public int getResultCode() {
		return resultCode;
	}

	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMessage() {
		return resultMessage;
	}

	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}

	public String getResultData() {
		return resultData;
	}

	public void setResultData(String resultData) {
		this.resultData = resultData;
	}

	@Override
	public String toString() {
		return "HttpResult [isNewInterf=" + isNewInterf + ", isHttpSuccess="
				+ isHttpSuccess + ", resultCode=" + resultCode
				+ ", resultMessage=" + resultMessage + ", resultData="
				+ resultData + "]";
	}
	
}