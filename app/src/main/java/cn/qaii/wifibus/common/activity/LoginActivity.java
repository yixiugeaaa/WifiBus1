package cn.qaii.wifibus.common.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import cn.qaii.wifibus.R;
import cn.qaii.wifibus.common.http.request.LiveRequest;
import cn.qaii.wifibus.frame.constant.LHandlerMsg;
/**
 * ��¼
 *
 */
public class LoginActivity extends Activity {

	private Button login_btn;
	private EditText tel_et;
	private TextView tips;
	private SharedPreferences loginInfo;// ����֤��Ϣ���浽����
	private boolean isLoginned = false;
	
	/**
	 *  ��ȡ�û�ʣ����������ת��¼
	 */
	private final Handler loginHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if(msg.what!=LHandlerMsg.MSG_GET_FLOW)
				return;
			super.handleMessage(msg);
			String result = (String) msg.obj;
			//UserInfo.flow = Integer.parseInt(result);
			Intent intent = new Intent(LoginActivity.this, MainActivity.class);
			intent.putExtra("flowNum", result);
			startActivity(intent);
			LoginActivity.this.finish();
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		login_btn = (Button) findViewById(R.id.button_login);
		login_btn.setEnabled(false);
		tel_et = (EditText) findViewById(R.id.edittext_tel);
		tips = (TextView) findViewById(R.id.textView_tips);
		loginInfo = getSharedPreferences("login", Context.MODE_PRIVATE);
		String telStr = loginInfo.getString("tel", null);// ȡ���ϴε�¼ʱ�����IP
		setTextVerificationListener(tel_et);

		/**���ݱ����Ƿ�洢���û���֤��Ϣ�����ÿռ���ʾ����*/
		if (telStr == null) {
			isLoginned = false;
			login_btn.setText(R.string.login_button_renzheng);
			tips.setText(R.string.login_tips_first);
		} else {
			isLoginned = true;
			login_btn.setText(R.string.login_button_surfing);
			tips.setText(R.string.login_tips_used);
			tel_et.setText(telStr);
			//UserInfo.TEL = telStr;
		}

		login_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (!isLoginned) {
					Editor edit = loginInfo.edit();
					edit.putString("tel", tel_et.getText().toString().trim());
					edit.commit();
				}
				new LiveRequest(LoginActivity.this, loginHandler).getFlow();//�����û�ʣ������
			}
		});

	}

	/**
	 *  ʹ�ܵ�¼��ť
	 */
	private void enableLoginButtonAfterInputVerification() {
		int i = tel_et.getText().toString().trim().length();
		if ((i == 11)) {
			login_btn.setEnabled(true);
			return;
		}
		login_btn.setEnabled(false);
	}

	/** 
	 * ��������У����Ƶ�¼��ť�������ť״̬ 
	 */
	private void setTextVerificationListener(final EditText et) {
		tel_et.addTextChangedListener(new TextWatcher() {
			public void afterTextChanged(Editable paramEditable) {
				enableLoginButtonAfterInputVerification();
			}

			public void beforeTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3) {

			}

			public void onTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3) {

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

}