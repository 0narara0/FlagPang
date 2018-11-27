package com.mydoublej.flagpang;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class SelectOX extends AppCompatActivity implements View.OnClickListener {

    ImageView imageViewOXFlag;
    TextView textViewQuiz, textViewProgress, textViewScore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_ox);

        imageViewOXFlag = findViewById(R.id.imageViewOXFlag);
        ((ImageButton)findViewById(R.id.btn_O)).setOnClickListener(this);
        ((ImageButton)findViewById(R.id.btn_X)).setOnClickListener(this);
        ((Button)findViewById(R.id.btn_Reset)).setOnClickListener(this);
        ((Button)findViewById(R.id.btn_Main)).setOnClickListener(this);
        textViewQuiz = findViewById(R.id.textViewQuiz);
        textViewProgress = findViewById(R.id.textViewProgress);
        textViewScore = findViewById(R.id.textViewScore);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn_O:

                break;

            case R.id.btn_X:

                break;

            case R.id.btn_Reset:

                break;

            case R.id.btn_Main:

                break;

        }

    }

    public void quizSet(){

    }
}
