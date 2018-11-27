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

        switch (v.getId()){
            case R.id.btn_O:
//                if()

                Toast.makeText(this,"정답입니다.",Toast.LENGTH_SHORT).show();

                quizSet();

                break;

            case R.id.btn_X:

                Toast.makeText(this,"오답입니다.",Toast.LENGTH_SHORT).show();

                quizSet();

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

    }

    //퀴즈세팅
    public void quizSet(){
        ArrayList<GetRecord> arrayList = new ArrayList<>();
        String country, continent, image, level;
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
