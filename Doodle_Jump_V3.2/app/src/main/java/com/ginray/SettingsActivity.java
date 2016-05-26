package com.ginray;

import android.app.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;

public class SettingsActivity extends Activity implements OnClickListener {
	private SharedPreferences baseSettings;
	private SharedPreferences rankingSettings;


	protected void onResume() {
		/**
		 * 设置为竖屏
		 */
		if(getRequestedOrientation()!= ActivityInfo.SCREEN_ORIENTATION_PORTRAIT){
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		}
		super.onResume();
	}
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
        setContentView(R.layout.settings);
        
        // 创建一个共享首选项
        baseSettings = PreferenceManager.getDefaultSharedPreferences(this);
        
        // 设置震动效果
        CheckBox vibrateCheckbox = (CheckBox) findViewById(R.id.settings_vibrate);
        // 设置复选框的值,第二个参数为如果ConstantInfo.PREFERENCE_KEY_VIBRATE为空或者不存在,默认返回true
		vibrateCheckbox.setChecked(baseSettings.getBoolean(
				GameUi.PREFERENCE_KEY_VIBRATE, true));
		vibrateCheckbox
				.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
				  
					public void  onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
						
						if (isChecked) {
							baseSettings.edit().putBoolean(
									GameUi.PREFERENCE_KEY_VIBRATE, true)
									.commit();
						} else {
							baseSettings.edit().putBoolean(
									GameUi.PREFERENCE_KEY_VIBRATE, false)
									.commit();
						}
					}
				});
		
		// 设置声音效果
		CheckBox soundsCheckbox = (CheckBox)findViewById(R.id.settings_sounds);
		soundsCheckbox.setChecked(baseSettings.getBoolean(GameUi.PREFERENCE_KEY_SOUNDS, true));
		soundsCheckbox.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener(){
			public void  onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
				
				if (isChecked) {
					baseSettings.edit().putBoolean(
							GameUi.PREFERENCE_KEY_SOUNDS, true)
							.commit();
					Log.e("vibrateCheckbox", "checked");
				} else {
					baseSettings.edit().putBoolean(
							GameUi.PREFERENCE_KEY_SOUNDS, false)
							.commit();
					Log.e("vibrateCheckbox", "notChecked");
				}
			}
			
		});
		
		// 设置游戏前提示信息效果
		
		CheckBox showTipsCheckbox = (CheckBox) findViewById(R.id.settings_showtips);
		showTipsCheckbox.setChecked(baseSettings.getBoolean(
				GameUi.PREFERENCE_KEY_SHOWTIPS, true));
		showTipsCheckbox
				.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
					
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						if (isChecked) {
							baseSettings.edit().putBoolean(
									GameUi.PREFERENCE_KEY_SHOWTIPS, true)
									.commit();
						} else {
							baseSettings
									.edit()
									.putBoolean(
											GameUi.PREFERENCE_KEY_SHOWTIPS,
											false).commit();
						}
					}
				});
		
		SeekBar seekBar = (SeekBar) findViewById(R.id.velocityController);
		seekBar.setProgress(baseSettings.getInt(GameUi.PREFERENCE_KEY_POWER, 60));
		seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
			}
			public void onStartTrackingTouch(SeekBar seekBar) {
				
			}
			//  手离开进度条时
			public void onStopTrackingTouch(SeekBar seekBar) {
				baseSettings.edit().putInt(GameUi.PREFERENCE_KEY_POWER, seekBar.getProgress()).commit();
			}
		});
  
		// 在data/data/share_prefs中会生成GameUi.PREFERENCE_RANKING_INFO.xml文件
		rankingSettings = getSharedPreferences(
				GameUi.PREFERENCE_RANKING_INFO, 0);
				
		TextView bestRecordTextView = (TextView) findViewById(R.id.settings_best_record_textview);
		bestRecordTextView.setText(""
				+ bestRecordTextView.getText()
				+ rankingSettings.getInt(
						GameUi.PREFERENCE_KEY_RANKING_SCORE, 0));
		
		Button okayButton = (Button) findViewById(R.id.options_okay_button);
		okayButton.setOnClickListener(this);

		Button tipsButton = (Button) findViewById(R.id.options_tips_button);
		tipsButton.setOnClickListener(this);





		//得到TextView控件对象
		CheckBox checkBox1 =(CheckBox)findViewById(R.id.settings_vibrate);
		CheckBox checkBox2 =(CheckBox)findViewById(R.id.settings_sounds);
		CheckBox checkBox3 =(CheckBox)findViewById(R.id.settings_showtips);

		TextView textView1  =(TextView)findViewById(R.id.settings_power_controller_title);
		TextView textView2  =(TextView)findViewById(R.id.settings_best_record_textview);

		Button button1 =(Button)findViewById(R.id.options_okay_button);
		Button button2 =(Button)findViewById(R.id.options_tips_button);

		//将字体文件保存在assets目录下，创建Typeface对象
		Typeface typeFace =Typeface.createFromAsset(getAssets(),"wawati.TTF");

		//使用字体
		checkBox1.setTypeface(typeFace);
		checkBox2.setTypeface(typeFace);
		checkBox3.setTypeface(typeFace);

		textView1.setTypeface(typeFace);
		textView2.setTypeface(typeFace);

		button1.setTypeface(typeFace);
		button2.setTypeface(typeFace);
	}
	    
	          public void onClick(View v) {

	      		switch (v.getId()) {
	      		case R.id.options_okay_button:
	      			Intent i=new Intent(this, DoodleJumpActivity.class);
					startActivity(i);
					this.finish();
	      			break;

	      		case R.id.options_tips_button:
	      			Intent i1 = new Intent(this, IntroduceActivity.class);
	      			startActivity(i1);
					this.finish();
	      		}
	   }
}
