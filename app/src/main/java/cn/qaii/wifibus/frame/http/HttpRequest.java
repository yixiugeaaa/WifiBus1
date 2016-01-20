package cn.qaii.wifibus.frame.http;

import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import cn.qaii.wifibus.frame.util.LLogger;
import cn.qaii.wifibus.frame.util.StringUtil;

/**
 * �������������
 * @author larry
 *
 */
public abstract class HttpRequest extends AsyncTask<Object, Object, HttpResult> {
	
	public Map<String, Object> mParam;
	public String mCommand;
	
	@Override
	protected HttpResult doInBackground(Object... params) {
		initParams();
		if(isCancelled()) {
			return null;
		}
		HttpResult result = request(mParam);
		if(result.isHttpSuccess() && result.getResultCode() >= 0) {
			onBackgroundSuccess(result);
		}
		return result;
	}
	
	private HttpResult request(final Map<String, Object> param) {
		String resultStr = HttpExecutor.requestGet(mCommand, param);
		
		if(!StringUtil.isNull(resultStr)) {
			LLogger.e("���󷵻�ֵ���ȣ�" + resultStr.length() + "\n" + resultStr);
		}
		
		//HttpResult result = parseResult(resultStr);//////////////////////////////////////////////////////////////////////////////////////////////////////////
		HttpResult result = new HttpResult();
		result.setHttpSuccess(true);
		result.setResultData(resultStr);
		return result;
	}

	@Override
	protected void onPostExecute(HttpResult result) {
		if(result == null) {
			//��;ȡ��
		} else if(result.isHttpSuccess() && result.getResultCode() >= 0) {
			onRequestSuccess(result);
		} else {
			onRequestFail(result);
		}
	}

	public HttpResult parseResult(String resultStr) {
		
		HttpResult result = new HttpResult();
		
		if(StringUtil.isNull(resultStr)) {
			result.setResultCode(HttpResult.RESULT_CODE_CONNECTION_EXCEPTION);
			result.setResultMessage(HttpResult.RESULT_CODE_CONNECTION_EXCEPTION_VALUE);
			result.setHttpSuccess(false);
			return result;
		}
		result = parser(resultStr);
		
		return result;
	}
	
	private HttpResult parser(String str){
		HttpResult result = new HttpResult();
		try {
			if(str.substring(0, 1).equals("[")){//�Ͻӿ�
				JSONArray array = new JSONArray(str);
				result.setResultData(array.toString());
				result.setHttpSuccess(true);
				return result;
			}
			JSONObject object = new JSONObject(str);
			if(object.has(HttpResult.RETURN_CODE)){
				result.setNewInterf(true);//�½ӿ�
				int returnCode = object.getInt(HttpResult.RETURN_CODE);
				if(returnCode == 0){
					result.setResultCode(returnCode);
					result.setResultMessage(object.getString(HttpResult.RETURN_DES));
					result.setResultData(object.getString(HttpResult.RETURN_DATA));
					result.setHttpSuccess(true);
				}else{
					result.setResultCode(returnCode);
					result.setResultMessage(HttpResult.RESULT_CODE_SERVER_EXCEPTION_VALUE);
					result.setHttpSuccess(false);
				}
			}else{
				result.setNewInterf(false);//�Ͻӿ�
				//�Ͻӿ��ݲ�����
			}
		} catch (JSONException e) {
//			LLogger.e("��������ֵʱ�����쳣" + e);
			result.setResultCode(HttpResult.RESULT_CODE_UNKNOW_EXCEPTION);
			result.setResultMessage(HttpResult.RESULT_CODE_UNKNOW_EXCEPTION_VALUE);
			result.setHttpSuccess(false);
		}
		
		return result;
	}
	
	protected void onBackgroundSuccess(HttpResult result) {}
	
	/**
	 * ����ʧ�ܣ��������Ӳ��ϣ���������˷����쳣���ֻ��Ƿ���δ֪�쳣
	 * @param result
	 */
	protected abstract void onRequestFail(HttpResult result);
	
	protected abstract void initParams();
	
	/**
	 * ��������ɹ�ʱ�Ļص�
	 * ע�⣬��������������ɹ��������������ҵ���߼��ɹ���
	 * �����ҵ���߼�����������Ҫ��result.getResultCode()��ֵ���ж�
	 * @param result
	 */
	protected abstract void onRequestSuccess(HttpResult result);
}