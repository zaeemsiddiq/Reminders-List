package au.edu.monash.todolist.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

import au.edu.monash.todolist.constant.Constants;

/**
 * Created by Zaeem on 4/13/2016.
 */
public class Reminder {
    public static final String TABLE_NAME = "Reminder";
    public static final String COLUMN_ID = "reminderID";
    public static final String COLUMN_TITLE = "reminderTitle";
    public static final String COLUMN_DESCRIPTION = "reminderDescription";
    public static final String COLUMN_DUEDATE = "reminderDueDate";
    public static final String COLUMN_ISCOMPLETE = "reminderIsComplete";


    // Table create statement
    public static final String CREATE_STATEMENT =
            "CREATE TABLE " + TABLE_NAME + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    COLUMN_TITLE + " TEXT NOT NULL, " +
                    COLUMN_DESCRIPTION + " TEXT NOT NULL, " +
                    COLUMN_DUEDATE + " INTEGER NOT NULL, " +
                    COLUMN_ISCOMPLETE + " INTEGER NOT NULL " +
                    ")";


    int ReminderID;
    String Title;
    String Description;
    int Date;
    int Complete;

    public Reminder(int reminderID, String title, String description, int date, int isComplete) {
        setReminderID(reminderID);
        setTitle(title);
        setDescription(description);
        setDate(date);
        setComplete(isComplete);
    }
    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getDate() {
        return Date;
    }

    public void setDate(int date) {
        Date = date;
    }

    public int getIsComplete() {
        return Complete;
    }

    public void setComplete(int complete) {
        Complete = complete;
    }

    public static void updateReminder(int index) {
        //Reminder r = Constants.reminderList.get(index);
        //r.setComplete(complete);
        //Constants.reminderList.set(index,r);
    }

    public void setReminderID(int reminderID) {
        this.ReminderID = reminderID;
    }

    public int getReminderID() {
        return this.ReminderID;
    }
}
