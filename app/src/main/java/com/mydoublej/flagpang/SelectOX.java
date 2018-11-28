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
    TextView textViewQuiz, textViewProgress, textViewScore, textViewAnswer;
    int score, quizNum;
    String country,countryQuiz;
    Button btn_GameOver;
    ImageButton btn_O, btn_X;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_ox);

        imageViewOXFlag = findViewById(R.id.imageViewOXFlag);
        btn_O = findViewById(R.id.btn_O);
        btn_O.setOnClickListener(this);
        btn_X = findViewById(R.id.btn_X);
        btn_X.setOnClickListener(this);
        ((Button)findViewById(R.id.btn_Reset)).setOnClickListener(this);
        ((Button)findViewById(R.id.btn_Main)).setOnClickListener(this);
        btn_GameOver = findViewById(R.id.btn_GameOver);
        btn_GameOver.setOnClickListener(this);
        textViewQuiz = findViewById(R.id.textViewQuiz);
        textViewProgress = findViewById(R.id.textViewProgress);
        textViewScore = findViewById(R.id.textViewScore);
        textViewAnswer = findViewById(R.id.textViewAnswer);


        dbOpenHelper = DBOpenHelper.getInstance(this);
        quizSet();



    }

    @Override
    public void onClick(View v) {
        int result=0; //0-textviewAnswer x, 1-정답 ,2-오답

        switch (v.getId()){
            case R.id.btn_O:
                if(country==countryQuiz) result = 1;
                else result = 2;
                break;

            case R.id.btn_X:
                if(country!=countryQuiz) result = 1;
                else result = 2;

                break;

            case R.id.btn_Reset:
                btn_GameOver.setVisibility(View.GONE);
                quizNum=0;
                score = 0;
                btn_O.setClickable(true);
                btn_X.setClickable(true);
                textViewScore.setText(" Score : " + score);
                textViewProgress.setText(quizNum + " / 10");
                imageViewOXFlag.setAlpha(1f);
                btn_O.setAlpha(1f);
                btn_X.setAlpha(1f);
                textViewAnswer.setVisibility(View.GONE);
                quizSet();
                break;

            case R.id.btn_Main:
                Intent intent = new Intent(this,FlagPang.class);
                startActivity(intent);
                finish();
                break;

            case R.id.btn_GameOver:
                btn_GameOver.setVisibility(View.GONE);
                quizNum=0;
                score = 0;
                btn_O.setClickable(true);
                btn_X.setClickable(true);
                textViewScore.setText(" Score : " + score);
                textViewProgress.setText(quizNum + " / 10");
                imageViewOXFlag.setAlpha(1f);
                btn_O.setAlpha(1f);
                btn_X.setAlpha(1f);
                textViewAnswer.setVisibility(View.GONE);
                quizSet();
                break;

        }
        if(result == 1) {
            textViewAnswer.setVisibility(View.VISIBLE);
            textViewAnswer.setText("CORRECT!!");
            score++;
            quizSet();
        }
        else if(result == 2) {
            textViewAnswer.setVisibility(View.VISIBLE);
            textViewAnswer.setText("INCORRECT!!");
            quizSet();
        }

    }




    //퀴즈세팅
    public void quizSet(){

        ArrayList<GetRecord> arrayList = new ArrayList<>();
        String continent, image, level;
        int id;

        btn_GameOver.setVisibility(View.GONE);
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
        if(quizNum>=10){
            btn_GameOver.setVisibility(View.VISIBLE);
            imageViewOXFlag.setAlpha(0.5f);
            btn_O.setAlpha(0.5f);
            btn_X.setAlpha(0.5f);
            btn_O.setClickable(false);
            btn_X.setClickable(false);

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
