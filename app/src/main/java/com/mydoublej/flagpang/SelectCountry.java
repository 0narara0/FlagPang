package com.mydoublej.flagpang;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;

public class SelectCountry extends AppCompatActivity implements View.OnClickListener{
    private  DBOpenHelper dbOpenHelper;


    TextView textViewScore, textViewProgress, textViewSelectCountry;
    ImageView imageViewFlag;
    Button buttonCountry1, buttonCountry2, buttonCountry3, buttonCountry4, buttonReset;
    int score = 0, quizNum = 0;
    String imageTag = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_country);

        textViewScore = findViewById(R.id.textViewScore);
        textViewProgress = findViewById(R.id.textViewProgress);
        textViewSelectCountry = findViewById(R.id.textViewSelectCountry);
        imageViewFlag = findViewById(R.id.imageViewFlag);
        (buttonReset = findViewById(R.id.buttonReset)).setOnClickListener(this);
        (buttonCountry1 = findViewById(R.id.buttonCountry1)).setOnClickListener(this);
        (buttonCountry2 = findViewById(R.id.buttonCountry2)).setOnClickListener(this);
        (buttonCountry3 = findViewById(R.id.buttonCountry3)).setOnClickListener(this);
        (buttonCountry4 = findViewById(R.id.buttonCountry4)).setOnClickListener(this);

        dbOpenHelper = DBOpenHelper.getInstance(this);
        QuizSet();
    }

    @Override
    public void onClick(View view) {
        // Reset 버튼 눌렀을 때
        if(view.getId() == (R.id.buttonReset)){
            score = 0;
            quizNum = 0;
            buttonCountry1.setEnabled(true);
            buttonCountry2.setEnabled(true);
            buttonCountry3.setEnabled(true);
            buttonCountry4.setEnabled(true);
            QuizSet();
        }

        // 정답일 때
        else if(view.getTag().equals(imageViewFlag.getTag())){
            Toast.makeText(this, "정답입니다!", Toast.LENGTH_SHORT);
            score++;
            QuizSet();
        }

        // 오답일 때
        else {
            view.setEnabled(false);
            Toast.makeText(this, "오답입니다!", Toast.LENGTH_SHORT);
        }
    }

    //퀴즈 새로 셋팅
    public void QuizSet(){
        ArrayList<GetRecord> arrayList = new ArrayList<>();
        String country, continent, image, level;
        int id;

        quizNum++;
        textViewScore.setText(" Score : " + score);
        textViewProgress.setText(quizNum + "of 10");



        // 랜덤하게 이미지 적용
        //imageViewFlag.setImageDrawable("Korea.png");

        arrayList = dbOpenHelper.selectGetRecord();
        int member = arrayList.size();
        int rid =((int) (Math.random() * 100)) % member;
        AssetManager am = getResources().getAssets();
        InputStream is = null ;


 //       String filename = "oceania/austria.png";
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

        imageViewFlag.setImageBitmap(bitmap);
        // 랜덤하게 이미지 적용
        //imageViewFlag.setImageDrawable("Korea.png");


        imageTag = "Korea";

        // 랜덤하게 테스트 적용
        buttonCountry1.setText("Korea");
        buttonCountry1.setTag("Korea");
        buttonCountry2.setText("Japan");
        buttonCountry2.setTag("Japan");
        buttonCountry3.setText("China");
        buttonCountry3.setTag("China");
        buttonCountry4.setText("India");
        buttonCountry4.setTag("India");
    }
}
