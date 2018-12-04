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
                "country_kor text NOT NULL UNIQUE, " +
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

    public ArrayList<GetRecord> selectGetRecord(){
        sql = "SELECT * FROM flag";
        cursor = mdb.rawQuery(sql,null);

        ArrayList<GetRecord> arrayList = new ArrayList<>();
        GetRecord getRecord;
        String country, country_kor, continent, image, level;
        int id;

        while(cursor.moveToNext()){
            id = cursor.getInt(cursor.getColumnIndex("id"));
            country = cursor.getString(cursor.getColumnIndex("country"));
            country_kor = cursor.getString(cursor.getColumnIndex("country_kor"));
            continent = cursor.getString(cursor.getColumnIndex("continent"));
            image = cursor.getString(cursor.getColumnIndex("image"));
            level = cursor.getString(cursor.getColumnIndex("level"));

            getRecord = new GetRecord(id, country, country_kor,continent, image, level);
            arrayList.add(getRecord);
        }

        return arrayList;
    }

    private void table_inputdata(SQLiteDatabase db){
        //south-america
        sql = "INSERT INTO flag " +
                "VALUES(null, 'argentina','아르헨티나', 'samerica', 'argentina.png','2')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'bolivia','볼리비아', 'samerica', 'bolivia.png','2')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'brazil','브라질', 'samerica', 'brazil.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'chile', '칠레', 'samerica', 'chile.png','2')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'colombia','콜롬비아', 'samerica', 'colombia.png','2')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'ecuador', '에콰도르', 'samerica', 'ecuador.png','2')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'guyana', '가이아나', 'samerica', 'guyana.png','3')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'paraguay', '파라과이', 'samerica', 'paraguay.png','2')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'peru', '페루', 'samerica', 'peru.png','2')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'suriname', '수리남','samerica', 'suriname.png','2')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'trinidad_and_tobago', '트리니다드 토바고', 'samerica', 'trinidad_and_tobago.png','3')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'uruguay', '우루과이','samerica', 'uruguay.png','2')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'venezuela', '베네수엘라', 'samerica', 'venezuela.png','2')";
        db.execSQL(sql);


        //europe
        sql = "INSERT INTO flag " +
                "VALUES(null, 'albania','알바니아', 'europe', 'albania.png','2')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'andorra','안도라', 'europe', 'andorra.png','3')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'austria','오스트리아', 'europe', 'austria.png','2')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'belarus','벨라루스' ,'europe', 'belarus.png','2')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'belgium','벨기에', 'europe', 'belgium.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'bosnia_and_herzegovina','보스니아 헤르체고비나' ,'europe', 'bosnia_and_herzegovina.png','2')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'bulgaria','불가리아', 'europe', 'bulgaria.png','2')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'croatia','크로아티아', 'europe', 'croatia.png','2')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'denmark','덴마크', 'europe', 'denmark.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'estonia','에스토니아', 'europe', 'estonia.png','2')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'finland','핀란드', 'europe', 'finland.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'france','프랑스', 'europe', 'france.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'germany','독일', 'europe', 'germany.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'greece','그리스', 'europe', 'greece.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'hungary','헝가리', 'europe', 'hungary.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'iceland','아이슬란드' ,'europe', 'iceland.png','2')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'ireland','아일랜드', 'europe', 'ireland.png','2')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'italy','이탈리아', 'europe', 'italy.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'kosovo','코소보', 'europe', 'kosovo.png','3')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'latvia','라트비아', 'europe', 'latvia.png','2')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'liechtenstein','리히텐슈타인', 'europe', 'liechtenstein.png','2')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'lithuania','리투아니아','europe', 'lithuania.png','2')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'luxembourg','룩셈부르크', 'europe', 'luxembourg.png','2')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'macedonia','마케도니아', 'europe', 'macedonia.png','3')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'malta','몰타', 'europe', 'malta.png','2')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'moldova','몰도바', 'europe', 'moldova.png','3')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'monaco','모나코', 'europe', 'monaco.png','2')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'montenegro','몬테네그로', 'europe', 'montenegro.png','3')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'norway','노르웨이', 'europe', 'norway.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'poland','폴란드', 'europe', 'poland.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'portugal','포르투갈', 'europe', 'portugal.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'romania','루마니아', 'europe', 'romania.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'russia','러시아', 'europe', 'russia.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'san_marino','산마리노', 'europe', 'san_marino.png','3')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'serbia','세르비아', 'europe', 'serbia.png','2')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'slovakia','슬로바키아','europe', 'slovakia.png','2')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'slovenia','슬로베니아', 'europe', 'slovenia.png','2')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'spain','스페인', 'europe', 'spain.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'sweden','스웨덴', 'europe', 'sweden.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'switzerland','스위스', 'europe', 'switzerland.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'the_czech_republic','체코', 'europe', 'the_czech_republic.png','2')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'the_netherlands','네덜란드', 'europe', 'the_netherlands.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'the_united_kingdom','영국', 'europe', 'the_united_kingdom.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'the_vatican_city','바티칸 시국', 'europe', 'the_vatican_city.png','2')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'turkey','터키','europe', 'turkey.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'ukraine', '우크라이나','europe', 'ukraine.png','2')";
        db.execSQL(sql);

        //africa
        sql = "INSERT INTO flag " +
                "VALUES(null, 'algeria','알제리', 'africa', 'algeria.png','2')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'angola','앙골라' ,'africa', 'angola.png','2')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'benin','베냉', 'africa', 'benin.png','3')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'botswana','보츠와나', 'africa', 'botswana.png','2')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'burkina_faso','부르키나파소', 'africa', 'burkina_faso.png','3')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'burundi','부루나이', 'africa', 'burundi.png','2')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'cameroon','카메룬', 'africa', 'cameroon.png','2')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'cape_verde', '카보베르데','africa', 'cape_verde.png','3')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'chad','차드', 'africa', 'chad.png','3')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'cote_d_ivoire','코트디부아르', 'africa', 'cote_d_ivoire.png','2')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'djibouti','지부티' ,'africa', 'djibouti.png','3')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'egypt','이집트', 'africa', 'egypt.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'equatorial_guinea','적도 기니', 'africa', 'equatorial_guinea.png','3')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'eritrea','에리트레아', 'africa', 'eritrea.png','3')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'ethiopia','에티오피아', 'africa', 'ethiopia.png','2')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'gabon','가봉' ,'africa', 'gabon.png','2')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'ghana','가나', 'africa', 'ghana.png','2')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'guinea','기니', 'africa', 'guinea.png','2')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'guinea_bissau','기니비사우','africa', 'guinea_bissau.png','3')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'kenya','케냐', 'africa', 'kenya.png','2')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'lesotho','레소토', 'africa', 'lesotho.png','3')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'liberia','라이베리아' ,'africa', 'liberia.png','3')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'libya','리비아', 'africa', 'libya.png','2')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'madagascar','마다가스카르','africa', 'madagascar.png','2')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'malawi','말라위' ,'africa', 'malawi.png','3')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'mali','말리', 'africa', 'mali.png','3')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'mauritania','모리타니', 'africa', 'mauritania.png','3')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'mauritius','모리셔스', 'africa', 'mauritius.png','2')";
        db.execSQL(sql);

        sql = "INSERT INTO flag " +
                "VALUES(null, 'morocco','모로코', 'africa', 'morocco.png','2')";
        db.execSQL(sql);

        sql = "INSERT INTO flag " +
                "VALUES(null, 'mozambique','모잠비크' ,'africa', 'mozambique.png','2')";
        db.execSQL(sql);

        sql = "INSERT INTO flag " +
                "VALUES(null, 'namibia','나미비아', 'africa', 'namibia.png','3')";
        db.execSQL(sql);

        sql = "INSERT INTO flag " +
                "VALUES(null, 'niger','니제르', 'africa', 'niger.png','3')";
        db.execSQL(sql);

        sql = "INSERT INTO flag " +
                "VALUES(null, 'nigeria','나이지리아','africa', 'nigeria.png','2')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'rwanda', '르완다', 'africa', 'rwanda.png','2')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'sao_tome_and_principe','상투메 프린시페', 'africa', 'sao_tome_and_principe.png','3')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'senegal','세네갈', 'africa', 'senegal.png','3')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'sierra_leone','시에라리온', 'africa', 'sierra_leone.png','2')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'somalia','소말리아', 'africa', 'somalia.png','2')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'south_africa','남아프리카 공화국', 'africa', 'south_africa.png','2')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'south_sudan','남수단','africa', 'south_sudan.png','2')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'sudan','수단', 'africa', 'sudan.png','2')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'swaziland','에스와티니', 'africa', 'swaziland.png','3')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'tanzania','탄자니아', 'africa', 'tanzania.png','2')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'the_central_african_republic','중앙아프리카 공화국','africa', 'the_central_african_republic.png','3')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'the_comoros','코모로', 'africa', 'the_comoros.png','3')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'the_democratic_republic_of_congo','콩고 민주 공화국', 'africa', 'the_democratic_republic_of_congo.png','2')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'the_gambia','감비아', 'africa', 'the_gambia.png','3')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'the_republic_of_the_congo','콩고 공화국', 'africa', 'the_republic_of_the_congo.png','2')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'the_seychelles', '세이셸', 'africa', 'the_seychelles.png','2')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'togo','토고', 'africa', 'togo.png','2')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'tunisia','튀니지' ,'africa', 'tunisia.png','2')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'uganda','우간다', 'africa', 'uganda.png','2')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'western_sahara','서사하라', 'africa', 'western_sahara.png','3')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'zambia','잠비아' ,'africa', 'zambia.png','2')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'zimbabwe','짐바브웨', 'africa', 'zimbabwe.png','2')";
        db.execSQL(sql);

        //north-america
        sql = "INSERT INTO flag " +
                "VALUES(null, 'antigun_and_barbuda', '앤티가 바부다', 'namerica', 'antigun_and_barbuda.png','3')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'barbados', '바베이도스', 'namerica', 'barbados.png','3')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'belize', '벨리즈', 'namerica', 'belize.png','3')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'canada', '캐나다', 'namerica', 'canada.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'costa', '코스타리카', 'namerica', 'costa.png','2')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'cuba', '쿠바', 'namerica', 'cuba.png','2')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'dominica', '도미니카 연방', 'namerica', 'dominica.png','3')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'el_savador', '엘살바도르', 'namerica', 'el_savador.png','3')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'grenada', '그레나다', 'namerica', 'grenada.png','3')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'guatemala', '과테말라', 'namerica', 'guatemala.png','2')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'haiti', '아이티', 'namerica', 'haiti.png','2')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'honduras', '온두라스', 'namerica', 'honduras.png','3')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'jamaica', '자메이카', 'namerica', 'jamaica.png','2')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'mexico', '멕시코', 'namerica', 'mexico.png','2')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'nicaragua', '니카라과', 'namerica', 'nicaragua.png','3')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'panama', '파나마', 'namerica', 'panama.png','3')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'saint_kitts_and_nevis', '세인트키츠 네비스', 'namerica', 'saint_kitts_and_nevis.png','3')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'saint_lucia', '세인트키츠 네비스', 'namerica', 'saint_lucia.png','3')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'saint_vincent_and_the_grenadines', '세인트빈센트 그레나딘', 'namerica', 'saint_vincent_and_the_grenadines.png','3')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'the_bahamas', '바하마', 'namerica', 'the_bahamas.png','3')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'the_dominican_republic', '도미니카 공화국', 'namerica', 'the_dominican_republic.png','2')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'the_united_states', '미국', 'namerica', 'the_united_states.png','1')";
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
        /*
        sql = "INSERT INTO flag " +
                "VALUES(null, 'indonesia', 'oceania', 'indonesia.png','1')";
        db.execSQL(sql);*/

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
                "VALUES(null, 'tonga','oceania', 'tonga.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'tuvalu', 'oceania', 'tuvalu.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'vanuatu', 'oceania', 'vanuatu.png','1')";
        db.execSQL(sql);


        //asia
        sql = "INSERT INTO flag " +
                "VALUES(null, 'afghanistan', '아프가니스탄', 'asia', 'afghanistan.png','2')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'armenia', '아르메니아', 'asia', 'armenia.png','3')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'azerbaijan', '아제르바이잔', 'asia', 'azerbaijan.png','3')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'bahrain', '바레인', 'asia', 'bahrain.png','3')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'bangladesh', '방글라데시', 'asia', 'bangladesh.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'bhutan', '부탄', 'asia', 'bhutan.png','2')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'brunei', '브루나이', 'asia', 'brunei.png','3')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'cambodia', '캄보디아', 'asia', 'cambodia.png','2')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'cyprus', '키프로스', 'asia', 'cyprus.png','3')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'georgia', '조지아', 'asia', 'georgia.png','3')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'india', '인도', 'asia', 'india.png','2')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'indonesia', '인도네시아', 'asia', 'indonesia.png','2')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'iran', '이란', 'asia', 'iran.png','2')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'iraq', '이라크', 'asia', 'iraq.png','2')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'israel', '이스라엘', 'asia', 'israel.png','2')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'japan', '일본', 'asia', 'japan.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'jordan', '요르단', 'asia', 'jordan.png','3')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'kazakhstan', '카자흐스탄', 'asia', 'kazakhstan.png','2')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'kuwait', '쿠웨이트', 'asia', 'kuwait.png','2')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'kyrgyzstan', '키르기스스탄', 'asia', 'kyrgyzstan.png','3')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'laos', '라오스', 'asia', 'laos.png','2')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'lebanon', '레바논', 'asia', 'lebanon.png','2')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'malaysia', '말레이시아', 'asia', 'malaysia.png','2')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'maldives', '몰디브', 'asia', 'maldives.png','2')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'mongolia', '몽골', 'asia', 'mongolia.png','2')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'myanmar', '미얀마', 'asia', 'myanmar.png','2')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'nepal', '네팔', 'asia', 'nepal.png','2')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'north_korea', '북한', 'asia', 'north_korea.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'oman', '오만', 'asia', 'oman.png','2')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'pakistan', '파키스탄', 'asia', 'pakistan.png','2')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'qatar', '카타르', 'asia', 'qatar.png','3')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'saudi_arabia', '사우디아라비아', 'asia', 'saudi_arabia.png','2')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'singapore', '싱가포르', 'asia', 'singapore.png','2')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'south_korea', '대한민국', 'asia', 'south_korea.png','1')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'sri_lanka', '스리랑카', 'asia', 'sri_lanka.png','2')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'syria', '시리아', 'asia', 'syria.png','2')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'tajikistan', '타지키스탄', 'asia', 'tajikistan.png','3')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'thailand', '태국', 'asia', 'thailand.png','2')";

        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'the_people_s_republic_of_china', '중화인민공화국', 'asia', 'the_people_s_republic_of_china.png','2')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'the_philippines', '필리핀', 'asia', 'the_philippines.png','2')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'the_republic_of_china', '대만', 'asia', 'the_republic_of_china.png','2')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'the_united_arab_emirates', '아랍에미리트', 'asia', 'the_united_arab_emirates.png','2')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'turkmenistan', '투르크메니스탄', 'asia', 'turkmenistan.png','3')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'uzbekistan', '우즈베키스탄', 'asia', 'uzbekistan.png','2')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'vietnam', '베트남', 'asia', 'vietnam.png','2')";
        db.execSQL(sql);
        sql = "INSERT INTO flag " +
                "VALUES(null, 'yemen', '예멘', 'asia', 'yemen.png','2')";
        db.execSQL(sql);
    }
 }
