package cn.qaii.wifibus.busquery.util;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import cn.qaii.wifibus.R;
import cn.qaii.wifibus.common.http.request.LiveRequest;
import cn.qaii.wifibus.frame.util.StringUtil;

/**
 * ������������
 *
 */

public class Search {
	private Activity mParentActivity;
	private SearchActionListener searchActionListener = null;
	private EditText searchField;
	private String searchText;
	private ImageView srchDrawable;
	private boolean stateSearching;
	private LinearLayout xDrawable;
	private Handler handler;

	public Search(Activity paramActivity, SearchActionListener paramSearchActionListener, int paramInt1, int paramInt2, int paramInt3,
			Handler handler) {

		this.handler = handler;
		this.mParentActivity = paramActivity;
		this.searchActionListener = paramSearchActionListener;
		this.stateSearching = false;
		this.searchField = ((EditText) this.mParentActivity.findViewById(paramInt1));
		this.xDrawable = ((LinearLayout) this.mParentActivity.findViewById(paramInt3));
		this.srchDrawable = ((ImageView) this.mParentActivity.findViewById(paramInt2));
		this.xDrawable.setOnClickListener(new View.OnClickListener() {
			public void onClick(View paramView) {
				Search.this.searchField.setText("");
				Search.this.setupEditFieldItems();
				if (Search.this.stateSearching) {
					Search.this.stateSearching = false;
					Search.this.searchActionListener.searchStateChange(false);
				}
			}
		});
		setupEditFieldItems();
		this.searchField.setImeOptions(6);
		
		
		this.searchField.addTextChangedListener(new TextWatcher() {
			public void afterTextChanged(Editable paramEditable) {

			}

			public void beforeTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3) {
			}

			public void onTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3) {

				Search.this.setupEditFieldItems();
				Search.this.searchText = StringUtil.removeLeadingSpaces(Search.this.searchField.getText().toString());
				if (Search.this.searchText.length() == 0) {
					if (Search.this.stateSearching) {
						Search.this.stateSearching = false;
						Search.this.searchActionListener.searchStateChange(false);
					}
					Search.this.setupEditFieldItems();
					return;
				} else {
					ConnectivityManager localConnectivityManager = (ConnectivityManager) Search.this.mParentActivity.getSystemService("connectivity");
					if ((localConnectivityManager.getActiveNetworkInfo() == null) || (!localConnectivityManager.getActiveNetworkInfo().isAvailable())
							|| (!localConnectivityManager.getActiveNetworkInfo().isConnected())) {
						Search.this.searchActionListener.searchFailed(R.string.message_error_networkunavailable);//�����޷�ʹ�ã��ص�����ʧ�ܺ���
						return;
					}
					if (!Search.this.stateSearching) {
						Search.this.stateSearching = true;
						Search.this.searchActionListener.searchStateChange(true);
						Search.this.searchActionListener.searchTextChanged(false);
						setupSearchThread();
					} else {
						Search.this.searchActionListener.searchStateChange(true);
						Search.this.searchActionListener.searchTextChanged(true);
						setupSearchThread();
					}
				}
			}
		});
		
		/** ��������̵���ʾ������ */
		this.searchField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			public void onFocusChange(View paramView, boolean paramBoolean) {
				if (paramView == Search.this.searchField) {
					Search.this.setupEditFieldItems();
					if (paramBoolean)
						((InputMethodManager) Search.this.mParentActivity.getSystemService("input_method")).showSoftInput(Search.this.searchField, 2);
				} else {
					return;
				}
				((InputMethodManager) Search.this.mParentActivity.getSystemService("input_method")).hideSoftInputFromWindow(
						Search.this.searchField.getWindowToken(), 0);
			}
		});
	}

	/** �����첽�̣߳�������·�б� */
	private void setupSearchThread() {
		new Thread() {
			public void run() {
				if ((Search.this.searchText == null) || (Search.this.searchText.length() == 0))
					;
				else {
					new LiveRequest(mParentActivity, handler).getRoutes(searchText);//������·�б�
					Search.this.stateSearching = true;
				}
			}
		}.start();
	}

	/** ����������ؿؼ�����ʾ������ */
	private void setupEditFieldItems() {
		if (this.searchField.getText().toString().length() > 0) {
			this.searchField.setHint("");
			this.srchDrawable.setVisibility(8);
			this.xDrawable.setVisibility(0);
			return;
		}
		this.srchDrawable.setVisibility(0);
		this.xDrawable.setVisibility(8);
		this.searchField.setHint(R.string.bus_query_hint);
	}

	/** ��ȡ�����ı� */
	public String getSearchText() {
		return StringUtil.removeLeadingSpaces(this.searchField.getText().toString());
	}

	/** ����̶����������� */
	public void setSearchTextFocus(boolean paramBoolean) {
		if (this.searchField != null) {
			if (paramBoolean)
				this.searchField.requestFocus();
		} else
			return;
		this.searchField.clearFocus();
	}

	public static abstract interface SearchActionListener {

		public abstract void searchFailed(int paramInt);//����ʧ��ʱִ�лص�

		public abstract void searchStateChange(boolean paramBoolean);//����״̬�����仯ʱִ�лص�

		public abstract void searchTextChanged(boolean paramBoolean);//�������ݷ����仯ʱִ�лص�
	}
}