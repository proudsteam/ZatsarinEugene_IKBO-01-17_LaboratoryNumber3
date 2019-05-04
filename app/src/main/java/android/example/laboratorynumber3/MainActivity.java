package android.example.laboratorynumber3;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    EditText name;
    EditText secondName;
    EditText family;
    Button print;
    Button addLine;
    Button Ivanov;
    static SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DBhelper dbhelp = new DBhelper(MainActivity.this);
        db = dbhelp.getWritableDatabase();
        db.delete("Student",null,null);
        String FioOfStudents[] = {"Golovin Artem Valerievich", "Ulbi Timur Valerievich", "Zatsarin Evgeniy Andreevich", "Ananiev Filipp Alexandrovich", "Arakelyan Andrey Andreevich", "Bugaev Ivan Mihailovich", "Esin Alexandr Sergeevich", "Zagorulko Kostyan Alexandrovich", "Klushin Mihail Alexeevich", "Krukov Mihail Sergeevich", "Kru4kov Matvey Romanovich", "Kuznetsov Nikolay Alexandrovich", "Krasov Alexaey Vitalievich", "Korneev Anton Dmitrievich", "Turina Valeriya Sergeevna", "Suhov Igor Andreevich"};


        FillingData(FioOfStudents);
        Log.d("Zheka", db.getPath());

        //Initialisation
        print = findViewById(R.id.printInfo);
        addLine = findViewById(R.id.addLine);
        Ivanov = findViewById(R.id.ChangeToIvanov);
        name = findViewById(R.id.name_field);
        secondName = findViewById(R.id.secondName);
        family = findViewById(R.id.fam_field);


        print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,TableActivity.class);
                startActivity(intent);


            }
        });

        addLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((!(name.getText().toString().equals(""))) && (!(secondName.getText().toString().equals(""))) && (!(family.getText().toString().equals(""))))
                {
                    String fio;
                    ContentValues cv = new ContentValues();
                    fio = family.getText().toString() + " " + name.getText().toString() + " " + secondName.getText().toString();
                    cv.put("FIO",fio);
                    Date currentTime = Calendar.getInstance().getTime();
                    String currentDate = DateFormat.getDateInstance().format(currentTime);
                    cv.put("Date", currentDate);
                    db.insert("Student",null,cv);
                }
            }
        });
        Ivanov.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cursor = db.query("Student",null,null,null,null,null,null);

                int count = cursor.getCount();
                ContentValues cv = new ContentValues();
                String countString = "" + count;
                cv.put("FIO","Ivanov Ivan Ivanovich");
                Date currentTime = Calendar.getInstance().getTime();
                String currentDate = DateFormat.getDateInstance().format(currentTime);
                cv.put("Date", currentDate);
                db.update("Student",cv,"id = ?", new String[] {countString});
                cursor.close();
            }
        });



    }

    void FillingData(String names[])
    {
        ContentValues cv = new ContentValues();
        for (int i=1; i <= 5; i ++)
        {
            Date currentTime = Calendar.getInstance().getTime();
            String currentDate = DateFormat.getDateInstance().format(currentTime);
            cv.put("Date", currentDate);
            cv.put("FIO",names[new Random().nextInt(names.length)]);
            db.insert("Student",null,cv);

        }
    }
}


class DBhelper extends SQLiteOpenHelper
{
    static final int DB_VERSION = 1;
    static final String DB_NAME = "Student";
    static final String FIO = "FIO";
    static final String DATE = "Date";
    public DBhelper(Context context)
    {
        super(context, DB_NAME ,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + DB_NAME + " (id integer primary key, " + FIO + " text, " + DATE + " text);"  );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}