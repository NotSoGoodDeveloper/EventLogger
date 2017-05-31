package zero.zd.daily_event_logger;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ArrayAdapter<Event> mEventArrayAdapter;
    private List<Event> mEventList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initObjects();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_about:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initObjects() {
        mEventList = new ArrayList<>();

        ListView listView = (ListView) findViewById(R.id.list_event);
        mEventArrayAdapter = new EventArrayAdapter(this, R.layout.item_event, mEventList);
        listView.setAdapter(mEventArrayAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCreateEventDialog();
            }
        });
    }

    private void showCreateEventDialog() {
        ViewGroup dialogRootView = (ViewGroup) findViewById(R.id.root_dialog_create_event);
        final View dialogView = getLayoutInflater()
                .inflate(R.layout.dialog_create_event, dialogRootView);
        final EditText eventEditText = (EditText) dialogView.findViewById(R.id.edit_event);

        AlertDialog createEventDialog = new AlertDialog.Builder(this)
                .setTitle(R.string.title_event)
                .setView(dialogView)
                .setCancelable(false)
                .setNegativeButton(R.string.action_discard, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton(R.string.action_save, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String eventText = eventEditText.getText().toString();
                        eventText = formatText(eventText);
                        addEvent(new Event(eventText, new Date()));
                        dialog.dismiss();
                    }
                })
                .create();
        createEventDialog.show();
    }

    private void addEvent(Event event) {
        mEventList.add(event);
        mEventArrayAdapter.notifyDataSetChanged();
    }

    private String formatText(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    private static class ViewHolder {
        private TextView eventTextView;
        private TextView dateTextView;
    }

    private class EventArrayAdapter extends ArrayAdapter<Event> {

        private Context mContext;
        private int mResource;
        private List<Event> mEventList;

        private EventArrayAdapter(Context context, int resource, List<Event> eventList) {
            super(context, resource, eventList);
            mContext = context;
            mResource = resource;
            mEventList = eventList;
        }

        @Override
        public int getCount() {
            return mEventList.size();
        }

        @Nullable
        @Override
        public Event getItem(int position) {
            return mEventList.get(position);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            ViewHolder viewHolder;

            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater)
                        mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(mResource, parent, false);

                viewHolder = new ViewHolder();
                viewHolder.eventTextView = (TextView) convertView.findViewById(R.id.text_event);
                viewHolder.dateTextView = (TextView) convertView.findViewById(R.id.text_date);

                convertView.setTag(viewHolder);
            } else viewHolder = (ViewHolder) convertView.getTag();

            Event event = mEventList.get(position);

            viewHolder.eventTextView.setText(event.getEvent());
            viewHolder.dateTextView.setText(event.getDate().toString());

            return convertView;
        }
    }
}
