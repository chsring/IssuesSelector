package io.aifo.issuesselector;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.daimajia.slider.library.SliderLayout;


/**
 * Created by daimajia on 14-5-29.
 */
public class TransformerAdapter extends BaseAdapter {
    private Context mContext;

    public TransformerAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return SliderLayout.Transformer.values().length;
    }

    @Override
    public Object getItem(int position) {
        return SliderLayout.Transformer.values()[position].toString();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view =  LayoutInflater.from(mContext).inflate(R.layout.item_layout, null);
        TextView t = (TextView) view.findViewById(R.id.list_item_text);
        t.setText(getItem(position).toString());
        return t;
    }
}
