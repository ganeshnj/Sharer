package com.ganesh.sharer.adapters;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ganesh.sharer.R;
import com.ganesh.sharer.Repository;
import com.ganesh.sharer.models.Share;
import com.ganesh.sharer.models.User;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Ganesh-XPS13 on 2/10/2016.
 */
public class ShareArrayAdapter extends ArrayAdapter<Share> {
    private Activity context;
    private List<Share> shares;

    static class ViewHolder {
        public TextView textViewName;
        public TextView editTextAmount;
        public TextView textViewCurrency;
    }

    public ShareArrayAdapter(Activity context, int resource, List<Share> objects) {
        super(context, resource, objects);
        this.context = context;
        this.shares = objects;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View rowView = convertView;

        if(rowView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.row_layout_share, null);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.textViewCurrency = (TextView) rowView.findViewById(R.id.textviewCurrencySign);
            viewHolder.textViewName = (TextView) rowView.findViewById(R.id.textview_name);
            viewHolder.editTextAmount = (TextView) rowView.findViewById(R.id.editTextAmount);
            viewHolder.editTextAmount.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (!s.toString().isEmpty())
                        shares.get(position).setAmount(Double.valueOf(s.toString()));
                }
            });
            rowView.setTag(viewHolder);
        }

        ViewHolder holder = (ViewHolder) rowView.getTag();
        Share share = shares.get(position);
        if (share.getSharer() == Repository.getLoginedUser()){
            holder.textViewName.setText("You");
        }
        else {
            holder.textViewName.setText(share.getSharer().getName());
        }

        holder.editTextAmount.setText(String.valueOf(share.getAmount()));

        return rowView;
    }
}
