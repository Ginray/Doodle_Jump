package com.ginray;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * Created by Ginray on 2016/5/24.
 */
public class ShowstoreActivity extends Activity implements View.OnClickListener {
    protected void onResume() {
        /**
         * 设置为竖屏
         */
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        super.onResume();
    }

    public void onCreate(Bundle bundle) {

        super.onCreate(bundle);

        //强制全屏
        //首先去掉title,就是没有title 那一行，但是还不是全屏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 禁止屏幕休眠
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        //去掉状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.store);
        Log.d("showstore", "showstore start");

        findViewById(R.id.bunny).setOnClickListener(this);
        findViewById(R.id.doodlestein).setOnClickListener(this);
        findViewById(R.id.ice).setOnClickListener(this);
        findViewById(R.id.jungle).setOnClickListener(this);
        findViewById(R.id.ninja).setOnClickListener(this);
        findViewById(R.id.snow).setOnClickListener(this);
        findViewById(R.id.soccer).setOnClickListener(this);
        findViewById(R.id.space).setOnClickListener(this);
        findViewById(R.id.underwater).setOnClickListener(this);

    }

    public void onClick(View v) {
        // TODO Auto-generated method stub

        if (v.getId() == R.id.bunny) {
            DoodleJumpView.NowRoleImag= DoodleJumpView.Role_Bunny;
            DoodleJumpView.NowBackGroundImag= DoodleJumpView.BackGround_Bunny;
        }
        if (v.getId() == R.id.doodlestein) {
            DoodleJumpView.NowRoleImag= DoodleJumpView.Role_doodlestein;
            DoodleJumpView.NowBackGroundImag= DoodleJumpView.BackGround_doodlestein;
        }
        if (v.getId() == R.id.ice) {
            DoodleJumpView.NowRoleImag= DoodleJumpView.Role_ice;
            DoodleJumpView.NowBackGroundImag= DoodleJumpView.BackGround_ice;
        }
        if (v.getId() == R.id.jungle) {
            DoodleJumpView.NowRoleImag= DoodleJumpView.Role_jungle;
            DoodleJumpView.NowBackGroundImag= DoodleJumpView.BackGround_jungle;
        }
        if (v.getId() == R.id.ninja) {
            DoodleJumpView.NowRoleImag= DoodleJumpView.Role_ninja;
            DoodleJumpView.NowBackGroundImag= DoodleJumpView.BackGround_ninja;
        }
        if (v.getId() == R.id.snow) {
            DoodleJumpView.NowRoleImag= DoodleJumpView.Role_snow;
            DoodleJumpView.NowBackGroundImag= DoodleJumpView.BackGround_snow;
        }
        if (v.getId() == R.id.soccer) {
            DoodleJumpView.NowRoleImag= DoodleJumpView.Role_soccer;
            DoodleJumpView.NowBackGroundImag= DoodleJumpView.BackGround_soccer;
        }
        if (v.getId() == R.id.space) {
            DoodleJumpView.NowRoleImag= DoodleJumpView.Role_space;
            DoodleJumpView.NowBackGroundImag= DoodleJumpView.BackGround_space;
        }
        if (v.getId() == R.id.underwater) {
            DoodleJumpView.NowRoleImag= DoodleJumpView.Role_underwater;
            DoodleJumpView.NowBackGroundImag= DoodleJumpView.BackGround_underwater;
        }


    }

}