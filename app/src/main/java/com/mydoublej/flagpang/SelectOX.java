package com.mydoublej.flagpang;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

public class SelectOX extends AppCompatActivity implements View.OnClickListener {

    ImageView imageViewOXFlag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_ox);

        imageViewOXFlag = findViewById(R.id.imageViewOXFlag);
        ((ImageButton)findViewById(R.id.btn_O)).setOnClickListener(this);
        ((ImageButton)findViewById(R.id.btn_X)).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

    }
}
