package com.ginray;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.view.View.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class DoodleJumpActivity extends Activity  implements OnClickListener  {

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	protected void onResume() {
		/**
		 * 设置为竖屏
		 */
		if(getRequestedOrientation()!= ActivityInfo.SCREEN_ORIENTATION_PORTRAIT){
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		}
		super.onResume();
	}

	private SharedPreferences mBaseSettings;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //强制全屏
        //首先去掉title,就是没有title 那一行，但是还不是全屏
        requestWindowFeature(Window.FEATURE_NO_TITLE);    
        // 禁止屏幕休眠
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        //去掉状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.main);
        
        //监听开始按钮
        Button startbutton=(Button) findViewById(R.id.start_game);
            startbutton.setOnClickListener(this);
        //监听游戏设置按钮
       	Button setutton = (Button) findViewById(R.id.set_game);
              setutton.setOnClickListener(this);
		//进入商店按钮
		Button showbutton = (Button) findViewById(R.id.show_store);
		showbutton .setOnClickListener(this);

		//监听退出按钮

    }
    
    public void onClick(View v) {
    	mBaseSettings =PreferenceManager
				.getDefaultSharedPreferences(this);
		Intent i = null;
		switch (v.getId()) {
		case R.id.start_game:
			//判断设置里面是否开启，提示，默认是开启的
			if (mBaseSettings.getBoolean(GameUi.PREFERENCE_KEY_SHOWTIPS,
					true)) {
				i = new Intent(this, IntroduceActivity.class);
				
			} else {
				i = new Intent(this, mainActivity.class);
			}
			break;

		case R.id.show_store:
			i=new Intent(this,CircleShowStore.class);
			//i = new Intent(this, ShowstoreActivity.class);
			break;

		case R.id.set_game:
			i = new Intent(this, SettingsActivity.class);
			break;



		}
		if (i != null) {
			startActivity(i);
			this.finish();
		}
	}
    
    
    
    
    
    
    
    
    
    
    
    
    
}