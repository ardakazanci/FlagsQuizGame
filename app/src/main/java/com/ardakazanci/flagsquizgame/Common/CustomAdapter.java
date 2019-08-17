package com.ardakazanci.flagsquizgame.Common;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ardakazanci.flagsquizgame.Model.Ranking;
import com.ardakazanci.flagsquizgame.R;

import java.util.List;

public class CustomAdapter extends BaseAdapter {

    private Context context;
    private List<Ranking> lstRanking;

    public CustomAdapter(Context context, List<Ranking> lstRanking) {
        this.context = context;
        this.lstRanking = lstRanking;
    }

    @Override
    public int getCount() {
        return lstRanking.size();
    }

    @Override
    public Object getItem(int position) {
        return lstRanking.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = layoutInflater.inflate(R.layout.row, null);

        ImageView imageTop = view.findViewById(R.id.imgTop);
        TextView txtTop = view.findViewById(R.id.txtTop);

        if (position == 0)
            imageTop.setImageResource(R.drawable.top1);
        else if (position == 1)
            imageTop.setImageResource(R.drawable.top2);
        else
            imageTop.setImageResource(R.drawable.top3);

        txtTop.setText(String.valueOf(lstRanking.get(position).getRanking_score()));


        return view;
    }
}
