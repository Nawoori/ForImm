package com.example.administrator.forimm5.DB;

import android.database.Cursor;
import android.database.CursorWrapper;

import static com.example.administrator.forimm5.Util.Const.CONTENT;
import static com.example.administrator.forimm5.Util.Const.TITLE;

/**
 * Created by Administrator on 2017-07-13.
 */

public class LawCursorWrapper extends CursorWrapper {

    public LawCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public LawChild getLawFromCursor(){
        String title = getString(getColumnIndex(TITLE));
        String content = getString(getColumnIndex(CONTENT));

        LawChild law = new LawChild(title, content);
        return law;
    }

}
