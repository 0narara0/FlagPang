package com.mydoublej.flagpang;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.HashMap;

public class PreferenceSetting extends AppCompatActivity implements View.OnClickListener{
    String language, level, sound;
    private RadioGroup rgLanguage, rgLevel, rgSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference);

        rgLanguage = (RadioGroup)findViewById(R.id.rgLanguage);
        rgLevel = (RadioGroup)findViewById(R.id.rgLevel);
        rgSound = (RadioGroup)findViewById(R.id.rgSound);
        ((Button)findViewById(R.id.btnClear)).setOnClickListener(this);
        ((Button)findViewById(R.id.btnSave)).setOnClickListener(this);
        ((Button)findViewById(R.id.btnQuit)).setOnClickListener(this);
        setRadiobutton();
    }

    @Override
    public void onClick(View v) {
        int idLanguage = rgLanguage.getCheckedRadioButtonId();
        int idLevel = rgLevel.getCheckedRadioButtonId();
        int idSound = rgSound.getCheckedRadioButtonId();

        switch (v.getId()){
            case R.id.btnClear:
                radioGroupInit();
                setRadiobutton();
                savePreference();
                break;
            case R.id.btnSave:
                savePreference();
                break;
            case R.id.btnQuit:
                loadPreference(this);
                Intent intent = new Intent();
                intent.putExtra("result_msg", 400);
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }

    //RadioGroup 초기화
    private  void radioGroupInit() {
        level = "1";
        language = "korean";
        sound = "on";
    }
    //
    private  void setRadiobutton(){
        HashMap<String, String> mm = loadPreference(this);
        language = mm.get("p_language");
        level = mm.get("p_level");
        sound = mm.get("p_sound");

        if(language.equals("korean")){
            ((RadioButton)findViewById(R.id.rbKorean)).setChecked(true);
            ((RadioButton)findViewById(R.id.rbEnglish)).setChecked(false);
        }
        else if(language.equals("english")){
            ((RadioButton)findViewById(R.id.rbKorean)).setChecked(false);
            ((RadioButton)findViewById(R.id.rbEnglish)).setChecked(true);
        }


        if(level.equals("1")){
            ((RadioButton)findViewById(R.id.rbBasic)).setChecked(true);
            ((RadioButton)findViewById(R.id.rbMiddle)).setChecked(false);
            ((RadioButton)findViewById(R.id.rbAdvanced)).setChecked(false);
        }
        else if(level.equals("2")){
            ((RadioButton)findViewById(R.id.rbBasic)).setChecked(false);
            ((RadioButton)findViewById(R.id.rbMiddle)).setChecked(true);
            ((RadioButton)findViewById(R.id.rbAdvanced)).setChecked(false);
        }
        else if(level.equals("3")){
            ((RadioButton)findViewById(R.id.rbBasic)).setChecked(false);
            ((RadioButton)findViewById(R.id.rbMiddle)).setChecked(false);
            ((RadioButton)findViewById(R.id.rbAdvanced)).setChecked(true);
        }


        if(sound.equals("on")) {
            ((RadioButton)findViewById(R.id.rbOn)).setChecked(true);
            ((RadioButton)findViewById(R.id.rbOff)).setChecked(false);
        }
        else if(sound.equals("off")) {
            ((RadioButton)findViewById(R.id.rbOn)).setChecked(false);
            ((RadioButton)findViewById(R.id.rbOff)).setChecked(true);
        }
    }

    private  void savePreference() {
         int id;
        RadioButton rb;

        id = rgLanguage.getCheckedRadioButtonId();
        rb = (RadioButton) findViewById(id);
        language = rb.getText().toString().toLowerCase();

        id = rgLevel.getCheckedRadioButtonId();
        rb = (RadioButton) findViewById(id);
        level = rb.getText().toString().toLowerCase();

        id = rgSound.getCheckedRadioButtonId();
        rb = (RadioButton) findViewById(id);
        sound = rb.getText().toString().toLowerCase();
        saveData(language,level,sound);
    }

    private void saveData(String language, String level, String sound) {
         //ShardPreferences와 Editor 객체 얻어오기
         SharedPreferences pref = getSharedPreferences("pref", 0);
         SharedPreferences.Editor editor = pref.edit();
         if(level.equals("basic")) level="1";
        else if(level.equals("middle")) level="2";
        else level = "3"; //"advanced"

        editor.putString("p_language", language);
        editor.putString("p_level", level);
        editor.putString("p_sound", sound);
        editor.commit();
    }

    public HashMap<String,String> loadPreference(Context context) {
        //ShardPreferences와 Editor 객체 얻어오기
        SharedPreferences pref = context.getSharedPreferences("pref", 0);
        //SharedPreferences.Editor editor = pref.edit();
        language = pref.getString("p_language", "korean");
        level = pref.getString("p_level", "1");
        sound = pref.getString("p_sound", "on");

        HashMap<String,String> map = new HashMap<>();
        map.put("p_language",language);
        map.put("p_level",level);
        map.put("p_sound",sound);
        return  map;
    }
    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
