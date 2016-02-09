package com.ganesh.sharer.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ganesh.sharer.R;
import com.ganesh.sharer.models.User;

import java.util.List;

/**
 * Created by Ganesh-XPS13 on 12/15/2015.
 */
public class FriendsArrayAdapter extends ArrayAdapter<User> {

    private Activity context;
    private List<User> users;

    public FriendsArrayAdapter(Context context, int resource, List<User> objects, Activity context1, List<User> users) {
        super(context, resource, objects);
        context = context1;
        this.users = users;
    }

    static class ViewHolder {
        public TextView textViewName;
        public TextView textViewEmail;
    }

    public FriendsArrayAdapter(Activity context, int resource, List<User> objects) {
        super(context, resource, objects);
        this.context = context;
        this.users = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;

        if(rowView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.row_layout_friends, null);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.textViewName = (TextView) rowView.findViewById(R.id.textview_name);
            viewHolder.textViewEmail = (TextView) rowView.findViewById(R.id.textview_email);
            rowView.setTag(viewHolder);
        }

        ViewHolder holder = (ViewHolder) rowView.getTag();
        User user = users.get(position);
        holder.textViewName.setText(user.getFirstname() + " " + user.getLastname());
        holder.textViewEmail.setText(user.getEmail());

        return rowView;
    }
}