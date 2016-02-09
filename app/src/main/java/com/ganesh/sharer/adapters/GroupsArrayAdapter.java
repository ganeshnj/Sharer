package com.ganesh.sharer.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ganesh.sharer.R;
import com.ganesh.sharer.models.Group;

import java.util.List;

/**
 * Created by Ganesh-XPS13 on 1/13/2016.
 */
public class GroupsArrayAdapter extends ArrayAdapter<Group> {
    private Activity context;
    private List<Group> groups;

    public GroupsArrayAdapter(Activity context, int resource, List<Group> objects) {
        super(context, resource, objects);
        this.context = context;
        this.groups = objects;
    }

    static class ViewHolder {
        public TextView textViewTitle;
        public TextView textViewDescription;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;

        if(rowView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.row_layout_groups, null);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.textViewTitle = (TextView) rowView.findViewById(R.id.textview_title);
            viewHolder.textViewDescription = (TextView) rowView.findViewById(R.id.textview_description);
            rowView.setTag(viewHolder);
        }

        ViewHolder holder = (ViewHolder) rowView.getTag();
        Group group = groups.get(position);
        holder.textViewTitle.setText(group.getTitle());
        holder.textViewDescription.setText(group.getDescription());

        return rowView;
    }
}
