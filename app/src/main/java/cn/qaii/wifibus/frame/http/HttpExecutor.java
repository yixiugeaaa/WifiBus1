package cn.qaii.wifibus.frame.http;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;

import cn.qaii.wifibus.frame.constant.LCommands;
import cn.qaii.wifibus.frame.constant.LConstants;
import cn.qaii.wifibus.frame.constant.LContext;
import cn.qaii.wifibus.frame.util.LLogger;

import com.google.gson.GsonBuilder;

/**
 * ���������
 * @author larry
 *
 */
public class HttpExecutor {
	
	private static HttpClient httpClient;
	
	private static final String COMMAND = "CMDId";
	private static final String JSON = "JSon";
	private static final String TOKEN = "Token";
	private static final String MD5 = "MD5";
	private static final String MD5_VALUE = "";//MD5����ֵ��Ŀǰû���õ�����Ĭ�Ͽ��ַ���
	
	/**
	 * ��ʼ��HttpExecutor,����Ӧ��ʱ��ʼ��
	 */
	public static void init() {
        HttpParams params =new BasicHttpParams();
        /* �����ӳ���ȡ���ӵĳ�ʱʱ�� */ 
        ConnManagerParams.setTimeout(params, 50000);
        /* ���ӳ�ʱ */ 
        HttpConnectionParams.setConnectionTimeout(params, 50000); 
        /* ����ʱ */
        HttpConnectionParams.setSoTimeout(params, 100000);
        
        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));//////////////////////////////////////////////////////////////

        ThreadSafeClientConnManager cm=new ThreadSafeClientConnManager(params, schemeRegistry);
        httpClient = new DefaultHttpClient(cm,params);
	}
	
	/**
	 * ����POST����
	 * @param command
	 * @param param
	 * @return
	 */
	public static String requestPost(String command, Map<String, Object> param) {
		InputStream content = null;
		String requestJson = new GsonBuilder().disableHtmlEscaping().create().toJson(param);
		try {
			HttpPost request = new HttpPost(LConstants.BASE_URL);
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			
			LLogger.e("��������Ϊ �� " + requestJson + " \n " + "�������� �� " + command + " : " + LContext.TOKEN + " : " + MD5_VALUE + " : " + LConstants.BASE_URL);
			params.add(new BasicNameValuePair(COMMAND, command));
			params.add(new BasicNameValuePair(JSON, requestJson));
			params.add(new BasicNameValuePair(TOKEN, LContext.TOKEN));
			params.add(new BasicNameValuePair(MD5, MD5_VALUE));
			
			request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			long start = System.currentTimeMillis();
			if(httpClient == null){
				init();
			}
			HttpResponse response = httpClient.execute(request);
			LLogger.e("��������[" + command + "]��ʱ:" + (System.currentTimeMillis() - start));
			if (response.getStatusLine().getStatusCode() == 200) {
				content = response.getEntity().getContent();
				return getResponseData(content);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				content.close();
			} catch (Exception e) {}
		}
		return null;
	}
	
	
	/**
	 * ����GET����
	 * @param command
	 * @param param
	 * @return
	 */

	public static String requestGet(String command, Map<String, Object> param) {
		InputStream content = null;
		String urlStr=null;
		if(command.equals(LCommands.GET_FLOW)){
			urlStr= LConstants.QUERY_FLOW_URL+param.get("user_id");
		}else if(command.equals(LCommands.GET_ROUTES)){
			urlStr= LConstants.GET_ROUTES_URL+param.get("key");
		}else if(command.equals(LCommands.GET_STATIONS)){
			urlStr= LConstants.GET_STATIONS_URL+param.get("route_id");
		}else if(command.equals(LCommands.GET_BUS_COMING)){
			urlStr= LConstants.GET_BUS_COMING_URL+param.get("route_id")+"/sid/"+param.get("station_id")+"/num/"+param.get("num");
		}
			
		
		HttpGet hg = new HttpGet(urlStr);
		try {
			if(httpClient == null){
				init();
			}
			long start = System.currentTimeMillis();
			HttpResponse response = httpClient.execute(hg);
			LLogger.e("��������[" + command + "]��ʱ:" + (System.currentTimeMillis() - start));
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				content = response.getEntity().getContent();
				return getResponseData(content);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				content.close();
			} catch (Exception e) {}
		}
		return null;
	}
	
	
	
	
	
	/**
	 * ��ȡ����ֵ����
	 * @param content
	 * @return
	 * @throws IOException
	 */
	private static String getResponseData(InputStream content) throws IOException {
		//��ȡ����ֵ
		byte[] data = new byte[1024 * 8];
		StringBuffer strBuf = new StringBuffer();
		
		int count = 0;
		while((count = content.read(data)) != -1) {
			strBuf.append(new String(data, 0, count));
		}
		return strBuf.toString();
	}

}
