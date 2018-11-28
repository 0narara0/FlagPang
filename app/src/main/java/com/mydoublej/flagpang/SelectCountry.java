package com.mydoublej.flagpang;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class SelectCountry extends AppCompatActivity implements View.OnClickListener{
    int score = 0, quizNum = 0, buttonCount = 4;
    private  DBOpenHelper dbOpenHelper;
    TextView textViewScore, textViewProgress, textViewSelectCountry;
    ImageView imageViewFlag;
    Button[] buttonCountry = new Button[buttonCount];
    Button buttonReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_country);

        textViewScore = findViewById(R.id.textViewScore);
        textViewProgress = findViewById(R.id.flagProgress);
        textViewSelectCountry = findViewById(R.id.textViewSelectCountry);
        imageViewFlag = findViewById(R.id.imageViewFlag);
        (buttonReset = findViewById(R.id.buttonReset)).setOnClickListener(this);
        (buttonCountry[0] = findViewById(R.id.buttonCountry1)).setOnClickListener(this);
        (buttonCountry[1] = findViewById(R.id.buttonCountry2)).setOnClickListener(this);
        (buttonCountry[2] = findViewById(R.id.buttonCountry3)).setOnClickListener(this);
        (buttonCountry[3] = findViewById(R.id.buttonCountry4)).setOnClickListener(this);

        dbOpenHelper = DBOpenHelper.getInstance(this);
        QuizSet();
    }

    @Override
    public void onClick(View view) {
        // Reset 버튼 눌렀을 때
        if(view.getId() == (R.id.buttonReset)){
            score = 0;
            quizNum = 0;
            QuizSet();
        }

        // 정답일 때
        else if(view.getTag().equals(imageViewFlag.getTag())){
            Toast.makeText(this, "정답입니다!", Toast.LENGTH_SHORT).show();
            score++;
            QuizSet();
        }

        // 오답일 때
        else {
            Toast.makeText(this, "오답입니다!", Toast.LENGTH_SHORT).show();
            view.setEnabled(false);
        }
    }

    //퀴즈 새로 셋팅
    public void QuizSet(){
        quizNum++;
        textViewScore.setText(" Score : " + score);
        textViewProgress.setText(quizNum + " of 10");

        // 버튼 활성화
        for(int i = 0; i < 4; i++) {
            buttonCountry[i].setEnabled(true);
        }

        // DB 가져오기
        ArrayList<GetRecord> arrayList = dbOpenHelper.selectGetRecord();
        int member = arrayList.size();
        int rid =((int) (Math.random() * 100)) % member;

        // 이미지뷰에 국기 적용
        setCountryImageView(arrayList, rid);
        // 버튼에 나라 적용
        setContryButton(arrayList, rid);
    }

    public void setCountryImageView(ArrayList<GetRecord> arrayList, int rid){

        // 이미지 가져오기
        AssetManager am = getResources().getAssets();
        InputStream is = null ;

        // String filename = "oceania/austria.png";
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

        // 이미지 적용
        imageViewFlag.setImageBitmap(bitmap);
        imageViewFlag.setTag(arrayList.get(rid).getCountry());
    }

    public void setContryButton (ArrayList<GetRecord>  arrayList, int randomID) {

        // db에서 랜덤하게 나라 가져옴.
        HashSet<Integer> setDBPK = new HashSet<>();
        setDBPK.add(randomID);// image에 적용한 나라 넣어줌.
        int arraySize = arrayList.size();
        while (setDBPK.size() < buttonCount) {// set은 중복된 값을 허용하지 않음. 다른 수 4개가 저장되면 루프 탈출
            int num = (int)(Math.random() * 100) % arraySize +1;
            setDBPK.add(num);
        }

        // 버튼 인덱스 랜덤하게
        HashSet<Integer> setButtonIndex = new HashSet<>();
        while(setButtonIndex.size() < buttonCount) {
            int num = (int)(Math.random() * 100) % buttonCount;
            setButtonIndex.add(num);
        }

        // 버튼에 텍스트 적용
        Iterator<Integer> iterDBPK =  setDBPK.iterator();
        Iterator<Integer> iterButtonIndex = setButtonIndex.iterator();
        while(iterDBPK.hasNext() && iterButtonIndex.hasNext()) {
            int index = iterButtonIndex.next();
            String country = arrayList.get(iterDBPK.next()).getCountry();
            buttonCountry[index].setText(country);
            buttonCountry[index].setTag(country);
        }
    }
}
