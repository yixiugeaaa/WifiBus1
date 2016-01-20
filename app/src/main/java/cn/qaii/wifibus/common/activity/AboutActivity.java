package cn.qaii.wifibus.common.activity;
/**
 * ����
 */
import android.os.Bundle;

import cn.qaii.wifibus.R.layout;
import cn.qaii.wifibus.R.string;
import cn.qaii.wifibus.common.view.ActivityWithOptions;

	public class AboutActivity extends ActivityWithOptions 
	{
	  public void onCreate(Bundle paramBundle) 
	  {
		  super.onCreate(paramBundle);
		  setContentView(layout.activity_about);
	      super.setBackActivity(true);
	      super.onCreatePost();
	      this.actionBar.setTitle(string.about_menu);
	  }
	}
