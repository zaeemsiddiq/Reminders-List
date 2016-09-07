package au.edu.monash.todolist;

import android.content.Intent;
import android.os.Parcelable;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import au.edu.monash.todolist.constant.Constants;
import au.edu.monash.todolist.data.DataHelper;
import au.edu.monash.todolist.data.Reminder;
import au.edu.monash.todolist.listAdapter.ReminderListAdapter;

public class MainActivity extends AppCompatActivity {

    private TextView toolbarTitle;
    private TextView emptyMessage;
    private ListView listviewDisplay;
    private Button btnAdd;
    ReminderListAdapter listAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Constants.dbContext = new DataHelper(getApplicationContext());
        Constants.reminderList = Constants.dbContext.getAllReminders();
        listAdapter = new ReminderListAdapter(this);
        initiateToolbar();
        emptyMessage = (TextView) findViewById(R.id.txtEmpty);
        listviewDisplay = (ListView) findViewById(R.id.listReminder);
        listviewDisplay.setAdapter(listAdapter);
        listviewDisplay.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Reminder r = listAdapter.getItem(position);
                Intent viewReminder = new Intent(getApplicationContext(), ViewReminder.class);
                viewReminder.putExtra("index", position);
                startActivityForResult(viewReminder, Constants.VIEW_REMINDER);
            }
        });
        if (Constants.reminderList.isEmpty()) {
            emptyMessage.setVisibility(View.VISIBLE);
            listviewDisplay.setVisibility(View.GONE);
        } else {
            emptyMessage.setVisibility(View.GONE);
            listviewDisplay.setVisibility(View.VISIBLE);
        }
    }

    private void initiateToolbar() {
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbarTitle = (TextView) findViewById(R.id.toolbarTitle);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btnAdd = (Button) findViewById(R.id.buttonGotoAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addIntent = new Intent(getApplicationContext(), AddReminder.class);
                startActivityForResult(addIntent, Constants.ADD_REMINDER);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == Constants.ADD_REMINDER) {
            if (Constants.reminderList.isEmpty()) {
                emptyMessage.setVisibility(View.VISIBLE);
                listviewDisplay.setVisibility(View.GONE);
            } else {
                emptyMessage.setVisibility(View.GONE);
                listviewDisplay.setVisibility(View.VISIBLE);
            }
            listAdapter.refreshList();
            listAdapter.notifyDataSetChanged();
        }
        else if (requestCode == Constants.VIEW_REMINDER) {
            listAdapter.refreshList();
            listAdapter.notifyDataSetChanged();
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
