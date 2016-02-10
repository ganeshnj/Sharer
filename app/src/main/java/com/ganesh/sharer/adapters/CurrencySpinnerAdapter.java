package com.ganesh.sharer.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ganesh.sharer.R;
import com.ganesh.sharer.models.Currency;

/**
 * Created by Ganesh-XPS13 on 2/10/2016.
 */
public class CurrencySpinnerAdapter extends ArrayAdapter<Currency> {

    private Currency[] myCurrencyArray;
    private Activity context;

    public CurrencySpinnerAdapter(Activity context, int textViewResourceId,
                            Currency[] myObjs) {
        super(context, textViewResourceId, myObjs);
        this.myCurrencyArray = myObjs;
        this.context = context;
    }

    public int getCount() {
        return myCurrencyArray.length;
    }

    public Currency getItem(int position) {
        return myCurrencyArray[position];
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView,
                        ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    private View getCustomView(int position, View convertView,
                               ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View view = inflater.inflate(R.layout.row_layout_currency, parent, false);

        TextView textSymbol = (TextView) view
                .findViewById(R.id.textSymbol);
        textSymbol.setText(myCurrencyArray[position].getSymbol());
        TextView textDesc = (TextView) view
                .findViewById(R.id.textDesc);
        textDesc.setText(myCurrencyArray[position].getDesc());

        return view;
    }
}
