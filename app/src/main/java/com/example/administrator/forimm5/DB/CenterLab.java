package com.example.administrator.forimm5.DB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.administrator.forimm5.Util.Const.REGION;
import static com.example.administrator.forimm5.Util.Const.TABLE_NAME;

public class CenterLab {

    List<Center> data;
    DatabaseHelper helper;
    SQLiteDatabase db;
    private static CenterLab sCenter;

    // 생성자
    public CenterLab(Context context) {
        data = new ArrayList<>();
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
    public static CenterLab getInstance(Context context){
        if(sCenter == null){
            sCenter = new CenterLab(context);
        }
        return sCenter;
    }

    // 전체 데이터 출력
    public List<Center> getDatas(){
        List<Center> data = new ArrayList<>();

        CenterCursorWrapper cursor = query(null,null);

        while(cursor.moveToNext()){
            data.add(cursor.getCenterFromCursor());
        }

        cursor.close();
        return data;
    }

    // 지역 출력 -- 그 지역에 해당하는 것만 쿼리해서 가져온다.
    public List<Center> getRegions(String region, int resId, int selectedResId){
        List<Center> data = new ArrayList();

        String whereClause = REGION + " = ?";
        String[] whereArgs = { region };

        CenterCursorWrapper cursor = query(whereClause, whereArgs);

        while(cursor.moveToNext()){
            Center center = cursor.getCenterFromCursor();
            center.setResId(resId);
            center.setSelectedResId(selectedResId);
            data.add(center);
        }
        cursor.close();
        return data;
    }

    public List<Center> getRegions(String region){
        List<Center> data = new ArrayList();

        String whereClause = REGION + " = ?";
        String[] whereArgs = { region };

        CenterCursorWrapper cursor = query(whereClause, whereArgs);

        while(cursor.moveToNext()){
            Center center = cursor.getCenterFromCursor();
            data.add(center);
        }
        cursor.close();
        return data;
    }

    // 쿼리 메소드
    public CenterCursorWrapper query(String whereClause, String[] whereArgs){
        Cursor cursor = db.query(TABLE_NAME, null, whereClause, whereArgs, null, null, null);
        return new CenterCursorWrapper(cursor);
    }
}
