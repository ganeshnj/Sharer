package com.ganesh.sharer.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ganesh.sharer.R;
import com.ganesh.sharer.models.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ganesh-XPS13 on 2/9/2016.
 */
public class FriendsCheckArrayAdapter extends ArrayAdapter<User> {
    private Activity context;
    private List<User> users;
    private List<Boolean> isSelecteds;

    static class ViewHolder {
        public TextView textViewName;
        public TextView textViewEmail;
        public CheckBox checkBoxIsSelected;
    }

    public FriendsCheckArrayAdapter(Activity context, int resource, List<User> objects) {
        super(context, resource, objects);
        this.context = context;
        this.users = objects;
        this.isSelecteds = new ArrayList<>();
        for (User user: users) {
            this.isSelecteds.add(false);
        }
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View rowView = convertView;

        if(rowView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.row_layout_friends_check, null);

            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.textViewName = (TextView) rowView.findViewById(R.id.textview_name);
            viewHolder.textViewEmail = (TextView) rowView.findViewById(R.id.textview_email);
            viewHolder.checkBoxIsSelected = (CheckBox) rowView.findViewById(R.id.checkboxIsSelected);
            viewHolder.checkBoxIsSelected.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isSelecteds.set(position, viewHolder.checkBoxIsSelected.isChecked());
                }
            });
            rowView.setTag(viewHolder);
        }

        final ViewHolder holder = (ViewHolder) rowView.getTag();
        User user = users.get(position);
        holder.textViewName.setText(user.getFirstname() + " " + user.getLastname());
        holder.textViewEmail.setText(user.getEmail());
        holder.checkBoxIsSelected.setChecked(isSelecteds.get(position));
        holder.checkBoxIsSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSelecteds.set(position, holder.checkBoxIsSelected.isChecked());
            }
        });

        return rowView;
    }

    public ArrayList<User> getSelectedUsers(){
        ArrayList<User> selectedUsers = new ArrayList<>();
        int i = 0;
        for (User user : users) {
            if (isSelecteds.get(i)){
                selectedUsers.add(user);
            }
            i++;
        }
        return selectedUsers;
    }
}
