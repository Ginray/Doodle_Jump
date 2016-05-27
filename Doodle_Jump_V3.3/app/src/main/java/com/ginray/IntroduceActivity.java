package com.ginray;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class IntroduceActivity extends Activity implements OnClickListener{

		protected void onResume() {
			/**
			 * 设置为竖屏
			 */
			if(getRequestedOrientation()!= ActivityInfo.SCREEN_ORIENTATION_PORTRAIT){
				setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			}
			super.onResume();
		}
	public void onCreate(Bundle bundle){
		super.onCreate(bundle) ;
		 
		Log.v("tag", "IntroduceAcitivity 被执行了");
		
		
		//强制全屏
        //首先去掉title,就是没有title 那一行，但是还不是全屏
        requestWindowFeature(Window.FEATURE_NO_TITLE);    
        // 禁止屏幕休眠
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        //去掉状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
       
        setContentView(R.layout.introduce);
        //介绍页面的开始按钮
        findViewById(R.id.begin).setOnClickListener(this);



		//得到TextView控件对象
		TextView textView1 =(TextView)findViewById(R.id.introduce);
		TextView textView2 =(TextView)findViewById(R.id.power);
		Button button=(Button)findViewById(R.id.begin);

		//将字体文件保存在assets目录下，创建Typeface对象

		Typeface typeFace =Typeface.createFromAsset(getAssets(),"wawati.TTF");
		//使用字体
		textView1.setTypeface(typeFace);
		textView2.setTypeface(typeFace);
		button.setTypeface(typeFace);



	}

	public void onClick(View v) {
		// TODO Auto-generated method stub

		if (v.getId() == R.id.begin) {
			Intent i = new Intent(this, mainActivity.class);
			startActivity(i);
			this.finish();
		}


	}
	
	
}
