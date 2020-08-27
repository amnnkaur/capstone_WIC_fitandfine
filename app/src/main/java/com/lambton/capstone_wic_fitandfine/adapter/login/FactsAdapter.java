package com.lambton.capstone_wic_fitandfine.adapter.login;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.lambton.capstone_wic_fitandfine.R;
import com.lambton.capstone_wic_fitandfine.models.Facts;

import java.util.List;

public class FactsAdapter extends RecyclerView.Adapter<FactsAdapter.MyViewHolder> {

    private List<Facts> levelsList;

    public FactsAdapter (List<Facts> levelsList) {
        this.levelsList = levelsList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView activity;
        public TextView description;
        public ImageView image;
        public  TextView levels;

        public MyViewHolder(View view) {
            super(view);
            activity = (TextView) view.findViewById(R.id.activity_levels);
            description = (TextView) view.findViewById(R.id.levels_description);
            image = (ImageView) view.findViewById(R.id.levels_image);
            levels = (TextView) view.findViewById(R.id.levels);

        }

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_facts, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Facts levels = levelsList.get(position);
        holder.activity.setText(levels.getActivity());
        holder.description.setText(levels.getDescription());
        holder.levels.setText(levels.getLevels());
        holder.image.setImageResource(levels.getImage());

    }

    @Override
    public int getItemCount() {
        return levelsList.size();
    }
}

