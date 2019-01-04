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
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;

public class SelectFlag extends AppCompatActivity implements View.OnClickListener {
    int score = 0, quizNum = 0, quizTotal = 10, imageCount = 4, correctIndex, mTime;
    String p_level = "1", p_language = "korean", p_sound = "on", p_time;
    String country, country_kor, countryQuiz;
    private DBOpenHelper dbOpenHelper;
    TextView flagScore, flagProgress, flagSelectCountry, textViewAnswer;
    ImageView[] flagImage = new ImageView[imageCount];
    Handler handler = new Handler();
    SoundPool soundPool;
    int soundCorrect, soundIncorrect;
    ImageView imageViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_flag);

        Bundle bundle = getIntent().getExtras();
        p_language = bundle.getString("p_language", "korean");
        p_level = bundle.getString("p_level", "1");
        p_sound = bundle.getString("p_sound", "on");
        p_time = bundle.getString("p_time", "1000");
        mTime = Integer.parseInt(p_time);

        flagScore = findViewById(R.id.flagScore);
        flagProgress = findViewById(R.id.flagProgress);
        flagSelectCountry = findViewById(R.id.flagSelectCountry);
        flagImage[0] = findViewById(R.id.flagImage1);
        flagImage[1] = findViewById(R.id.flagImage2);
        flagImage[2] = findViewById(R.id.flagImage3);
        flagImage[3] = findViewById(R.id.flagImage4);
        for (int i = 0; i < imageCount; i++) flagImage[i].setOnClickListener(this);
        ((Button) findViewById(R.id.flagInfo)).setOnClickListener(this);
        ((Button) findViewById(R.id.flagReset)).setOnClickListener(this);
        ((Button) findViewById(R.id.flagMain)).setOnClickListener(this);
        textViewAnswer = findViewById(R.id.textViewAnswer);
        imageViewResult = findViewById(R.id.imageViewResult);

        //사운드
        soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
        soundCorrect = soundPool.load(this, R.raw.dingdongdang, 1);
        soundIncorrect = soundPool.load(this, R.raw.tick, 1);

        dbOpenHelper = DBOpenHelper.getInstance(this);
        QuizSet();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            //wikipedia button 추가
            case R.id.flagInfo:
                String defaultUrl = "https://ko.wikipedia.org/wiki/" + country_kor;
                Intent intentWikipedia = new Intent(Intent.ACTION_VIEW, Uri.parse(defaultUrl));
                startActivity(intentWikipedia);
                break;

            case R.id.flagReset:
                score = 0;
                quizNum = 0;
                imageViewResult.setVisibility(View.GONE);
                QuizSet();
                break;
            case R.id.flagMain:
                Intent intent = new Intent();
                intent.putExtra("result_msg", 300);
                setResult(RESULT_OK, intent);
                finish();
                break;

            case R.id.flagImage1:
            case R.id.flagImage2:
            case R.id.flagImage3:
            case R.id.flagImage4:
                pressImageButton(view);
                break;
        }

    }

    private void pressImageButton(View view) {
        String text;

        countryQuiz = view.getTag().toString();

        if (country.equals(countryQuiz)) {  //정답일때...
            score++;
            flagScore.setText(" Score : " + score);
            textViewAnswer.setVisibility(View.VISIBLE);
            text = (p_language.equals("korean")) ? (country_kor) : (country);
            textViewAnswer.setText(text);
            textViewAnswer.setTextColor(Color.parseColor("#FF00893C"));

            imageViewResult.setVisibility(View.VISIBLE);
            imageViewResult.setImageResource(R.drawable.correct);

            if (p_sound.equals("on")) {
                soundPool.play(soundCorrect, 1, 1, 0, 0, 1.0f);
            }
        } else {  //오답일때...
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            ((ImageView) findViewById(view.getId())).startAnimation(shake);
            textViewAnswer.setVisibility(View.VISIBLE);
            text = (p_language.equals("korean")) ? (country_kor) : (country);
            textViewAnswer.setText(text);
            textViewAnswer.setTextColor(Color.RED);

            imageViewResult.setVisibility(View.VISIBLE);
            imageViewResult.setImageResource(R.drawable.incorrect);

            if (p_sound.equals("on")) {
                soundPool.play(soundIncorrect, 1, 1, 0, 0, 1.0f);
            }
        }

        //정답 테두리
        flagImage[correctIndex].setBackgroundResource(R.drawable.stroke);

        if (quizNum >= quizTotal) {
            delayGameOver();

        } else {
            delayResult(view);
        }
    }

    //퀴즈 새로 셋팅
    public void QuizSet() {
        textViewAnswer.setVisibility(View.GONE);
        imageViewResult.setVisibility(View.GONE);
        ++quizNum;
        flagScore.setText(" Score : " + score);
        flagProgress.setText(quizNum + " of 10");
        flagImage[correctIndex].setBackgroundResource(R.drawable.solid);
        textViewAnswer.setVisibility(View.GONE);


        // DB 가져오기
        ArrayList<GetRecord> arrayList = dbOpenHelper.selectGetRecord(p_level);
        int member = arrayList.size();
        int rid = (int) (Math.random() * member);

        // 이미지뷰에 국기 적용
        setCountryTextView(arrayList, rid);
        // 버튼에 나라 적용
        setContryImage(arrayList, rid);
    }

    public void setCountryTextView(ArrayList<GetRecord> arrayList, int rid) {
        String text;
        country = arrayList.get(rid).getCountry().toString();
        country_kor = arrayList.get(rid).getCountryKor().toString();

        text = (p_language.equals("korean")) ? country_kor : country;
        flagSelectCountry.setText(text);
    }

    public void setContryImage(ArrayList<GetRecord> arrayList, int randomID) {

        // db에서 랜덤하게 나라 가져옴.
        LinkedHashSet<Integer> setDBPK = new LinkedHashSet<>();
        setDBPK.add(randomID);// image에 적용한 나라 넣어줌.
        int arraySize = arrayList.size();
        while (setDBPK.size() < imageCount) {// set은 중복된 값을 허용하지 않음. 다른 수 4개가 저장되면 루프 탈출
            int num = (int) (Math.random() * arraySize);
            setDBPK.add(num);
        }

        // 이미지 가져오기
        AssetManager am = getResources().getAssets();
        InputStream is = null;
        int index, id;

        // 이미지 인덱스 랜덤하게
        ArrayList<Integer> listIndex = new ArrayList<Integer>();
        listIndex.add(0);
        listIndex.add(1);
        listIndex.add(2);
        listIndex.add(3);

        Collections.shuffle(listIndex);

        Iterator<Integer> iterDBPK = setDBPK.iterator();
        Iterator<Integer> iterImageIndex = listIndex.iterator();
        while (iterDBPK.hasNext() && iterImageIndex.hasNext()) {
            index = iterImageIndex.next();
            id = iterDBPK.next();
            countryQuiz = arrayList.get(id).getCountry().toString();
            String filename = arrayList.get(id).getContinent().toString();
            filename += "/";
            filename += arrayList.get(id).getImage().toString();
            Bitmap bitmap = null;

            try {
                is = am.open(filename);
                bitmap = BitmapFactory.decodeStream(is);
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("country", filename);
            }

            if (is != null) {
                try {
                    is.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            // 이미지 적용
            flagImage[index].setImageBitmap(bitmap);
            flagImage[index].setTag(countryQuiz);
            if (country.equals(countryQuiz)) correctIndex = index;
        }
    }

    public void delayResult(final View view) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                flagImage[correctIndex].setBackgroundResource(R.drawable.solid);
                QuizSet();
                textViewAnswer.setVisibility(View.GONE);
                imageViewResult.setVisibility(View.GONE);
            }
        }, mTime);//지연
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
                        QuizSet();
                    }
                });
        builder.show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        soundCorrect = soundPool.load(this, R.raw.dingdongdang, 1);
        soundIncorrect = soundPool.load(this, R.raw.tick, 1);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();

        soundPool.unload(soundCorrect);
        soundPool.unload(soundIncorrect);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (soundPool != null) {
            soundPool.release();
        }
    }
}

