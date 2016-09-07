package au.edu.monash.todolist;

import android.app.DialogFragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import au.edu.monash.todolist.constant.Constants;
import au.edu.monash.todolist.data.Reminder;

public class ViewReminder extends AppCompatActivity implements
        DatePickerFragment.DatePickerListener {

    private TextView toolbarTitle;
    private EditText title;
    private EditText description;
    private TextView dueDate;
    private Button btnDate;
    private CheckBox isComplete;
    private Button save;

    private Calendar calendar;

    private int index=0;
    private Reminder reminder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reminder);
        initiateToolbar();
        Bundle extras = getIntent().getExtras();
        index = extras.getInt("position");
        reminder = Constants.reminderList.get(index);
        initiateFormFields();

    }
    private void initiateToolbar() {
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbarTitle = (TextView) findViewById(R.id.toolbarTitle);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    private void initiateFormFields() {
        calendar = Calendar.getInstance();
        title = (EditText) findViewById(R.id.viewReminderTitle);
        description = (EditText) findViewById(R.id.viewReminderDescription);
        dueDate = (TextView) findViewById(R.id.viewReminderDueDate);
        btnDate = (Button) findViewById(R.id.btnSelectDate);
        btnDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getFragmentManager(), "datePicker");
                dueDate.setText((new SimpleDateFormat("MM-dd-yyyy").format(new Date((long)(reminder.getDate()*1000)))));
            }
        });

        isComplete = (CheckBox) findViewById(R.id.viewReminderIsCompleted);
        save = (Button) findViewById(R.id.btnSaveReminder);

        title.setText(reminder.getTitle());
        description.setText(reminder.getDescription());
        isComplete.setChecked(reminder.getIsComplete() == 1 ? true: false);

        save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String rTitle = title.getText().toString();
                String rDescription = description.getText().toString();
                Date rDate = calendar.getTime();
                int ctime = (int) (rDate.getTime()/1000);
                int rIsComplete = isComplete.isChecked()? 1 : 0;
                reminder.setTitle(rTitle);
                reminder.setDescription(rDescription);
                reminder.setDate(ctime);
                reminder.setComplete(rIsComplete);
                Constants.dbContext.updateReminder(reminder);
                finish();
            }
        });
    }

    @Override
    public void onDateSet(int year, int month, int day) {
        // Set selected year, month and day in calendar object
        calendar.set(year, month, day);
        dueDate.setText(day + "/" + month + "/" + year);
    }
}
