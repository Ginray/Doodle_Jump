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
import android.widget.TextView;

public class Gameover extends Activity implements OnClickListener{

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

        Log.v("tag", "Gameover被执行了");


        //强制全屏
        //首先去掉title,就是没有title 那一行，但是还不是全屏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 禁止屏幕休眠
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        //去掉状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.gameover);
        //介绍页面的开始按钮

        Bundle TextBundle = getIntent().getExtras();//获取一个句柄

        String ScoreString= TextBundle.getString("score");

        TextView  ftextview;
        ftextview = (TextView)(findViewById(R.id.tgameover));
        Typeface typeFace =Typeface.createFromAsset(getAssets(),"wawati.TTF");
        //使用字体
        ftextview.setTypeface(typeFace);
        ftextview.setText(ScoreString);

        findViewById(R.id.retry).setOnClickListener(this);
        findViewById(R.id.goback).setOnClickListener(this);
    }

    public void onClick(View v) {
        // TODO Auto-generated method stub

        if (v.getId() == R.id.retry) {
            Intent i = new Intent(this, mainActivity.class);
            startActivity(i);
            this.finish();

        }
        if (v.getId() == R.id.goback) {
            Intent i = new Intent(this, DoodleJumpActivity.class);
            startActivity(i);
            this.finish();
            //this.overridePendingTransition(R.anim.activity_open,R.anim.activity_close);
        }

    }


}
