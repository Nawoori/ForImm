package com.example.administrator.forimm5.DB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.administrator.forimm5.BuildConfig;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class DatabaseHelper extends SQLiteOpenHelper {

    String DB_PATH = null;
    static String DB_NAME = "forimm.sqlite";
    SQLiteDatabase db;
    Context context;

    public DatabaseHelper(Context context){
        super(context, DB_NAME, null, 1);
        this.context = context;
        DB_PATH = "/data/data/"+context.getPackageName()+"/"+"databases/";
    }


    // 데이터베이스가 열려있는지 확인하고 열려 있으면 닫아준다..
    public boolean checkDatabase(){

        SQLiteDatabase checkDB = null;
        try{
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e){

        }

        if(checkDB != null){
            checkDB.close();
        }

        return checkDB != null ? true : false;
    }

    // 데이터 베이스가 없으면 열어준다.
    public void createDatabase() throws IOException {

        boolean dbexists = checkDatabase();

        if(dbexists) {

            if(BuildConfig.DEBUG){
                try{
                    // 디버그 모드에서는 항상 데이터베이스를 복사해서 새로 생성한다
                    copyDatabase();
                }catch (IOException e){

                }
            }

        } else {
            this.getReadableDatabase();
            try{
                // 여기가 핵심
                copyDatabase();
            }catch (IOException e){

            }
        }
    }

    // 에셋에 넣어준 sqlite 파일을 위에서 만들어 준 데이터베이스에 복사해준다. 이 메소드를 호출하지 않으면 복사해 놓은 파일을 사용할 수 없다.
    public void copyDatabase() throws IOException {
        InputStream myInput = context.getAssets().open(DB_NAME);
        String outFileName = DB_PATH+ DB_NAME;
        OutputStream myOutput = new FileOutputStream(outFileName);
        byte[] buffer = new byte[1024];
        int length;
        while((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    // 위에서 복사해 준 후 데이터베이스를 열어준다. 이 데이터베이스로 이제 쿼리하면 된다.
    public void openDatabase() throws SQLiteException {
        String myPath = DB_PATH + DB_NAME;
        db = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    }

    // 열어준 데이터베이스를 사용할 곳(Lab)에 리턴해준다.
    public SQLiteDatabase getDb(){
        return  db;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    // 데이터가 바뀔 때마다 자동으로 파일을 데이터베이스에 복사해 준다.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(newVersion > oldVersion){
            try{
                copyDatabase();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }


    @Override
    public synchronized void close(){
        if(db != null){
            db.close();
        }
        super.close();
    }
}
