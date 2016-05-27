package com.ginray;

import android.view.View.OnClickListener;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ginray.view.CircleImageView;
import com.ginray.view.CircleLayout;
import com.ginray.view.CircleLayout.OnItemClickListener;
import com.ginray.view.CircleLayout.OnItemSelectedListener;

public class CircleShowStore extends Activity implements OnClickListener,OnItemSelectedListener, OnItemClickListener{
    TextView selectedTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        //强制全屏
        //首先去掉title,就是没有title 那一行，但是还不是全屏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 禁止屏幕休眠
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        //去掉状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.circle_show_store);

        CircleLayout circleMenu = (CircleLayout)findViewById(R.id.main_circle_layout);
        circleMenu.setOnItemSelectedListener(this);
        circleMenu.setOnItemClickListener(this);

        Typeface typeFace =Typeface.createFromAsset(getAssets(),"wawati.TTF");
        selectedTextView = (TextView)findViewById(R.id.main_selected_textView);
        selectedTextView.setTypeface(typeFace);
        selectedTextView.setTextSize(30);
        selectedTextView.setTextColor(0xFFFFA07A);
        selectedTextView.setText(((CircleImageView) circleMenu.getSelectedItem()).getName());

        DoodleJumpView.NowRoleImag= DoodleJumpView.Role_Bunny;
        DoodleJumpView.NowBackGroundImag= DoodleJumpView.BackGround_Bunny;

        findViewById(R.id.store_chose).setOnClickListener(this);
        findViewById(R.id.store_menu).setOnClickListener(this);
    }



    public void onClick(View v) {
        // TODO Auto-generated method stub

        if (v.getId() == R.id.store_chose) {
            Intent i = new Intent(this, mainActivity.class);
            startActivity(i);
            this.finish();
        }
        if (v.getId() == R.id.store_menu) {
            Intent i = new Intent(this, DoodleJumpActivity.class);
            startActivity(i);
            this.finish();
        }

    }

    public void onItemSelected(View view, int position, long id, String name) {
        Typeface typeFace =Typeface.createFromAsset(getAssets(),"wawati.TTF");
        selectedTextView.setTypeface(typeFace);
        selectedTextView.setTextColor(0xFFFFA07A);
        selectedTextView.setTextSize(30);
        selectedTextView.setText(name);

        if (name.equals("bunny")) {
            DoodleJumpView.NowRoleImag= DoodleJumpView.Role_Bunny;
            DoodleJumpView.NowBackGroundImag= DoodleJumpView.BackGround_Bunny;
        }
        if (name.equals("doodlestein")) {
            DoodleJumpView.NowRoleImag= DoodleJumpView.Role_doodlestein;
            DoodleJumpView.NowBackGroundImag= DoodleJumpView.BackGround_doodlestein;
        }
        if (name.equals("ice") ) {
            DoodleJumpView.NowRoleImag= DoodleJumpView.Role_ice;
            DoodleJumpView.NowBackGroundImag= DoodleJumpView.BackGround_ice;
        }
        if (name.equals("jungle") ) {
            DoodleJumpView.NowRoleImag= DoodleJumpView.Role_jungle;
            DoodleJumpView.NowBackGroundImag= DoodleJumpView.BackGround_jungle;
        }
        if (name.equals("ninja")) {
            DoodleJumpView.NowRoleImag= DoodleJumpView.Role_ninja;
            DoodleJumpView.NowBackGroundImag= DoodleJumpView.BackGround_ninja;
        }
        if (name.equals("snow")) {
            DoodleJumpView.NowRoleImag= DoodleJumpView.Role_snow;
            DoodleJumpView.NowBackGroundImag= DoodleJumpView.BackGround_snow;
        }
        if (name.equals("soccer")) {
            DoodleJumpView.NowRoleImag= DoodleJumpView.Role_soccer;
            DoodleJumpView.NowBackGroundImag= DoodleJumpView.BackGround_soccer;
        }
        if (name.equals("space")) {
            DoodleJumpView.NowRoleImag= DoodleJumpView.Role_space;
            DoodleJumpView.NowBackGroundImag= DoodleJumpView.BackGround_space;
        }
        if (name.equals("underwater")) {
            DoodleJumpView.NowRoleImag= DoodleJumpView.Role_underwater;
            DoodleJumpView.NowBackGroundImag= DoodleJumpView.BackGround_underwater;
        }



    }

    @Override
    public void onItemClick(View view, int position, long id, String name) {
        Toast.makeText(getApplicationContext(), getResources().getString(R.string.start_app) + " " + name, Toast.LENGTH_SHORT).show();
        //在这里判断name来执行屏幕和角色的变换
    }

}
