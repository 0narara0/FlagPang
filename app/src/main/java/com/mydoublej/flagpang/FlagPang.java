package com.mydoublej.flagpang;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

public class FlagPang extends AppCompatActivity implements View.OnClickListener {
    private DBOpenHelper dbOpenHelper;
    private SQLiteDatabase mdb;
    String language, level, sound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((Button)findViewById(R.id.btn_country)).setOnClickListener(this);
        ((Button)findViewById(R.id.btn_ox)).setOnClickListener(this);
        ((Button)findViewById(R.id.btn_flag)).setOnClickListener(this);
        ((ImageButton)findViewById(R.id.btn_setting)).setOnClickListener(this);
        //애니메이션xml 파일을 로드
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.wave);
        ImageView imageViewFlagpang = findViewById(R.id.imageViewFlagpang);
        //애니메이션을 시작
        imageViewFlagpang.startAnimation(animation);
        //화면을 갱신
        imageViewFlagpang.invalidate();

        dbOpenHelper = DBOpenHelper.getInstance(this);
    }

    @Override
    public void onClick(View v) {
        Bundle bundle = new Bundle();
        loadPreference();
        bundle.putString("p_language",language);
        bundle.putString("p_level",level);
        bundle.putString("p_sound",sound);

        switch (v.getId()){
            case R.id.btn_country:
                Intent intentCountry = new Intent(this,SelectCountry.class);
                intentCountry.putExtras(bundle);
                startActivityForResult(intentCountry,100);
                break;
            case R.id.btn_ox:
                Intent intentOX = new Intent(this,SelectOX.class);
                intentOX.putExtras(bundle);
                startActivityForResult(intentOX,200);
                break;
            case R.id.btn_flag:
                Intent intentFlag = new Intent(this,SelectFlag.class);
                intentFlag.putExtras(bundle);
                startActivityForResult(intentFlag,300);
                break;
            case R.id.btn_setting:
                Intent intent = new Intent(this,PreferenceSetting.class);
                intent.putExtras(bundle);
                startActivityForResult(intent,500);
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
    @Override
    protected void onStop() {
        super.onStop();
        // Activity 가 종료되기 전에 저장한다
        // SharedPreferences 에 설정값(특별히 기억해야할 사용자 값)을 저장하기
//        SharedPreferences pref = getSharedPreferences(sfName, 0);
//        SharedPreferences.Editor editor = sf.edit();//저장하려면 editor가 필요
//        String str = et.getText().toString(); // 사용자가 입력한 값
//        editor.putString("name", str); // 입력
//        editor.putString("xx", "xx"); // 입력
//        editor.commit(); // 파일에 최종 반영함
    }
    private void loadPreference() {
        //ShardPreferences와 Editor 객체 얻어오기
        SharedPreferences pref = getSharedPreferences("pref", 0);
        SharedPreferences.Editor editor = pref.edit();
        language = pref.getString("p_language", "korean");
        level = pref.getString("p_level", "1");
        sound = pref.getString("p_sound", "on");
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
