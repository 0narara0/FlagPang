package com.mydoublej.flagpang;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;

import static android.content.Intent.ACTION_VIEW;
import static android.content.Intent.parseUri;

public class SelectOX extends AppCompatActivity implements View.OnClickListener {
    private  DBOpenHelper dbOpenHelper;

    ImageView imageViewOXFlag;
    TextView textViewQuiz, textViewProgress, textViewScore, textViewAnswer;
    int score, quizNum;
    String p_level="1";
    String country,country_kor,countryQuiz;
    Button btn_O, btn_X;
    FloatingActionButton btn_Info;

    Handler handler = new Handler();



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
        textViewQuiz = findViewById(R.id.textViewQuiz);
        textViewProgress = findViewById(R.id.textViewProgress);
        textViewScore = findViewById(R.id.textViewScore);
        textViewAnswer = findViewById(R.id.textViewAnswer);
        btn_Info = findViewById(R.id.btn_Info);
        btn_Info.setOnClickListener(this);



        //Bundle bundle = getIntent().getExtras();
        //String text = bundle.getString("saveValue", "NO DATA");

        dbOpenHelper = DBOpenHelper.getInstance(this);
        quizSet();
    }





    @Override
    public void onClick(View v) {
        int result=0; //0-textviewAnswer x, 1-정답 ,2-오답

        switch (v.getId()){

            //wikipedia button 추가
            case R.id.btn_Info:
                result=0;
                String defaultUrl = "https://ko.wikipedia.org/wiki/"+country_kor;
                Intent intentWikipedia = new Intent(Intent.ACTION_VIEW, Uri.parse(defaultUrl));
                startActivity(intentWikipedia);
                break;



            case R.id.btn_O:
                if(country==countryQuiz) result = 1;
                else result = 2;
            break;

            case R.id.btn_X:
                if(country!=countryQuiz) result = 1;
                else result = 2;
                break;

            case R.id.btn_Reset:
                quizNum=0;
                score = 0;
                textViewAnswer.setVisibility(View.GONE);
                quizSet();
                break;

            case R.id.btn_Main:
                Intent intent = new Intent();
                intent.putExtra("result_msg", 200);
                setResult(RESULT_OK, intent);
                finish();
                break;

        }
        if(result == 1) {
            textViewAnswer.setVisibility(View.VISIBLE);
            textViewAnswer.setText("CORRECT!!" + "\n" + country);
            textViewAnswer.setTextColor(Color.parseColor("#FF00893C"));
            score++;
        }
        else if(result == 2) {
            Animation shake = AnimationUtils.loadAnimation(this,R.anim.shake);
            imageViewOXFlag.startAnimation(shake);
            textViewAnswer.setVisibility(View.VISIBLE);
            textViewAnswer.setText("INCORRECT!!" + "\n" + country);
            textViewAnswer.setTextColor(Color.RED);
        }

        if(quizNum == 10){
            country = "";
            quizNum = 0;
            textViewScore.setText(" Score : " + score);
           // textViewProgress.setText("game over");
            delayGameOver();
            textViewAnswer.setVisibility(View.GONE);

        }
        else if(result==1 || result==2){
            delayResult();
        }
    }








    //퀴즈세팅
    public void quizSet(){

        ArrayList<GetRecord> arrayList = new ArrayList<>();
        String continent, image, level;
        int id;

        quizNum++;
        textViewScore.setText(" Score: " + score);
        textViewProgress.setText(quizNum + " of 10");

        //랜덤하게 이미지 적용
        arrayList = dbOpenHelper.selectGetRecord(p_level);
        int member = arrayList.size();

        int[] rid = new int[2];
        rid[0] = (int) (Math.random() * member);//정답
        rid[1] = (int) (Math.random() * member);//오답
        AssetManager am = getResources().getAssets();
        InputStream is = null ;

        String filename = arrayList.get(rid[0]).getContinent().toString();
        filename += "/";
        filename += arrayList.get(rid[0]).getImage().toString();
        Bitmap bitmap = null;
        country = arrayList.get(rid[0]).getCountry().toString();

        int randomIndex = (int) ((Math.random()) * 10 % 2);//정답 오답을 랜덤하게 뽑음
        countryQuiz = arrayList.get(rid[randomIndex]).getCountry().toString();
        country_kor = arrayList.get(rid[randomIndex]).getCountryKor().toString();
//        textViewQuiz.setText(countryQuiz);
        textViewQuiz.setText(country_kor);

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

    public void delayResult() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                quizSet();
                textViewAnswer.setVisibility(View.GONE);
            }
        }, 500);//지연
    }

    public void delayGameOver() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("GAME OVER");
        builder.setMessage("score : " + score);
        builder.setPositiveButton("다시 게임하기",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        score = 0;
                        quizNum = 0;
                        quizSet();
                    }
                });
        builder.show();
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

}
