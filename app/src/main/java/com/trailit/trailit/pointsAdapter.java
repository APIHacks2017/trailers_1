package com.trailit.trailit;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by theMachine on 17-06-2017.
 */

public class pointsAdapter extends RecyclerView.Adapter<pointsAdapter.MyViewHolder> {

    private List<point> points;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name,type;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.place_name);
            type = (TextView) view.findViewById(R.id.place_type);
        }
    }


    public pointsAdapter(List<point> pList) {
        this.points = pList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.places_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        point p = points.get(position);
        holder.name.setText(p.getpName());
        holder.type.setText(p.getpType());
    }

    public void updateList(List<point> list){
        points=list;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return points.size();
    }
}
