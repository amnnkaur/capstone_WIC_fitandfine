package com.lambton.capstone_wic_fitandfine.adapter;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lambton.capstone_wic_fitandfine.R;
import com.lambton.capstone_wic_fitandfine.activities.FAQActivity;
import com.lambton.capstone_wic_fitandfine.models.QA;

import java.util.List;

public class FaqAdapter extends RecyclerView.Adapter<FaqAdapter.MyViewHolder> {

    private List<QA> qaList;
    private FAQActivity activity;


    public FaqAdapter(List<QA> qas, FAQActivity activity) {
        this.qaList = qas;
        this.activity = activity;
    }

    @NonNull
    @Override
    public FaqAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.question_answer, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final FaqAdapter.MyViewHolder holder, final int position) {
        final boolean[] bool = {false};
        QA qa = qaList.get(position);
        holder.question.setText(qa.getQuestion());
        switch (position) {
            case 4: {
                SpannableString ss = new SpannableString(qa.getAnswer());
                ClickableSpan clickableSpan = new ClickableSpan() {
                    @Override
                    public void onClick(View textView) {
                        activity.mail();
                    }

                    @Override
                    public void updateDrawState(TextPaint ds) {
                        super.updateDrawState(ds);
                        ds.setUnderlineText(false);
                    }
                };
                ss.setSpan(clickableSpan, 96, 117, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                holder.answer.setText(ss);
                holder.answer.setMovementMethod(LinkMovementMethod.getInstance());
                holder.answer.setHighlightColor(Color.TRANSPARENT);
                break;
            }
            case 5: {
                SpannableString ss = new SpannableString(qa.getAnswer());
                ClickableSpan clickableSpan = new ClickableSpan() {
                    @Override
                    public void onClick(View textView) {
                        activity.mail();
                    }

                    @Override
                    public void updateDrawState(TextPaint ds) {
                        super.updateDrawState(ds);
                        ds.setUnderlineText(false);
                    }
                };
                ss.setSpan(clickableSpan, 159, 180, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                holder.answer.setText(ss);
                holder.answer.setMovementMethod(LinkMovementMethod.getInstance());
                holder.answer.setHighlightColor(Color.TRANSPARENT);
                break;
            }
            case 6: {
                SpannableString ss = new SpannableString(qa.getAnswer());
                ClickableSpan clickableSpan = new ClickableSpan() {
                    @Override
                    public void onClick(View textView) {
                        activity.mail();
                    }

                    @Override
                    public void updateDrawState(TextPaint ds) {
                        super.updateDrawState(ds);
                        ds.setUnderlineText(false);
                    }
                };
                ss.setSpan(clickableSpan, 25, 46, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                holder.answer.setText(ss);
                holder.answer.setMovementMethod(LinkMovementMethod.getInstance());
                holder.answer.setHighlightColor(Color.TRANSPARENT);
                break;
            }
            default:
                holder.answer.setText(qa.getAnswer());
                break;
        }
        holder.question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!bool[0]) {
                    if (position == 2)
                        holder.mfp.setVisibility(View.VISIBLE);
                    holder.answer.setVisibility(View.VISIBLE);
                    bool[0] = true;
                } else {
                    if (position == 2)
                        holder.mfp.setVisibility(View.GONE);
                    holder.answer.setVisibility(View.GONE);
                    bool[0] = false;
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return qaList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView answer;
        private Button question, mfp;

        public MyViewHolder(View view) {
            super(view);
            question = view.findViewById(R.id.question);
            answer = view.findViewById(R.id.answer);
            mfp = view.findViewById(R.id.myfitnesspalBtn);

        }
    }
}
