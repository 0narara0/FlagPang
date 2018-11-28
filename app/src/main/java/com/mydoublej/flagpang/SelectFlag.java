package com.mydoublej.flagpang;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    int score = 0, quizNum = 0, imageCount = 4;
    String country, countryQuiz;
    private  DBOpenHelper dbOpenHelper;
    TextView flagScore, flagProgress, flagSelectCountry;
    ImageView flagImage1,flagImage2,flagImage3,flagImage4;
    Button flagReset,flagMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_flag);

        flagScore = findViewById(R.id.flagScore);
        flagProgress = findViewById(R.id.flagProgress);
        flagSelectCountry = findViewById(R.id.flagSelectCountry);
        flagImage1 = findViewById(R.id.flagImage1);
        flagImage2 = findViewById(R.id.flagImage2);
        flagImage3 = findViewById(R.id.flagImage3);
        flagImage4 = findViewById(R.id.flagImage4);
        flagImage1.setOnClickListener(this);
        flagImage2.setOnClickListener(this);
        flagImage3.setOnClickListener(this);
        flagImage4.setOnClickListener(this);
        ((Button)findViewById(R.id.flagReset)).setOnClickListener(this);
        ((Button)findViewById(R.id.flagMain)).setOnClickListener(this);

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
                Intent intent = new Intent(this, FlagPang.class);
                startActivity(intent);
                break;
            case R.id.flagImage1:
            case R.id.flagImage2:
            case R.id.flagImage3:
            case R.id.flagImage4:
                countryQuiz = view.getTag().toString();
                if(country.equals(countryQuiz)) result = 1;
                else result = 2;
                break;
        }

        // 정답일 때
        if(result == 1){
            Toast.makeText(this, "정답입니다!", Toast.LENGTH_SHORT).show();
            score++;
            QuizSet();
        }

        // 오답일 때
        else if(result == 2){
            Toast.makeText(this, "오답입니다!", Toast.LENGTH_SHORT).show();
            QuizSet();

        }
    }

    //퀴즈 새로 셋팅
    public void QuizSet(){
        if(++quizNum > 10) {
            quizNum = 1;
        }
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
        int index, id;
        String imageViewName = null;

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
            String country = arrayList.get(id).getCountry().toString();
            String filename = arrayList.get(id).getContinent().toString();
            filename += "/";
            filename += arrayList.get(id).getImage().toString();
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
            // 이미지 적용
            switch (index){
                case 0:
                    flagImage1.setImageBitmap(bitmap);
                    flagImage1.setTag(country);
                    break;
                case 1:
                    flagImage2.setImageBitmap(bitmap);
                    flagImage2.setTag(country);
                    break;
                case 2:
                    flagImage3.setImageBitmap(bitmap);
                    flagImage3.setTag(country);
                    break;
                case 3:
                    flagImage4.setImageBitmap(bitmap);
                    flagImage4.setTag(country);
                    break;
            }
        }
    }

    @Override
    protected void onPause() {
        finish();
        super.onPause();
    }
}

