package com.mydoublej.flagpang;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;

public class SelectOX extends AppCompatActivity implements View.OnClickListener {
    private  DBOpenHelper dbOpenHelper;

    ImageView imageViewOXFlag;
    TextView textViewQuiz, textViewProgress, textViewScore;
    int score, quizNum;
    String country,countryQuiz;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_ox);

        imageViewOXFlag = findViewById(R.id.imageViewOXFlag);
        ((ImageButton)findViewById(R.id.btn_O)).setOnClickListener(this);
        ((ImageButton)findViewById(R.id.btn_X)).setOnClickListener(this);
        ((Button)findViewById(R.id.btn_Reset)).setOnClickListener(this);
        ((Button)findViewById(R.id.btn_Main)).setOnClickListener(this);
        textViewQuiz = findViewById(R.id.textViewQuiz);
        textViewProgress = findViewById(R.id.textViewProgress);
        textViewScore = findViewById(R.id.textViewScore);

        dbOpenHelper = DBOpenHelper.getInstance(this);
        quizSet();

    }

    @Override
    public void onClick(View v) {
        int result=0; //0-toast x, 1-정답 ,2-오답

        switch (v.getId()){
            case R.id.btn_O:
                if(country==countryQuiz) result = 1;
                else result = 2;

                break;

            case R.id.btn_X:
                if(country==countryQuiz) result = 2;
                else result = 1;



                break;

            case R.id.btn_Reset:
                quizReset();
                break;

            case R.id.btn_Main:
                Intent intent = new Intent(this,FlagPang.class);
                startActivity(intent);
                finish();
                break;

        }
        if(result == 1) {
            Toast.makeText(this, "정답입니다.", Toast.LENGTH_SHORT).show();
            quizSet();
        }
        else if(result == 2) {
            Toast.makeText(this, "오답입니다.", Toast.LENGTH_SHORT).show();
            quizSet();
        }

    }


    //퀴즈세팅
    public void quizSet(){
        ArrayList<GetRecord> arrayList = new ArrayList<>();
        String continent, image, level;
        int id;

        quizNum++;
        textViewScore.setText(" Score : " + score);
        textViewProgress.setText(quizNum + "/ 10");



        //랜덤하게 이미지 적용
        arrayList = dbOpenHelper.selectGetRecord();
        int member = arrayList.size();
        int rid =((int) (Math.random() * 100)) % member;
        AssetManager am = getResources().getAssets();
        InputStream is = null ;

        String filename = arrayList.get(rid).getContinent().toString();
        filename += "/";
        filename += arrayList.get(rid).getImage().toString();
        Bitmap bitmap = null;
        country = arrayList.get(rid).getCountry().toString();

        rid = ((int) (Math.random() * 100)) % member;
        countryQuiz = arrayList.get(rid).getCountry().toString();
        textViewQuiz.setText(countryQuiz);

        try {
            is = am.open(filename) ;
            bitmap = BitmapFactory.decodeStream(is);
            // TODO : use is(InputStream).

        } catch (Exception e) {
            e.printStackTrace() ;
        }

        if (is != null) {
            try {
                is.close() ;
            } catch (Exception e) {
                e.printStackTrace() ;
            }
        }
        imageViewOXFlag.setImageBitmap(bitmap);
    }

    public void quizReset(){
        ArrayList<GetRecord> arrayList = new ArrayList<>();
        String country, continent, image, level;
        int id;

        quizNum=1;
        score = 0;
        textViewScore.setText(" Score : " + score);
        textViewProgress.setText(quizNum + " / 10");



        //랜덤하게 이미지 적용
        arrayList = dbOpenHelper.selectGetRecord();
        int member = arrayList.size();
        int rid =((int) (Math.random() * 100)) % member;
        AssetManager am = getResources().getAssets();
        InputStream is = null ;

        String filename = arrayList.get(rid).getContinent().toString();
        filename += "/";
        filename += arrayList.get(rid).getImage().toString();
         Bitmap bitmap = null;

        try {
            is = am.open(filename) ;
            bitmap = BitmapFactory.decodeStream(is);
            // TODO : use is(InputStream).

        } catch (Exception e) {
            e.printStackTrace() ;
        }

        if (is != null) {
            try {
                is.close() ;
            } catch (Exception e) {
                e.printStackTrace() ;
            }
        }
        imageViewOXFlag.setImageBitmap(bitmap);
    }
}
