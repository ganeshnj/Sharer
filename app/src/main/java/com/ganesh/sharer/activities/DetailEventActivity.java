package com.ganesh.sharer.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.ganesh.sharer.R;
import com.ganesh.sharer.Repository;
import com.ganesh.sharer.models.Event;
import com.ganesh.sharer.models.Share;
import com.ganesh.sharer.models.User;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class DetailEventActivity extends AppCompatActivity {

    public static final String ARG_EVENT_ID = "event_id";

    private int mEventId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle(getString(R.string.EventDetails));

        updateDetails();
    }

    @Override
    protected void onResume() {
        super.onResume();

        updateDetails();
    }

    public void updateDetails() {
        TextView textViewTitle = (TextView) findViewById(R.id.textViewTitle);
        TextView textViewDescription = (TextView) findViewById(R.id.textViewDescription);
        TextView textViewSharersInfo = (TextView) findViewById(R.id.textViewSharersInfo);
        TextView textViewAmount = (TextView) findViewById(R.id.textViewAmount);

        String currencySign = Repository.getCurrency().getSymbol();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mEventId = extras.getInt(ARG_EVENT_ID);

            Event event = Repository.getEventById(mEventId);


            if (event != null){
                double avg = event.getTotalAmount()/event.getSharers().size();
                textViewTitle.setText(event.getTitle());
                textViewDescription.setText(event.getDescription());
                textViewAmount.setText(currencySign + event.getTotalAmount());

                HashMap<User, Double> map = event.getResults();
                StringBuilder builder = new StringBuilder();
                for (Map.Entry<User, Double> entry: map.entrySet()) {
                    builder.append(entry.getKey().getName() + ": ");
                    builder.append(entry.getValue()>0?'+':'-');
                    builder.append(currencySign + Math.abs(entry.getValue()));
                    builder.append('\n');
                }
                TextView textviewStatus = (TextView) findViewById(R.id.textViewStatus);
                textviewStatus.setText(builder.toString());

                builder = new StringBuilder();
                for (Share share: event.getSharers()) {
                    double difference = share.getAmount() -avg;

                    if (share.getSharer() == Repository.getLoginedUser()) {
                        builder.append("You paid ");
                    }else {
                        builder.append(share.getSharer().getName() + " paid ");
                    }

                    builder.append(currencySign);
                    builder.append(share.getAmount());
                    builder.append(" that results ");
                    builder.append(difference>0?'+':'-');
                    builder.append(currencySign);
                    builder.append(Math.abs(Math.round(difference)));
                    builder.append('\n');

                }
                textViewSharersInfo.setText(builder.toString());
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.details_event, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.edit_event:
                Intent intent = new Intent(this, EditEventActivity.class);
                intent.putExtra(EditEventActivity.ARG_EVENT_ID, mEventId);
                startActivity(intent);
                return true;
            case R.id.delete_event:
                if (Repository.deleteEventById(mEventId)){
                    finish();
                }
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
