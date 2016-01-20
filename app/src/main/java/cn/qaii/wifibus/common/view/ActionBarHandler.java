package cn.qaii.wifibus.common.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.qaii.wifibus.R;
import cn.qaii.wifibus.common.activity.AboutActivity;
/**
 * ������������
 *
 */
public class ActionBarHandler implements OnClickListener {

	private LinearLayout optionsButton;
	private Activity activity;
	private LinearLayout optionsLayout;
	protected boolean savedInstanceSet;
	private LinearLayout backButton;
	private ImageView backIcon;
	private RelativeLayout optionsBackground;
	//OnOptionsVisible childOnOptionsVisible = null;
	private boolean backActive = false;
	//private LogoutPrompt logoutDialog;
	OnClickListener childOnClickListener = null;
	private LinearLayout titleBackground;
	private String titleString = null;
	private TextView titleText;
	private int enterAnim = R.anim.fadein;
	private int exitAnim = R.anim.fadeout;
	private boolean inviteActive = false;
	private boolean useAnimation = false;
	OnOptionsVisible childOnOptionsVisible = null;
	private LinearLayout coverallBackground;
	
	public ActionBarHandler(Activity paramActivity) {
		this.activity = paramActivity;
		paramActivity.requestWindowFeature(1);
	}

	public ActionBarHandler(Activity paramActivity, Bundle paramBundle) {
		this(paramActivity);
		if (paramBundle != null) {
			this.savedInstanceSet = true;
		}
	}
	
	/** �����ؼ��������˵����ػ���ϵͳ�����̨ */
	private boolean handleDisplayed(int paramInt) {
		if ((paramInt != R.id.menubar_options_button) && (optionsVisible()))
			hideOptions();
		return true;
	}
	
	/** ���������˵� */
	private void hideOptions() {
		if (this.coverallBackground != null)
			this.coverallBackground.setVisibility(8);
		if (this.optionsBackground != null)
			this.optionsBackground.setVisibility(8);
		if (this.optionsButton != null)
			this.optionsButton.setSelected(false);

		if (this.childOnOptionsVisible != null)
			this.childOnOptionsVisible.setViewTouch(true);
		if (this.optionsLayout != null)
			this.optionsLayout.setVisibility(8);
	}

	private boolean optionsVisible() {
		if (this.optionsBackground != null) {
			if (this.optionsBackground.getVisibility() == 0)
				return true;
			else
				return false;
		} else {
			return true;
		}
	}

	/** ��ʾ�����˵� */
	private void showOptions() {
		if (this.coverallBackground != null)
			this.coverallBackground.setVisibility(0);
		if (this.optionsBackground != null)
			this.optionsBackground.setVisibility(0);
		if (this.optionsButton != null)
			this.optionsButton.setSelected(true);
		if (this.childOnOptionsVisible != null)
			this.childOnOptionsVisible.setViewTouch(false);
		if (this.optionsLayout != null)
			this.optionsLayout.setVisibility(0);
	}

	public void clearAnimations() {
		this.useAnimation = false;
	}

	public void closeOptions() {
		hideOptions();
	}
	
	/** ����ָ���Ĳ˵��� */
	public void hideMenuOption(int paramInt) {
		LinearLayout localLinearLayout = (LinearLayout) this.activity.findViewById(paramInt);
		if (localLinearLayout != null)
			localLinearLayout.setVisibility(8);
	}

	public boolean onBackHandler() {
		return handleDisplayed(-1);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.menubar_options_settings:
			if (optionsVisible())
			hideOptions();
			/*
			 * Intent localIntent3 = new Intent(this.activity, Settings.class);
			 * if (this.showSecretSettings) localIntent3.putExtra("ARGSECRET",
			 * true); this.activity.startActivity(localIntent3);
			 * this.activity.overridePendingTransition(2130968590, 0);
			 */
			break;
		case R.id.icon_back:
			if (this.backActive)
				this.activity.finish();
			break;
		case R.id.menubar_options_about:
			if (optionsVisible())
				hideOptions();
			Intent localIntent4 = new Intent(this.activity, AboutActivity.class);
			this.activity.startActivity(localIntent4);
			this.activity.overridePendingTransition(R.anim.zoom_enter, 0);
			
			break;
		case R.id.menubar_options_logout:
			if (optionsVisible())
			hideOptions();
			//this.logoutDialog.show();
			break;
		case R.id.menubar_emailinvite_button:
			Intent sendIntent = new Intent();
			sendIntent.setAction(Intent.ACTION_SEND);
			sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
			sendIntent.setType("text/plain");
			this.activity.startActivity(Intent.createChooser(sendIntent, "������������"));
			break;
		default:
			break;
		}
		
