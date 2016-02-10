package com.ganesh.sharer.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ganesh.sharer.R;
import com.ganesh.sharer.Repository;
import com.ganesh.sharer.models.Settlement;

import java.util.List;

/**
 * Created by Ganesh-XPS13 on 2/10/2016.
 */
public class SettlementArrayAdapter extends ArrayAdapter<Settlement>{
    private Activity context;
    private List<Settlement> settlements;
    private String currecySign = Repository.getCurrency().getSymbol();

    static class ViewHolder {
        public TextView textViewName;
        public TextView textViewAmount;
    }

    public SettlementArrayAdapter(Activity context, int resource, List<Settlement> objects) {
        super(context, resource, objects);
        this.context = context;
        this.settlements = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;

        if(rowView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.row_layout_settlements, null);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.textViewName = (TextView) rowView.findViewById(R.id.textViewName);
            viewHolder.textViewAmount = (TextView) rowView.findViewById(R.id.textViewAmount);
            rowView.setTag(viewHolder);
        }

        ViewHolder holder = (ViewHolder) rowView.getTag();
        Settlement settlement = settlements.get(position);
        holder.textViewName.setText(settlement.getTaker().getName());
        holder.textViewAmount.setText(currecySign+String.valueOf(settlement.getAmount()));

        return rowView;
    }
}
