package com.mydoublej.flagpang;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class FlagPang extends AppCompatActivity implements View.OnClickListener {
    private DBOpenHelper dbOpenHelper;
    private SQLiteDatabase mdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((Button)findViewById(R.id.btn_country)).setOnClickListener(this);
        ((Button)findViewById(R.id.btn_ox)).setOnClickListener(this);
        ((Button)findViewById(R.id.btn_flag)).setOnClickListener(this);

        ((ImageView)findViewById(R.id.imageView)).setImageResource(R.drawable.ag);

        dbOpenHelper = DBOpenHelper.getInstance(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_country:
                Intent intentCountry = new Intent(this,SelectCountry.class);
                startActivityForResult(intentCountry,100);
                break;
            case R.id.btn_ox:
                Intent intentOX = new Intent(this,SelectOX.class);
                startActivityForResult(intentOX,100);
                break;
            case R.id.btn_flag:
                Intent intentFlag = new Intent(this,SelectFlag.class);
                startActivityForResult(intentFlag,100);
                break;
        }

    }
}
