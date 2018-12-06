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
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;

public class SelectFlag extends AppCompatActivity implements View.OnClickListener{
    int score = 0, quizNum = 0, imageCount = 4, correctIndex,index;
    String country, country_kor, countryQuiz;
    private  DBOpenHelper dbOpenHelper;
    TextView flagScore, flagProgress, flagSelectCountry, textViewAnswer;
//    ImageView flagImage1,flagImage2,flagImage3,flagImage4;
    ImageView[] flagImage = new ImageView[imageCount];
    Button flagReset,flagMain;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_flag);

        flagScore = findViewById(R.id.flagScore);
        flagProgress = findViewById(R.id.flagProgress);
        flagSelectCountry = findViewById(R.id.flagSelectCountry);
        flagImage[0] = findViewById(R.id.flagImage1);
        flagImage[1] = findViewById(R.id.flagImage2);
        flagImage[2] = findViewById(R.id.flagImage3);
        flagImage[3] = findViewById(R.id.flagImage4);
        for(int i=0; i < imageCount; i++)flagImage[i].setOnClickListener(this);
        ((Button)findViewById(R.id.flagReset)).setOnClickListener(this);
        ((Button)findViewById(R.id.flagMain)).setOnClickListener(this);
        textViewAnswer = findViewById(R.id.textViewAnswer);

        dbOpenHelper = DBOpenHelper.getInstance(this);
        QuizSet();
    }

    @Override
    public void onClick(View view) {
        index = 5;
        switch(view.getId()){
            case R.id.flagReset:
                score = 0;
                quizNum = 0;
                QuizSet();
                break;
            case R.id.flagMain:
                Intent intent = new Intent();
                intent.putExtra("result_msg", 300);
                setResult(RESULT_OK, intent);
                finish();
                break;

            case R.id.flagImage1: index = 0; break;
            case R.id.flagImage2: index = 1; break;
            case R.id.flagImage3: index = 2; break;
            case R.id.flagImage4: index = 3; break;
        }
        if(index != 5) {
            countryQuiz = view.getTag().toString();
            if (country.equals(countryQuiz)) {  //정답일때...
                score++;
                textViewAnswer.setVisibility(View.VISIBLE);
                textViewAnswer.setText("CORRECT!!" + "\n" + country);
                textViewAnswer.setTextColor(Color.parseColor("#FF00893C"));
             }
            else {  //오답일때...
                Animation shake = AnimationUtils.loadAnimation(this,R.anim.shake);
                flagImage[index].startAnimation(shake);
                textViewAnswer.setVisibility(View.VISIBLE);
                textViewAnswer.setText("INCORRECT!!" + "\n" + country);
                textViewAnswer.setTextColor(Color.RED);
            }
        }

        flagImage[correctIndex].setBackgroundResource(R.drawable.stroke);
        if(quizNum >= 10){
                    /*Toast.makeText(this, String.valueOf(score)+"점입니다. " +
                            "게임을 계속할려면 RESET 버튼을 클릭하세요", Toast.LENGTH_LONG).show();*/
            country = "";
            quizNum = 0;
            flagScore.setText(" Score : " + score);
            flagProgress.setText("game over");
            delayGameOver();
            flagImage[correctIndex].setBackgroundResource(R.drawable.solid);
            textViewAnswer.setVisibility(View.GONE);

        }
        else{
            delayResult(view);
        }

    }

    //퀴즈 새로 셋팅
    public void QuizSet(){
        ++quizNum;
        /*if(++quizNum > 10) {
            country = "";
            flagProgress.setText("game over");
            return;
        }*/

        flagScore.setText(" Score : " + score);
        flagProgress.setText(quizNum + " of 10");

         // DB 가져오기
        ArrayList<GetRecord> arrayList = dbOpenHelper.selectGetRecord();
        int member = arrayList.size();
        int rid =(int) (Math.random() * member);

        // 이미지뷰에 국기 적용
        setCountryTextView(arrayList, rid);
        // 버튼에 나라 적용
        setContryImage(arrayList, rid);
    }

    public void setCountryTextView(ArrayList<GetRecord> arrayList, int rid) {
        country = arrayList.get(rid).getCountry().toString();
        country_kor = arrayList.get(rid).getCountryKor().toString();

 //       flagSelectCountry.setText(country);
        flagSelectCountry.setText(country_kor);
    }

    public void setContryImage (ArrayList<GetRecord>  arrayList, int randomID) {

        // db에서 랜덤하게 나라 가져옴.
        LinkedHashSet<Integer> setDBPK = new LinkedHashSet<>();
        setDBPK.add(randomID);// image에 적용한 나라 넣어줌.
        int arraySize = arrayList.size();
        while (setDBPK.size() < imageCount) {// set은 중복된 값을 허용하지 않음. 다른 수 4개가 저장되면 루프 탈출
            int num = (int)(Math.random() * arraySize);
            setDBPK.add(num);
        }

        // 이미지 가져오기
        AssetManager am = getResources().getAssets();
        InputStream is = null ;
        int index, id, imgindex;

        // 이미지 인덱스 랜덤하게
        ArrayList<Integer> listIndex = new ArrayList<Integer>();
        listIndex.add(0);
        listIndex.add(1);
        listIndex.add(2);
        listIndex.add(3);

        Collections.shuffle(listIndex);
        correctIndex = listIndex.get(0);

        Iterator<Integer> iterDBPK =  setDBPK.iterator();
        Iterator<Integer> iterImageIndex = listIndex.iterator();
        while(iterDBPK.hasNext() && iterImageIndex.hasNext()) {
            index = iterImageIndex.next();
            id = iterDBPK.next();
            countryQuiz = arrayList.get(id).getCountry().toString();
            String filename = arrayList.get(id).getContinent().toString();
            filename += "/";
            filename += arrayList.get(id).getImage().toString();
            Bitmap bitmap = null;

            try {
                is = am.open(filename) ;
                bitmap = BitmapFactory.decodeStream(is);
            } catch (Exception e) {
                e.printStackTrace() ;
                Log.d("country", filename);
            }

            if (is != null) {
                try {
                    is.close() ;
                } catch (Exception e) {
                    e.printStackTrace() ;
                }
            }
            // 이미지 적용
            flagImage[index].setImageBitmap(bitmap);
            flagImage[index].setTag(countryQuiz);
            if(country.equals(countryQuiz)) correctIndex = index;
        }
    }

    public void delayResult(final View view) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                flagImage[correctIndex].setBackgroundResource(R.drawable.solid);
                QuizSet();
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
                        QuizSet();
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

