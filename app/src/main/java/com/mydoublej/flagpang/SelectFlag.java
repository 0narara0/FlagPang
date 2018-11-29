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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class SelectFlag extends AppCompatActivity implements View.OnClickListener{
    int score = 0, quizNum = 0, imageCount = 4, indexCorrect;
    String country, countryQuiz;
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
        int result = 0;
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

            case R.id.flagImage1:
            case R.id.flagImage2:
            case R.id.flagImage3:
            case R.id.flagImage4:
                //if ("".equals(country)) {
                if(quizNum > 10){
                    result = 0;

                    /*Toast.makeText(this, String.valueOf(score)+"점입니다. " +
                            "게임을 계속할려면 RESET 버튼을 클릭하세요", Toast.LENGTH_LONG).show();*/
                    country = "";
                    flagProgress.setText("game over");
                    delayGameOver();
                    break;
                }
                countryQuiz = view.getTag().toString();
                if(country.equals(countryQuiz)) result = 1;
                else result = 2;
                break;
        }

        // 정답일 때
        if(result == 1){
            score++;
//             flagImage[indexCorrect].setBackgroundColor(Color.DKGRAY); //0xff3cb371
            textViewAnswer.setVisibility(View.VISIBLE);
            textViewAnswer.setText("CORRECT!!" + "\n" + country);
            textViewAnswer.setTextColor(Color.parseColor("#FF00893C"));
        }
        // 오답일 때
        else if(result == 2){
//             flagImage[indexCorrect].setBackgroundColor(Color.MAGENTA); //0xffeeb2ee
            textViewAnswer.setVisibility(View.VISIBLE);
            textViewAnswer.setText("INCORRECT!!" + "\n" + country);
            textViewAnswer.setTextColor(Color.RED);
        }
        // 배경색을 원래대로 ~
        if(result != 0) {
//            flagImage[indexCorrect].setBackgroundColor(Color.alpha(0xff778899));//0xff778899
            //QuizSet();
            delayResult();
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
        int rid =((int) (Math.random() * 100)) % member;

        // 이미지뷰에 국기 적용
        setCountryTextView(arrayList, rid);
        // 버튼에 나라 적용
        setContryImage(arrayList, rid);
    }

    public void setCountryTextView(ArrayList<GetRecord> arrayList, int rid) {
        country = arrayList.get(rid).getCountry().toString();
        flagSelectCountry.setText(country);
    }

    public void setContryImage (ArrayList<GetRecord>  arrayList, int randomID) {

        // db에서 랜덤하게 나라 가져옴.
        HashSet<Integer> setDBPK = new HashSet<>();
        setDBPK.add(randomID);// image에 적용한 나라 넣어줌.
        int arraySize = arrayList.size();
        while (setDBPK.size() < imageCount) {// set은 중복된 값을 허용하지 않음. 다른 수 4개가 저장되면 루프 탈출
            int num = (int)(Math.random() * 100) % arraySize +1;
            setDBPK.add(num);
        }

        // 이미지 가져오기
        AssetManager am = getResources().getAssets();
        InputStream is = null ;
        int index, id, imgindex;

        // 이미지 인덱스 랜덤하게
        HashSet<Integer> setImageIndex = new HashSet<>();
        while(setImageIndex.size() < imageCount) {
            int num = (int)(Math.random() * 100) % imageCount;
            setImageIndex.add(num);
        }

        Iterator<Integer> iterDBPK =  setDBPK.iterator();
        Iterator<Integer> iterImageIndex = setImageIndex.iterator();
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
            if(country.equals(countryQuiz)) indexCorrect = index;
        }
    }

    public void delayResult() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

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
        finish();
        super.onPause();
    }
}

