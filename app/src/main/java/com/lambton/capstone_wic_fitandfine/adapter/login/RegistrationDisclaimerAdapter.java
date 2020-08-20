package com.lambton.capstone_wic_fitandfine.adapter.login;


import android.content.Intent;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.lambton.capstone_wic_fitandfine.R;
import com.lambton.capstone_wic_fitandfine.activities.RegistrationDisclaimerActivity;
import com.lambton.capstone_wic_fitandfine.util.JsonUtil;

import org.json.JSONObject;

import java.util.ArrayList;

public class RegistrationDisclaimerAdapter extends RecyclerView.Adapter<RegistrationDisclaimerAdapter.DisclaimerViewHolder> implements View.OnClickListener {

    private ArrayList<JSONObject> articles;
    private AppCompatActivity activity;

    public RegistrationDisclaimerAdapter(AppCompatActivity context, ArrayList<JSONObject> objects) {
        this.articles = objects;
        this.activity = context;
    }

    public static class DisclaimerViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView titleTextView;
        TextView descriptionTextView;
        ImageView chevronIconImageView;

        DisclaimerViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.card_reg_disclaimer);
            titleTextView = (TextView) itemView.findViewById(R.id.layout_item_reg_disclaimer_title);
            descriptionTextView = (TextView) itemView.findViewById(R.id.layout_item_reg_disclaimer_description);
            chevronIconImageView = (ImageView) itemView.findViewById(R.id.layout_item_reg_disclaimer_chevron_icon);
        }
    }

    @Override
    public DisclaimerViewHolder onCreateViewHolder(ViewGroup viewGroup, int index) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_item_reg_disclaimer, viewGroup, false);
        DisclaimerViewHolder viewHolder = new DisclaimerViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DisclaimerViewHolder viewHolder, int index) {

        if (index == articles.size()) {
            //for last index show register button and hides card.
            //Hide terms and show register layout only
            viewHolder.cardView.setVisibility(View.GONE);
        } else {
            //show terms and update UI
            viewHolder.cardView.setVisibility(View.VISIBLE);

            JSONObject jsonObject = articles.get(index);

            viewHolder.titleTextView.setText(JsonUtil.getStringFromJsonObject(jsonObject, "title"));
            viewHolder.descriptionTextView.setText(Html.fromHtml(JsonUtil.getStringFromJsonObject(jsonObject, "description")));

            boolean isExpanded = JsonUtil.getBooleanFromJsonObject(jsonObject, "isExpanded");

            if (isExpanded) {
                viewHolder.descriptionTextView.setVisibility(View.VISIBLE);
                viewHolder.chevronIconImageView.setImageResource(R.drawable.chevron_down);
            } else {
                viewHolder.descriptionTextView.setVisibility(View.GONE);
                viewHolder.chevronIconImageView.setImageResource(R.drawable.chevron_right);
            }
        }
        viewHolder.chevronIconImageView.setTag(index);
        viewHolder.chevronIconImageView.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return articles.size() + 1;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_item_reg_disclaimer_chevron_icon:
                startDetailActivity(view);
                break;
        }
    }

    private void startDetailActivity(View view){
        int index = (int) view.getTag();
        JSONObject jsonObject = articles.get(index);

        Intent intent = new Intent(this.activity, RegistrationDisclaimerActivity.class);
        intent.putExtra("json",jsonObject.toString());

        activity.startActivity(intent);
    }
}

