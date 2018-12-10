package com.mydoublej.flagpang;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;

public class SelectCountry extends AppCompatActivity implements View.OnClickListener{
    int score = 0, quizNum = 0, quizTotal = 10, buttonCount = 4;
    private  DBOpenHelper dbOpenHelper;
    TextView textViewScore, textViewProgress, textViewSelectCountry, textViewAnswer;
    ImageView imageViewFlag;
    Button[] buttonCountry = new Button[buttonCount];
    Button buttonReset, buttonMain;
    Handler handler = new Handler();
    private int correctIndex = 0;
    SoundPool soundPool;
    int soundCorrect, soundIncorrect;
    private int randomFlag;
    String p_level, p_language, p_sound;


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

        Bundle bundle = getIntent().getExtras();
        p_level = bundle.getString("p_level", "1");
        p_language = bundle.getString("p_language", "korean");
        p_sound = bundle.getString("p_sound", "on");

        //사운드
        soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
        soundCorrect = soundPool.load(this, R.raw.polong, 1);
        soundIncorrect = soundPool.load(this, R.raw.tick, 1);

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
            textViewAnswer.setText("CORRECT!" + "\n" + flag.toString());
            textViewAnswer.setTextColor(Color.parseColor("#FF00893C"));
            if(p_sound.equals("on")) {
                soundPool.play(soundCorrect, 1, 1, 0, 0, 1.0f);
            }
        }
        // 오답일 때
        else {
            Animation shake = AnimationUtils.loadAnimation(this,R.anim.shake);
            imageViewFlag.startAnimation(shake);
            textViewAnswer.setText("INCORRECT!" + "\n" + flag.toString());
            textViewAnswer.setTextColor(Color.RED);
            if(p_sound.equals("on")) {
                soundPool.play(soundIncorrect, 1, 1, 0, 0, 1.0f);
            }
        }

        //정답 테두리
        buttonCountry[correctIndex].setBackgroundResource(R.drawable.stroke);

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

        // 버튼 바탕색 넣어줌
        buttonCountry[correctIndex].setBackgroundColor(0xFF8498CA);

        // 버튼 활성화
        for(int i = 0; i < buttonCount; i++)
            buttonCountry[i].setEnabled(true);

        // DB 가져오기
        ArrayList<GetRecord> arrayList = dbOpenHelper.selectGetRecord(p_level);
        int member = arrayList.size();
        randomFlag =(int) (Math.random() * member);

        // 이미지뷰에 국기 적용
        setCountryImageView(arrayList, randomFlag);
        // 버튼에 나라 적용
        setContryButton(arrayList, randomFlag);
    }

    public void setCountryImageView(ArrayList<GetRecord> arrayList, int randomId){

        // 이미지 가져오기
        AssetManager am = getResources().getAssets();
        InputStream is = null ;

        // String filename = "oceania/austria.png";
        String filename = arrayList.get(randomFlag).getContinent().toString();
        filename += "/";
        filename += arrayList.get(randomFlag).getImage().toString();
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
        if("korean".equals(p_language)){
            imageViewFlag.setTag(arrayList.get(randomFlag).getCountryKor());
        }
        else if("english".equals(p_language)){
            imageViewFlag.setTag(arrayList.get(randomFlag).getCountry());
        }
    }

    public void setContryButton (ArrayList<GetRecord>  arrayList, int randomFlag) {

        // db에서 랜덤하게 나라 가져옴.
        LinkedHashSet<Integer> setDBPK = new LinkedHashSet<>();
        setDBPK.add(randomFlag);// image에 적용한 나라 넣어줌.
        int arraySize = arrayList.size();
        while (setDBPK.size() < buttonCount) {// set은 중복된 값을 허용하지 않음. 다른 수 4개가 저장되면 루프 탈출
            int num = (int)(Math.random() * arraySize);
            setDBPK.add(num);
        }

        // 버튼 인덱스 랜덤하게
        ArrayList<Integer> listIndex = new ArrayList<>();
        listIndex.add(0);
        listIndex.add(1);
        listIndex.add(2);
        listIndex.add(3);
        Collections.shuffle(listIndex);
        correctIndex = listIndex.get(0);

        // 버튼에 텍스트 적용
        Iterator<Integer> iterDBPK =  setDBPK.iterator();
        Iterator<Integer> iterButtonIndex = listIndex.iterator();
        while(iterDBPK.hasNext() && iterButtonIndex.hasNext()) {
            int index = iterButtonIndex.next();

            String country = null;
            if("korean".equals(p_language)){
                country = arrayList.get(iterDBPK.next()).getCountryKor();
            }
            else if("english".equals(p_language)){
                country = arrayList.get(iterDBPK.next()).getCountry();
            }

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
//      finish();
    }
}
