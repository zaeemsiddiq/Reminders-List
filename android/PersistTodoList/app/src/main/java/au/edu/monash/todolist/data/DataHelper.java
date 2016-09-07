package au.edu.monash.todolist.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by Zaeem on 4/22/2016.
 */
public class DataHelper extends SQLiteOpenHelper {
    // Set database properties
    public static final String DATABASE_NAME = "ReminderDB";
    public static final int DATABASE_VERSION = 1;

    // Constructor
    public DataHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(Reminder.CREATE_STATEMENT);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Reminder.TABLE_NAME);
    }

    public void updateReminder(Reminder m) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Reminder.COLUMN_TITLE, m.getTitle());
        values.put(Reminder.COLUMN_DESCRIPTION, m.getDescription());
        values.put(Reminder.COLUMN_DUEDATE, m.getDate());
        values.put(Reminder.COLUMN_ISCOMPLETE, m.getIsComplete());
        db.update(Reminder.TABLE_NAME, values, Reminder.COLUMN_ID + "=" +m.getReminderID(), null);
    }

    public void addReminder(Reminder m) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Reminder.COLUMN_TITLE, m.getTitle());
        values.put(Reminder.COLUMN_DESCRIPTION, m.getDescription());
        values.put(Reminder.COLUMN_DUEDATE, m.getDate());
        values.put(Reminder.COLUMN_ISCOMPLETE, m.getIsComplete());
        db.insert(Reminder.TABLE_NAME, null, values);
        db.close();
    }

    public ArrayList<Reminder> getAllReminders() {
        ArrayList<Reminder> reminders = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Reminder.TABLE_NAME, null);
        // Add Reminder to hash map for each row result
        if (cursor.moveToFirst()) {
            do {
                Reminder m = new Reminder(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getInt(4));
                reminders.add(m);
            } while (cursor.moveToNext());
        }
        // Close cursor
        cursor.close();
        return reminders;
    }
}