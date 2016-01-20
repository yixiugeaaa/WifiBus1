package cn.qaii.wifibus.common.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.ab.activity.AbActivity;

import cn.qaii.wifibus.R;

/**
 * 导航页面 1s
 *
 */

public class NavigationActivity extends AbActivity {
	private Animation mFadeIn;
	private Animation mFadeInScale;
	private ImageView img_launcher;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setAbContentView(R.layout.activity_navigation);
		img_launcher =(ImageView)findViewById(R.id.imageView_launcher);
		init();
		setListener();
	}

	private void setListener() {

		mFadeIn.setAnimationListener(new Animation.AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				img_launcher.startAnimation(mFadeInScale);
			}
		});

		mFadeInScale.setAnimationListener(new Animation.AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				Intent intent = new Intent();
				intent.setClass(NavigationActivity.this, MainActivity.class);
				startActivity(intent);
				finish();
			}
		});

	}

	private void init() {
		initAnim();
		img_launcher.startAnimation(mFadeIn);
	}

	private void initAnim() {
		mFadeIn = AnimationUtils.loadAnimation(NavigationActivity.this,
				android.R.anim.fade_in);
		mFadeIn.setDuration(500);
		mFadeIn.setFillAfter(true);

		mFadeInScale = AnimationUtils.loadAnimation(NavigationActivity.this,
				R.anim.welcome_fade_in_scale);
		mFadeInScale.setDuration(800);
		mFadeInScale.setFillAfter(true);
	}







	/*private AsyncTask<Void, Void, Void> initTask;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(layout.activity_navigation);
		
		initTask = new AsyncTask<Void, Void, Void>() {
			@Override
			protected Void doInBackground(Void... params) {
				new InitTask().init();
				return null;
			}//�첽��ʱ�����ƿ�ͷͼƬ����ʾʱ��

			protected void onPostExecute(Void result) {
				super.onPostExecute(result);
				Intent intent = new Intent(NavigationActivity.this,
						LoginActivity.class);
				NavigationActivity.this.startActivity(intent);
				overridePendingTransition(android.R.anim.fade_in,
						android.R.anim.fade_out);
				NavigationActivity.this.finish();
			}

		};
		initTask.execute();
	}
	
	public class InitTask {
		*//**
		 * �ӳ�1s��Ҳ���������һЩ��ʼ������
		 *//*
		public void init() {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.navigation, menu);
		return true;
	}*/

}
