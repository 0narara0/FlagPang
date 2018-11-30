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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class SelectCountry extends AppCompatActivity implements View.OnClickListener{
    int score = 0, quizNum = 0, quizTotal = 10, buttonCount = 4;
    private  DBOpenHelper dbOpenHelper;
    TextView textViewScore, textViewProgress, textViewSelectCountry, textViewAnswer;
    ImageView imageViewFlag;
    Button[] buttonCountry = new Button[buttonCount];
    Button buttonReset, buttonMain;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_country);

        textViewScore = findViewById(R.id.textViewScore);
        textViewProgress = findViewById(R.id.textViewProgress);
        textViewSelectCountry = findViewById(R.id.textViewSelectCountry);
        textViewAnswer = findViewById(R.id.textViewAnswer);
        imageViewFlag = findViewById(R.id.imageViewFlag);
        (buttonReset = findViewById(R.id.buttonReset)).setOnClickListener(this);
        (buttonMain = findViewById(R.id.buttonMain)).setOnClickListener(this);
        (buttonCountry[0] = findViewById(R.id.buttonCountry1)).setOnClickListener(this);
        (buttonCountry[1] = findViewById(R.id.buttonCountry2)).setOnClickListener(this);
        (buttonCountry[2] = findViewById(R.id.buttonCountry3)).setOnClickListener(this);
        (buttonCountry[3] = findViewById(R.id.buttonCountry4)).setOnClickListener(this);

        dbOpenHelper = DBOpenHelper.getInstance(this);
        QuizSet();
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.buttonReset:
                Init();
                QuizSet();
                break;

            case R.id.buttonMain:
                Intent intent = new Intent();
                intent.putExtra("result_msg", 100);
                setResult(RESULT_OK, intent);
                finish();
                break;

            // 나라 버튼
            default:
                pressNationButton(view);
                break;

        }
    }

    public void pressNationButton(View view){
        String nation = view.getTag().toString();
        String flag = imageViewFlag.getTag().toString();
        textViewAnswer.setVisibility(View.VISIBLE);
        for (int i = 0; i < buttonCount; i++)
            buttonCountry[i].setEnabled(false);// 나라 버튼 모두 활성화

        // 정답일 때
        if (nation.equals(flag)) {
            score++;
            textViewScore.setText(" Score : " + score);
            textViewAnswer.setText("Correct!" + "\n" + flag.toString());
            textViewAnswer.setTextColor(Color.parseColor("#FF00893C"));
        }
        // 오답일 때
        else {
            textViewAnswer.setText("Incorrect!" + "\n" + flag.toString());
            textViewAnswer.setTextColor(Color.RED);
        }

        // 문제 다 풀었는지 체크
        if(quizNum >= quizTotal)
            delayGameOver();
        else
            delayResult();
    }

    public void Init(){
        quizNum = 0;
        score = 0;
        textViewAnswer.setVisibility(View.GONE);
    }

    //퀴즈 새로 셋팅
    public void QuizSet(){
        quizNum++;
        textViewScore.setText(" Score : " + score);
        textViewProgress.setText(quizNum + " of 10");

        // 버튼 활성화
        for(int i = 0; i < buttonCount; i++)
            buttonCountry[i].setEnabled(true);

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
                        Init();
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
