package com.mydoublej.flagpang;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;

import static android.content.Intent.ACTION_VIEW;
import static android.content.Intent.parseUri;

public class SelectOX extends AppCompatActivity implements View.OnClickListener {
    private DBOpenHelper dbOpenHelper;
    ImageView imageViewOXFlag;
    TextView textViewQuiz, textViewProgress, textViewScore, textViewAnswer;
    int score, quizNum;
    String quiz, country_kor, imageFlag;
    Button btn_O, btn_X, btn_Info;
    Handler handler = new Handler();
    String p_level = "1", p_language = "korean", p_sound = "on";
    SoundPool soundPool;
    int soundCorrect, soundIncorrect;
    Bitmap bitmap = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_ox);

        imageViewOXFlag = findViewById(R.id.imageViewOXFlag);
        btn_O = findViewById(R.id.btn_O);
        btn_O.setOnClickListener(this);
        btn_X = findViewById(R.id.btn_X);
        btn_X.setOnClickListener(this);
        ((Button) findViewById(R.id.btn_Reset)).setOnClickListener(this);
        ((Button) findViewById(R.id.btn_Main)).setOnClickListener(this);
        textViewQuiz = findViewById(R.id.textViewQuiz);
        textViewProgress = findViewById(R.id.textViewProgress);
        textViewScore = findViewById(R.id.textViewScore);
        textViewAnswer = findViewById(R.id.textViewAnswer);
        btn_Info = findViewById(R.id.btn_Info);
        btn_Info.setOnClickListener(this);

        //사운드
        soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
        soundCorrect = soundPool.load(this, R.raw.dingdongdang, 1);
        soundIncorrect = soundPool.load(this, R.raw.tick, 1);

        Bundle bundle = getIntent().getExtras();
        p_language = bundle.getString("p_language", "korean");
        p_level = bundle.getString("p_level", "1");
        p_sound = bundle.getString("p_sound", "on");

        dbOpenHelper = DBOpenHelper.getInstance(this);
        quizSet();
    }


    @Override
    public void onClick(View v) {
        String text;
        int result = 0; //0-textviewAnswer x, 1-정답 ,2-오답
        String flag = imageViewOXFlag.getTag().toString();

        switch (v.getId()) {

            //wikipedia button 추가
            case R.id.btn_Info:
                result = 0;
                String defaultUrl = "https://ko.wikipedia.org/wiki/" + country_kor;
                Intent intentWikipedia = new Intent(Intent.ACTION_VIEW, Uri.parse(defaultUrl));
                startActivity(intentWikipedia);
                break;

            case R.id.btn_O:
                if (quiz == imageFlag) result = 1;
                else result = 2;
                break;

            case R.id.btn_X:
                if (quiz != imageFlag) result = 1;
                else result = 2;
                break;

            case R.id.btn_Reset:
                quizNum = 0;
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
        if (result == 1) {
            textViewAnswer.setVisibility(View.VISIBLE);
            text = (p_language.equals("korean")) ? ("정답!!" + "\n" + flag.toString()) : ("CORRECT!!" + "\n" + flag.toString());
            textViewAnswer.setText(text);
//            textViewAnswer.setText("CORRECT!" + "\n" + flag.toString());
            textViewAnswer.setTextColor(Color.parseColor("#FF00893C"));
            score++;
            if (p_sound.equals("on")) {
                soundPool.play(soundCorrect, 1, 1, 0, 0, 1.0f);
            }


        } else if (result == 2) {
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            imageViewOXFlag.startAnimation(shake);
            textViewAnswer.setVisibility(View.VISIBLE);
            text = (p_language.equals("korean")) ? ("오답!!" + "\n" + flag.toString()) : ("INCORRECT!!" + "\n" + flag.toString());
            textViewAnswer.setText(text);
//            textViewAnswer.setText("INCORRECT!" + "\n" + flag.toString());
            textViewAnswer.setTextColor(Color.RED);
            if (p_sound.equals("on")) {
                soundPool.play(soundIncorrect, 1, 1, 0, 0, 1.0f);
            }
        }

        if (quizNum == 10) {
            quiz = "";
            quizNum = 0;
            textViewScore.setText(" Score : " + score);
            // textViewProgress.setText("game over");
            delayGameOver();
            textViewAnswer.setVisibility(View.GONE);

        } else if (result == 1 || result == 2) {
            delayResult();
        }
    }

    //퀴즈세팅
    public void quizSet() {
        String text;
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
        InputStream is = null;


//        Bitmap bitmap = null;
        quiz = arrayList.get(rid[0]).getCountry().toString();//정답
//        text = (p_language .equals( "korean")) ? country_kor : imageFlag;
//        textViewQuiz.setText(imageFlag);

        if (p_language.equals("korean")) {
            text = arrayList.get(rid[0]).getCountryKor().toString();// 정답
        } else {
            text = arrayList.get(rid[0]).getCountry().toString();
        }

        textViewQuiz.setText(text);

        int randomIndex = (int) ((Math.random()) * 10 % 2);//정답 오답을 랜덤하게 뽑음
        imageFlag = arrayList.get(rid[randomIndex]).getCountry().toString();// 정답
        country_kor = arrayList.get(rid[randomIndex]).getCountryKor().toString();

        String filename = arrayList.get(rid[randomIndex]).getContinent().toString();
        filename += "/";
        filename += arrayList.get(rid[randomIndex]).getImage().toString();

        try {
            is = am.open(filename);
            bitmap = BitmapFactory.decodeStream(is);
            // TODO : use is(InputStream).

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (is != null) {
            try {
                is.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // 이미지 적용
        imageViewOXFlag.setImageBitmap(bitmap);
        if ("korean".equals(p_language)) {
            imageViewOXFlag.setTag(arrayList.get(rid[randomIndex]).getCountryKor());
        } else if ("english".equals(p_language)) {
            imageViewOXFlag.setTag(arrayList.get(rid[randomIndex]).getCountry());
        }
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

    @Override
    protected void onStop() {
        super.onStop();

        if (soundPool != null) {
            soundPool.release();
            soundPool = null;
        }
    }
}
