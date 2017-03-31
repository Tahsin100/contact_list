package bd.com.tahsin.contactlist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tahsin on 3/30/2017.
 */
public class DBHelper extends SQLiteOpenHelper {
    // Database Version
    public static final int DATABASE_VERSION = 10;
    // Database Name
    public static final String DATABASE_NAME = "cont_list.db";


    public static final String TABLE_CONTACT_LIST = "contact_list";

    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String NUMBER = "number";


    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        //Create ContactList Table
        String queryCAT = "CREATE TABLE " + TABLE_CONTACT_LIST + "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME + " TEXT, "
                + NUMBER + " TEXT" + ");";
        db.execSQL(queryCAT);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
     // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACT_LIST);
     // Creating tables again
        onCreate(db);
    }

    //Add a new Contact List
    public void addContactList(ContactList contactList) {
        ContentValues values = new ContentValues();
        values.put(NAME, contactList.getName());
        values.put(NUMBER, contactList.getNumber());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_CONTACT_LIST, null, values);
        db.close();
    }

    public List<String> FetchContactList(int index) {
        String dbString = "";
        final List<String> list=new ArrayList<String>();
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_CONTACT_LIST + " WHERE 1";
        int i = 0;
        Cursor c = db.rawQuery(query, null);

        c.moveToPosition(index);
        Log.d("Index->", String.valueOf(index));

        while (i < 10) {
            if(c.isAfterLast()){
                list.add("end");
                break;
            }
            if(c.getString(c.getColumnIndex(NAME))!=null) {
                dbString = c.getString(c.getColumnIndex(NAME)) + ":" + c.getString(c.getColumnIndex(NUMBER));
                //Log.d("Contacts",dbString);
                list.add(dbString);
                c.moveToNext();
                i++;
            }
        }
        db.close();
        return list;
    }

    public int isDBexists (){
        int isDBexists = 0;
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_CONTACT_LIST + " WHERE 1";
        Cursor c = db.rawQuery(query, null);
        if (c != null) {
            c.moveToFirst();
        }
        //c.getColumnIndex("item_name");
        if(c.getCount()>0){
            isDBexists = c.getCount();
        }
        return isDBexists;
    }



}
