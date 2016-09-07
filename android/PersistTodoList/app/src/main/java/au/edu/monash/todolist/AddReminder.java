package au.edu.monash.todolist;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

import au.edu.monash.todolist.constant.Constants;
import au.edu.monash.todolist.data.Reminder;


public class AddReminder extends AppCompatActivity implements
        DatePickerFragment.DatePickerListener {


    private TextView toolbarTitle;
    private TextView title;
    private TextView description;
    private TextView dueDate;
    private CheckBox isComplete;
    private Button add;
    private Button date;

    private Button btnGotoAdd;

    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder);
        initiateToolbar();
        initiateFormFields();

    }

    @Override
    public void onDateSet(int year, int month, int day)
    {
        // Set selected year, month and day in calendar object
        calendar.set(year, month, day);
        dueDate.setText(day + "/" + month + "/" + year);
    }
    private void initiateToolbar() {
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbarTitle = (TextView) findViewById(R.id.toolbarTitle);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btnGotoAdd = (Button) findViewById(R.id.buttonGotoAdd);
        btnGotoAdd.setVisibility(View.GONE);
    }

    private void initiateFormFields() {
        calendar = Calendar.getInstance();
        title = (TextView) findViewById(R.id.editTextTitle);
        description = (TextView) findViewById(R.id.editTextDescription);
        dueDate = (TextView) findViewById(R.id.textViewDueDate);
        date = (Button) findViewById(R.id.btnSelectDate);

        date.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getFragmentManager(), "datePicker");
                }
            });
        isComplete = (CheckBox) findViewById(R.id.checkboxIsCompleted);
        add = (Button) findViewById(R.id.btnAddReminder);

        add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String rTitle = title.getText().toString();
                String rDescription = description.getText().toString();
                Date rDate = calendar.getTime();
                int ctime = (int) (rDate.getTime()/1000);
                int rIsComplete = isComplete.isChecked()? 1 : 0;
                // do validation here
                Reminder r = new Reminder(1, rTitle ,rDescription, ctime, rIsComplete);
                Constants.dbContext.addReminder(r);
                Constants.reminderList = Constants.dbContext.getAllReminders();
                finish();
            }
        });
    }


}
