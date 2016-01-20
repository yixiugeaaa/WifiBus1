package cn.qaii.wifibus.common.view;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import cn.qaii.wifibus.R;
/**
 * ������������
 *
 */
public abstract class ActivityWithOptions extends FragmentActivity implements View.OnClickListener {
	public ActionBarHandler actionBar;

	protected boolean doSuperOnSaveInstance = true;
	private String messageBoxMessage = null;
	private String messageBoxTitle = null;

	public void closeOptions() {
		if (this.actionBar != null)
			this.actionBar.closeOptions();
	}

	/** �����ƶ��˵��� */
	protected void hideMenuOption(int paramInt) {
		if (this.actionBar != null)
			this.actionBar.hideMenuOption(paramInt);
	}

	public void onClick(View paramView) {
		if (this.actionBar != null)
			this.actionBar.onClick(paramView);
	}

	protected void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		this.actionBar = new ActionBarHandler(this, paramBundle);
		if (paramBundle != null) {
			this.messageBoxTitle = paramBundle.getString("MsgBoxTitle");
			this.messageBoxMessage = paramBundle.getString("MsgBoxMessage");
		}
		requestWindowFeature(1);
	}

	public void onCreatePost() {
		if (this.actionBar != null)
			this.actionBar.onCreatePost();
	}

	public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent) {
		if ((this.actionBar != null)
				&& (this.actionBar.onKeyDown(paramInt, paramKeyEvent)))
			return true;
		return super.onKeyDown(paramInt, paramKeyEvent);
	}

	protected void onPause() {
		super.onPause();
		if (this.actionBar != null)
			this.actionBar.onPause();
	}

	protected void onResume() {
		super.onResume();
		if (this.actionBar != null)
			this.actionBar.onResume();
	}

	public void onSaveInstanceState(Bundle paramBundle) {
		
	}

	public boolean onTouchEvent(MotionEvent paramMotionEvent) {
		if (this.actionBar != null)
			return this.actionBar.onTouchEvent(paramMotionEvent);
		else
			return false;
	}

	public void setBackActivity(boolean paramBoolean) {
		if (this.actionBar != null)
			this.actionBar.setBackActivity(paramBoolean);
	}

	public void showLogoutMessage() {
		if (this.actionBar != null)
			this.actionBar.showLogoutMessage();
	}

	protected void showMessage(String paramString) {
		showMessage(getString(R.string.app_name), paramString);
	}

	protected void showMessage(String paramString1, String paramString2) {
		this.messageBoxTitle = paramString1;
		this.messageBoxMessage = paramString2;
	}

}
