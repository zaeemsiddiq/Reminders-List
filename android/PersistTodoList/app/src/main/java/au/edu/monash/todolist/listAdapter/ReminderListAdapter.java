package au.edu.monash.todolist.listAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import au.edu.monash.todolist.R;
import au.edu.monash.todolist.constant.Constants;
import au.edu.monash.todolist.data.Reminder;

/**
 * Created by Zaeem on 4/13/2016.
 */
public class ReminderListAdapter extends ArrayAdapter {
    private Context context;
    private List<Reminder> listItems;
    private class ViewHolder {
        TextView Title;
        TextView DueDate;
    }
    public ReminderListAdapter(Context context) {
        super(context,android.R.layout.simple_list_item_1, Constants.reminderList);
        this.context = context;
        this.listItems = Constants.reminderList;
    }

    public Reminder getItem(int position)
    {
        return listItems.get(position);
    }

    public int getCount() {
        return listItems==null ? 0: listItems.size();
    }

    public void refreshList() {
        this.listItems = Constants.dbContext.getAllReminders();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder viewHolder = new ViewHolder();
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.custom_list_row, null);
            TextView Title = (TextView) v.findViewById(R.id.textViewTitle);
            TextView Date = (TextView) v.findViewById(R.id.textViewDate);

            viewHolder.Title = Title;
            viewHolder.DueDate = Date;
            v.setTag(viewHolder);
        }
        else
            viewHolder = (ViewHolder) v.getTag();


        Reminder d = getItem(position);
        viewHolder.Title.setText(String.valueOf(d.getTitle()));
        viewHolder.DueDate.setText(String.valueOf(d.getDate()));
        return v;
    }

}
