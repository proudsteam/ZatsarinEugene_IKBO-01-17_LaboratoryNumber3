package android.example.laboratorynumber3;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Size;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.File;

public class TableActivity extends AppCompatActivity {

    TableLayout table;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);
        //TableLayout.LayoutParams lP = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,TableLayout.LayoutParams.MATCH_PARENT, Gravity.CENTER);
       // lP.weight = 1;
        TableRow.LayoutParams trParam = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.MATCH_PARENT);
        trParam.weight = 1;
      //  trParam.weight = (float) 1.0;
        table = findViewById(R.id.tableID);


        Cursor curs = MainActivity.db.query("Student",new String[] {"ID, FIO, DATE"},null,null,null,null,null,null);
     //  Cursor forFIO = MainActivity.db.query("Student",new String[] {"FIO"},null,null,null,null,null,null);
     //  Cursor forID = MainActivity.db.query("Student",new String[] {"ID"},null,null,null,null,null,null);
     //  Cursor forDATE = MainActivity.db.query("Student",new String[] {"DATE"},null,null,null,null,null,null);
        if (curs.getCount() !=0) {
            curs.moveToFirst();

            do {
                int FIO = curs.getColumnIndex("FIO");
                int ID = curs.getColumnIndex("id");
                int DATE = curs.getColumnIndex("Date");
                Log.d("Zheka", "INDEXES    :" + " FIO =" + FIO + " ID =" + ID + " DATE =" + DATE);
                TableRow tr = new TableRow(this);
                tr.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                        TableLayout.LayoutParams.WRAP_CONTENT));
                TextView viewFamily = new TextView(this);
                TextView viewName = new TextView(this);
                TextView viewSecondName = new TextView(this);
                TextView view1[] = {viewFamily, viewName, viewSecondName};
                for (int j = 0; j < view1.length; j++) {
                    view1[j].setLayoutParams(trParam);
                    view1[j].setGravity(Gravity.CENTER);
                }
                view1[0].setText(curs.getString(ID));
                view1[1].setText(curs.getString(FIO));
                view1[2].setText(curs.getString(DATE));
                Log.d("Zheka", "ITOG : " + curs.getString(ID) + "   " + curs.getString(FIO) + "   " + curs.getString(DATE));
                for (int c = 0; c < view1.length; c++) {
                    tr.addView(view1[c]);
                }
                table.addView(tr);
            }
            while (curs.moveToNext());
        }

        curs.close();
        //File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/data/" + this.getPackageName(),"Student");
        //file = MainActivity.db.
   //     forFIO.close();
   //     forID.close();
   //     forDATE.close();


    }
}
