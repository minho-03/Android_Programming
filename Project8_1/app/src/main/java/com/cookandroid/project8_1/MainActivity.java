package com.cookandroid.project8_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    DatePicker dp;
    EditText edtDiary;
    Button btnWrite;
    String diaryDate;
    myDBHelper myHelper;
    SQLiteDatabase sqlDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("간단 일기장 (SQLite)");

        dp = (DatePicker) findViewById(R.id.datePicker1);
        edtDiary = (EditText) findViewById(R.id.edtDiary);
        btnWrite = (Button) findViewById(R.id.btnWrite);

        myHelper = new myDBHelper(this);

        Calendar cal = Calendar.getInstance();
        int cYear = cal.get(Calendar.YEAR);
        int cMonth = cal.get(Calendar.MONTH);
        int cDay = cal.get(Calendar.DAY_OF_MONTH);

        diaryDate = String.format("%04d-%02d-%02d", cYear, cMonth + 1, cDay);
        String str = readDiary(diaryDate);
        edtDiary.setText(str);
        btnWrite.setEnabled(true);

        dp.init(cYear, cMonth, cDay, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                diaryDate = String.format("%04d-%02d-%02d", year, monthOfYear + 1, dayOfMonth);
                String str = readDiary(diaryDate);
                edtDiary.setText(str);
                btnWrite.setEnabled(true);
            }
        });

        btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqlDB = myHelper.getWritableDatabase();
                sqlDB.execSQL("INSERT OR REPLACE INTO myDiary VALUES ('" + diaryDate + "', '" + edtDiary.getText().toString() + "');");
                sqlDB.close();
                Toast.makeText(getApplicationContext(), "저장됨", Toast.LENGTH_SHORT).show();
            }
        });
    }

    String readDiary(String dDate) {
        String diaryStr = null;
        sqlDB = myHelper.getReadableDatabase();
        Cursor cursor = sqlDB.rawQuery("SELECT content FROM myDiary WHERE diaryDate = '" + dDate + "';", null);

        if (cursor.moveToFirst()) {
            diaryStr = cursor.getString(0);
            btnWrite.setText("수정하기");
        } else {
            edtDiary.setHint("일기 없음");
            btnWrite.setText("새로 저장");
        }

        cursor.close();
        sqlDB.close();
        return diaryStr;
    }

    public class myDBHelper extends SQLiteOpenHelper {
        public myDBHelper(Context context) {
            super(context, "myDB", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE myDiary (diaryDate CHAR(10) PRIMARY KEY, content VARCHAR(500));");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS myDiary;");
            onCreate(db);
        }
    }
}