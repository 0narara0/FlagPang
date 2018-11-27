package com.mydoublej.flagpang;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBOpenHelper extends SQLiteOpenHelper {
    private  static DBOpenHelper instance;
    private  static SQLiteDatabase mdb;
    public static final  String DB_NAME = "flagpang.db";
    private static  final SQLiteDatabase.CursorFactory FACTORY = null;
    public  static final  int VERSION = 1;

    String sql;
    Cursor cursor;

    public  static DBOpenHelper getInstance(Context context) {
        if(instance == null){
            instance = new DBOpenHelper(context);
        }
        mdb = instance.getWritableDatabase();
        return instance;
    }


    private DBOpenHelper(Context context){
        super(context, DB_NAME, FACTORY, VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        sql = "CREATE TABLE flag" +
                "(id integer NOT NULL UNIQUE PRIMARY KEY AUTOINCREMENT, " +
                "country text NOT NULL UNIQUE, " +
                "continent text NOT NULL, " +
                "image text NOT NULL UNIQUE," +
                "level text NOT NULL);";
        db.execSQL(sql);

       table_inputdata(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE flag");
        onCreate(db);
    }

    private void table_inputdata(SQLiteDatabase db){
//        sql = "INSERT INTO flag " +
//                "VALUES(null, 'austria', 'oceania', 'austria.png','1')";
//        db.execSQL(sql);

        //south_america
        sql = "INSERT INTO flag " +
                "VALUES(null, 'argentina', 'samerica', 'argentina.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'bolivia', 'samerica', 'bolivia.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'brazil', 'samerica', 'brazil.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'chile', 'samerica', 'chile.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'colombia', 'samerica', 'colombia.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'ecuador', 'samerica', 'ecuador.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'guyana', 'samerica', 'guyana.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'paraguay', 'samerica', 'paraguay.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'peru', 'samerica', 'peru.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'suriname', 'samerica', 'suriname.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'trinidad_and_tobago', 'samerica', 'trinidad_and_tobago.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'uruguay', 'samerica', 'uruguay.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'venezuela', 'samerica', 'venezuela.png','1')";
        db.execSQL(sql);


        //europe
        sql = "INSERT INTO flag " +
                "VALUES(null, 'albania', 'europe', 'albania.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'andorra', 'europe', 'andorra.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'austria', 'europe', 'austria.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'belarus', 'europe', 'belarus.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'belgium', 'europe', 'belgium.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'bosnia_and_herzegovina', 'europe', 'bosnia_and_herzegovina.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'bulgaria', 'europe', 'bulgaria.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'croatia', 'europe', 'croatia.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'denmark', 'europe', 'denmark.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'estonia', 'europe', 'estonia.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'finland', 'europe', 'finland.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'france', 'europe', 'france.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'germany', 'europe', 'germany.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'greece', 'europe', 'greece.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'hungary', 'europe', 'hungary.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'iceland', 'europe', 'iceland.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'ireland', 'europe', 'ireland.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'italy', 'europe', 'italy.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'kosovo', 'europe', 'kosovo.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'latvia', 'europe', 'latvia.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'liechtenstein', 'europe', 'liechtenstein.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'lithuania', 'europe', 'lithuania.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'luxembourg', 'europe', 'luxembourg.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'macedonia', 'europe', 'macedonia.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'malta', 'europe', 'malta.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'moldova', 'europe', 'moldova.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'monaco', 'europe', 'monaco.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'montenegro', 'europe', 'montenegro.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'norway', 'europe', 'norway.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'poland', 'europe', 'poland.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'portugal', 'europe', 'portugal.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'romania', 'europe', 'romania.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'russia', 'europe', 'russia.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'san_marino', 'europe', 'san_marino.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'serbia', 'europe', 'serbia.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'slovakia', 'europe', 'slovakia.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'slovenia', 'europe', 'slovenia.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'spain', 'europe', 'spain.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'sweden', 'europe', 'sweden.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'switzerland', 'europe', 'switzerland.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'the_czech_republic', 'europe', 'the_czech_republic.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'the_netherlands', 'europe', 'the_netherlands.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'the_united_kingdom', 'europe', 'the_united_kingdom.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'the_vatican_city', 'europe', 'the_vatican_city.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'turkey', 'europe', 'turkey.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'ukraine', 'europe', 'ukraine.png','1')";
        db.execSQL(sql);

        //africa
        sql = "INSERT INTO flag " +
                "VALUES(null, 'algeria', 'africa', 'algeria.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'angola', 'africa', 'angola.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'benin', 'africa', 'benin.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'botswana', 'africa', 'botswana.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'burkina', 'africa', 'burkina.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'burundi', 'africa', 'burundi.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'cameroon', 'africa', 'cameroon.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'cape_verde', 'africa', 'cape_verde.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'chad', 'africa', 'chad.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'cote_d_ivoire', 'africa', 'cote_d_ivoire.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'djibouti', 'africa', 'djibouti.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'egypt', 'africa', 'egypt.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'equatorial_guinea', 'africa', 'equatorial_guinea.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'eritrea', 'africa', 'eritrea.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'ethiopia', 'africa', 'ethiopia.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'gabon', 'africa', 'gabon.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'ghana', 'africa', 'ghana.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'guinea', 'africa', 'guinea.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'guinea_bissau', 'africa', 'guinea_bissau.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'kenya', 'africa', 'kenya.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'lesotho', 'africa', 'lesotho.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'liberia', 'africa', 'liberia.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'libya', 'africa', 'libya.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'madagascar', 'africa', 'madagascar.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'malawi', 'africa', 'malawi.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'mali', 'africa', 'mali.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'mauritania', 'africa', 'mauritania.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'mauritius', 'africa', 'mauritius.png','1')";
        db.execSQL(sql);

        sql = "INSERT INTO flag " +
                "VALUES(null, 'morocco', 'africa', 'morocco.png','1')";
        db.execSQL(sql);

        sql = "INSERT INTO flag " +
                "VALUES(null, 'mozambique', 'africa', 'mozambique.png','1')";
        db.execSQL(sql);

        sql = "INSERT INTO flag " +
                "VALUES(null, 'namibia', 'africa', 'namibia.png','1')";
        db.execSQL(sql);

        sql = "INSERT INTO flag " +
                "VALUES(null, 'niger', 'africa', 'niger.png','1')";
        db.execSQL(sql);

        sql = "INSERT INTO flag " +
                "VALUES(null, 'nigeria', 'africa', 'nigeria.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'rwanda', 'africa', 'rwanda.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'sao_tome_and_principe', 'africa', 'sao_tome_and_principe.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'senegal', 'africa', 'senegal.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'sierra_leone', 'africa', 'sierra_leone.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'somalia', 'africa', 'somalia.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'south_africa', 'africa', 'south_africa.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'south_sudan', 'africa', 'south_sudan.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'sudan', 'africa', 'sudan.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'swaziland', 'africa', 'swaziland.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'tanzania', 'africa', 'tanzania.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'the_central_african_republic', 'africa', 'the_central_african_republic.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'the_comoros', 'africa', 'the_comoros.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'the_democratic_republic_of_congo', 'africa', 'the_democratic_republic_of_congo.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'the_gambia', 'africa', 'the_gambia.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'the_republic_of_the_congo', 'africa', 'the_republic_of_the_congo.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'the_seychelles', 'africa', 'the_seychelles.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'togo', 'africa', 'togo.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'tunisia', 'africa', 'tunisia.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'uganda', 'africa', 'uganda.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'western_sahara', 'africa', 'western_sahara.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'zambia', 'africa', 'zambia.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'zimbabwe', 'africa', 'zimbabwe.png','1')";
        db.execSQL(sql);

        //north-america
        sql = "INSERT INTO flag " +
                "VALUES(null, 'antigun_and_barbuda', 'namerica', 'antigun_and_barbuda.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'barbados', 'namerica', 'barbados.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'belize', 'namerica', 'belize.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'canada', 'namerica', 'canada.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'costa', 'namerica', 'costa.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'cuba', 'namerica', 'cuba.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'dominica', 'namerica', 'dominica.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'el_savador', 'namerica', 'el_savador.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'grenada', 'namerica', 'grenada.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'guatemala', 'namerica', 'guatemala.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'haiti', 'namerica', 'haiti.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'honduras', 'namerica', 'honduras.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'jamaica', 'namerica', 'jamaica.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'mexico', 'namerica', 'mexico.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'nicaragua', 'namerica', 'nicaragua.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'panama', 'namerica', 'panama.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'saint_kitts_and_nevis', 'namerica', 'saint_kitts_and_nevis.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'saint_lucia', 'namerica', 'saint_lucia.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'saint_vincent_and_the_grenadines', 'namerica', 'saint_vincent_and_the_grenadines.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'the_bahamas', 'namerica', 'the_bahamas.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'the_dominican_republic', 'namerica', 'the_dominican_republic.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'the_united_states', 'namerica', 'the_united_states.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'trinidad_and_tobago', 'namerica', 'trinidad_and_tobago.png','1')";
        db.execSQL(sql);


        //oceania(18)
        sql = "INSERT INTO flag " +
                "VALUES(null, 'australia', 'oceania', 'australia.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'cook_islands', 'oceania', 'cook_islands.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'east_timor', 'oceania', 'east_timor.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'fiji', 'oceania', 'fiji.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'indonesia', 'oceania', 'indonesia.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'kiribati', 'oceania', 'kiribati.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'micronesia', 'oceania', 'micronesia.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'nauru', 'oceania', 'nauru.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'new_zealand', 'oceania', 'new_zealand.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'niue', 'oceania', 'niue.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'palau', 'oceania', 'palau.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'papua_new_guinea', 'oceania', 'papua_new_guinea.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'samoa', 'oceania', 'samoa.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'the_marshall_islands', 'oceania', 'the_marshall_islands.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'the_solomon_islands', 'oceania', 'the_solomon_islands.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'tonga', 'oceania', 'tonga.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'tuvalu', 'oceania', 'tuvalu.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'vanuatu', 'oceania', 'vanuatu.png','1')";
        db.execSQL(sql);


    }

    public ArrayList<GetRecord> selectGetRecord(){
        sql = "SELECT * FROM flag";
        cursor = mdb.rawQuery(sql,null);

        ArrayList<GetRecord> arrayList = new ArrayList<>();
        GetRecord getRecord;
        String country, continent, image, level;
        int id;

        while(cursor.moveToNext()){
            id = cursor.getInt(cursor.getColumnIndex("id"));
            country = cursor.getString(cursor.getColumnIndex("country"));
            continent = cursor.getString(cursor.getColumnIndex("continent"));
            image = cursor.getString(cursor.getColumnIndex("image"));
            level = cursor.getString(cursor.getColumnIndex("level"));

            getRecord = new GetRecord(id, country, continent, image, level);
            arrayList.add(getRecord);
        }

        return arrayList;
    }
 }