		if (v.getId()==R.id.menubar_options_button) {
			if (optionsVisible())
				hideOptions();
			else
				showOptions(); 
		}else{
			hideOptions();
		}
	}
	
	/** �����ʼ�� */
	public void onCreatePost() {
		this.coverallBackground = ((LinearLayout) this.activity.findViewById(R.id.menubar_coverall));
		this.optionsBackground = ((RelativeLayout) this.activity.findViewById(R.id.menubar_options_background));
		this.optionsLayout = ((LinearLayout) this.activity.findViewById(R.id.menubar_options));
		if (this.optionsBackground != null) {
			this.optionsBackground.setVisibility(8);
			this.optionsBackground.setOnClickListener(this);
			this.optionsButton = ((LinearLayout) this.activity.findViewById(R.id.menubar_options_button));
			if (this.optionsButton != null)
				this.optionsButton.setOnClickListener(this);
			LinearLayout localLinearLayout1 = (LinearLayout) this.activity
					.findViewById(R.id.menubar_emailinvite_button);// �����ʼ���ť
			if (localLinearLayout1 != null)
				localLinearLayout1.setOnClickListener(this);
			inviteActive=true;
			setInviteActive(this.inviteActive);

			LinearLayout localLinearLayout2 = (LinearLayout) this.activity.findViewById(R.id.menubar_options_logout);
			if (localLinearLayout2 != null)
				localLinearLayout2.setOnClickListener(this);
			LinearLayout localLinearLayout3 = (LinearLayout) this.activity.findViewById(R.id.menubar_options_about);
			if (localLinearLayout3 != null)
				localLinearLayout3.setOnClickListener(this);
			LinearLayout localLinearLayout4 = (LinearLayout) this.activity.findViewById(R.id.menubar_options_settings);
			if (localLinearLayout4 != null)
				localLinearLayout4.setOnClickListener(this);

			this.backButton = ((LinearLayout) this.activity.findViewById(R.id.icon_back));
			this.backIcon = ((ImageView) this.activity.findViewById(R.id.menubar_back_button));
			setBackActivity(this.backActive);
			this.titleBackground = ((LinearLayout) this.activity.findViewById(R.id.title));
			if (this.titleBackground != null)
				this.titleBackground.setOnClickListener(this);
			this.titleText = ((TextView) this.activity.findViewById(R.id.title_text));

			if (this.optionsLayout != null)
				this.optionsLayout.setVisibility(8);
		}
	}

	/** �����ء������˵�����������*/
	public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent) {
		if (paramInt == KeyEvent.KEYCODE_MENU) {
			if (optionsVisible()) {
				hideOptions();
			} else {
				showOptions();
			}
			return true;
		} else if (paramInt == KeyEvent.KEYCODE_BACK) {
			if (optionsVisible()) {
				hideOptions();
				return true;
			} else if (this.backActive) {
				this.activity.finish();
				if (this.useAnimation)
					this.activity.overridePendingTransition(this.enterAnim, this.exitAnim);
				return true;
			} else
				return false;
		} else
			return false;
	}

	public void onPause() {
		/*if ((this.logoutDialog != null) && (this.logoutDialog.isVisible)) {
			this.logoutVisibleAtPause = true;
			this.logoutDialog.hide();
		}*/
	}

	public void onResume() {
		
	}

	public void onSaveInstanceState(Bundle paramBundle) {
		
	}

	public boolean onTouchEvent(MotionEvent paramMotionEvent) {
		if (optionsVisible()) {
			hideOptions();
			return true;
		} else
			return false;
	}

	public void setActivity(Activity paramActivity) {
		this.activity = paramActivity;
	}

	/** ���ö�̬�л�Ч�� */
	public void setAnimations(int paramInt1, int paramInt2) {
		this.enterAnim = paramInt1;
		this.exitAnim = paramInt2;
		this.useAnimation = true;
	}

	/** �Ƿ�������˵���һ��Activity*/
	public void setBackActivity(boolean paramBoolean) {
		this.backActive = paramBoolean;
		if ((this.backButton != null) && (this.backIcon != null)) {
			this.backButton.setFocusable(paramBoolean);
			if (paramBoolean) {
				this.backButton.setOnClickListener(this);
				this.backIcon.setVisibility(View.VISIBLE);
				this.backButton.setBackgroundResource(R.drawable.custom_menubar_button_background);
			} else {
				this.backButton.setEnabled(false);
				this.backIcon.setVisibility(View.GONE);
				// this.backButton.setBackgroundResource(17170445);
			}
		}
	}

	/** �Ƿ�ʹ�á����롱���� */
	public void setInviteActive(boolean paramBoolean) {
		this.inviteActive = paramBoolean;
		LinearLayout localLinearLayout = (LinearLayout) this.activity.findViewById(R.id.menubar_emailinvite_layout);
		if (localLinearLayout != null) {
			if (paramBoolean)
				localLinearLayout.setVisibility(0);
			else
				localLinearLayout.setVisibility(8);
		}
	}

	public void setOnClickListener(OnClickListener paramOnClickListener) {
		this.childOnClickListener = paramOnClickListener;
	}

	public void setOnOptionsVisible(OnOptionsVisible paramOnOptionsVisible) {
		this.childOnOptionsVisible = paramOnOptionsVisible;
	}

	/** ����Actionbar�ı��� */
	public void setTitle(int paramInt) {
		if (this.activity != null) {
			this.titleString = this.activity.getString(paramInt);
			if ((this.titleString != null) && (this.titleText != null))
				this.titleText.setText(this.titleString);
		}
	}

	public void setTitle(String paramString) {
		this.titleString = paramString;
		if ((this.titleString != null) && (this.titleText != null))
			this.titleText.setText(this.titleString);
	}

	/** ��ʾע���Ի��� */
	public void showLogoutMessage() {
		//this.logoutDialog.show();
	}

	static abstract interface OnOptionsVisible {
		public abstract void setViewTouch(boolean paramBoolean);
	}

	public static abstract interface OnClickListener {
		public abstract boolean onClick(int paramInt);
	}

}
