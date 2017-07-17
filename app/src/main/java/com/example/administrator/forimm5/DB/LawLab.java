package com.example.administrator.forimm5.DB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.administrator.forimm5.Util.Const.CHAPTER;
import static com.example.administrator.forimm5.Util.Const.LAW_TABLE_NAME;

/**
 * Created by Administrator on 2017-07-13.
 */

public class LawLab {

    DatabaseHelper helper;
    SQLiteDatabase db;
    private static LawLab sLawLab;

    // 생성자
    public LawLab(Context context) {

        helper = new DatabaseHelper(context);

        // 사용하는 곳에서 에셋의 데이터를 경로에 복사하고 데이터베이스를 열어줘야 한다.
        try {
            helper.createDatabase();
        } catch (IOException e) {
            e.printStackTrace();
        }

        helper.openDatabase();
        db = helper.getDb();
    }

    // 싱글턴
    public static LawLab getInstance(Context context){
        if(sLawLab == null){
            sLawLab = new LawLab(context);
        }
        return sLawLab;
    }

//    // 전체 데이터 출력
//    public List<Law> getDatas(){
//        List<Law> data = new ArrayList<>();
//
//        LawCursorWrapper cursor = query(null,null);
//
//        while(cursor.moveToNext()){
//            data.add(cursor.getLawFromCursor());
//        }
//
//        cursor.close();
//        return data;
//    }

    public List<LawChild> getLaws(String chapter){
        List<LawChild> data = new ArrayList<>();

        String whereClause = CHAPTER + " = ?";
        String[] whereArgs = { chapter };

        LawCursorWrapper cursor = query(whereClause, whereArgs);

        while(cursor.moveToNext()){
            LawChild law = cursor.getLawFromCursor();
            data.add(law);
        }
        cursor.close();
        return data;

    }


    // 쿼리 메소드
    public LawCursorWrapper query(String whereClause, String[] whereArgs){
        Cursor cursor = db.query(LAW_TABLE_NAME, null, whereClause, whereArgs, null, null, null);
        return new LawCursorWrapper(cursor);
    }

}
