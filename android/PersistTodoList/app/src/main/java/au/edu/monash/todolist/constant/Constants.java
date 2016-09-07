package au.edu.monash.todolist.constant;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import au.edu.monash.todolist.data.DataHelper;
import au.edu.monash.todolist.data.Reminder;

/**
 * Created by Zaeem on 4/13/2016.
 */
public class Constants {
    public static int ADD_REMINDER=1;
    public static int VIEW_REMINDER=2;
    public static List<Reminder> reminderList;
    public static DataHelper dbContext;
}
