package com.ganesh.sharer.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Entity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ganesh.sharer.R;
import com.ganesh.sharer.models.Event;
import com.ganesh.sharer.models.User;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * Created by Ganesh-XPS13 on 1/13/2016.
 */
public class EventsArrayAdapter extends ArrayAdapter<Event> {

    private Activity context;
    private List<Event> events;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");


    static class ViewHolder {
        public TextView textViewCreatedOn;
        public TextView textViewTitle;
        public TextView textViewDescription;
        public TextView textViewSharedBy;
        public TextView textViewAmount;
    }

    public EventsArrayAdapter(Activity context, int resource, List<Event> objects) {
        super(context, resource, objects);
        this.context = context;
        this.events = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;

        if(rowView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.row_layout_events, null);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.textViewCreatedOn = (TextView) rowView.findViewById(R.id.textview_created_on);
            viewHolder.textViewTitle = (TextView) rowView.findViewById(R.id.textview_title);
            viewHolder.textViewDescription = (TextView) rowView.findViewById(R.id.textview_description);
            viewHolder.textViewSharedBy = (TextView) rowView.findViewById(R.id.textview_shared_by);
            viewHolder.textViewAmount = (TextView) rowView.findViewById(R.id.textview_amount);

            rowView.setTag(viewHolder);
        }

        ViewHolder holder = (ViewHolder) rowView.getTag();
        Event event = events.get(position);
        holder.textViewCreatedOn.setText(simpleDateFormat.format(event.getCreatedOn().getTime()));
        holder.textViewTitle.setText(event.getTitle());
        holder.textViewDescription.setText(event.getDescription());

        StringBuilder stringBuilder = new StringBuilder();
        double amount = 0;

        for (Map.Entry<User, Double> entry: event.getSharedBy().entrySet()) {
            stringBuilder.append(entry.getKey().getUsername());
            stringBuilder.append(", ");
            amount += entry.getValue();
        }

        holder.textViewSharedBy.setText(stringBuilder.toString());
        holder.textViewAmount.setText(String.valueOf(amount));


        return rowView;
    }
}

