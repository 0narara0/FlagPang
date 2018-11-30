package com.mydoublej.flagpang;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
    Button btn_O, btn_X;

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

                btn_O.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        switch (event.getAction()){
                            case MotionEvent.ACTION_DOWN:{
                                btn_O.setBackgroundResource(R.drawable.btn_o1);
                                break;
                            }
                            case MotionEvent.ACTION_UP:{
                                btn_O.setBackgroundResource(R.drawable.btn_o2);
                                break;
                            }
                        }
                        return false;
                    }
                });
                if(quizNum>=10){

                result = 0;
                country = "";
                textViewProgress.setText("game over");
                delayGameOver();
                textViewAnswer.setVisibility(View.GONE);
            }
            break;

            case R.id.btn_X:
                if(country!=countryQuiz) result = 1;
                else result = 2;
                btn_X.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        switch (event.getAction()){
                            case MotionEvent.ACTION_DOWN:{
                                btn_X.setBackgroundResource(R.drawable.btn_x1);
                                break;
                            }
                            case MotionEvent.ACTION_UP:{
                                btn_X.setBackgroundResource(R.drawable.btn_x2);
                                break;
                            }
                        }
                        return false;
                    }
                });
                if(quizNum>=10){
                    result = 0;
                    country = "";
                    textViewProgress.setText("game over");
                    delayGameOver();
                    textViewAnswer.setVisibility(View.GONE);
                }
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
            delayResult();
        }
        else if(result == 2) {
            Animation shake = AnimationUtils.loadAnimation(this,R.anim.shake);
            imageViewOXFlag.startAnimation(shake);
            textViewAnswer.setVisibility(View.VISIBLE);
            textViewAnswer.setText("INCORRECT!!" + "\n" + country);
            textViewAnswer.setTextColor(Color.RED);
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
        arrayList = dbOpenHelper.selectGetRecord();
        int member = arrayList.size();

        int[] rid = new int[2];
        rid[0] = ((int) (Math.random() * 100)) % member;//정답
        rid[1] = ((int) (Math.random() * 100)) % member;//오답
        AssetManager am = getResources().getAssets();
        InputStream is = null ;

        String filename = arrayList.get(rid[0]).getContinent().toString();
        filename += "/";
        filename += arrayList.get(rid[0]).getImage().toString();
        Bitmap bitmap = null;
        country = arrayList.get(rid[0]).getCountry().toString();

        int randomIndex = (int) ((Math.random()) * 10 % 2);//정답 오답을 랜덤하게 뽑음
        countryQuiz = arrayList.get(rid[randomIndex]).getCountry().toString();
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
        finish();
    }
}
