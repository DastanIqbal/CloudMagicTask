package com.dastanapps.cloudmagictask.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dastanapps.cloudmagictask.R;
import com.dastanapps.cloudmagictask.bean.MailB;
import com.dastanapps.dastanLib.networks.RestResponse;
import com.dastanapps.dastanLib.utils.DateTimeUtils;
import com.dastanapps.dastanLib.utils.FontUtils;
import com.dastanapps.dastanLib.utils.ViewUtils;

import java.util.ArrayList;

/**
 * *@author : Dastan Iqbal
 *
 * @email : iqbal.ahmed@mebelkart.com
 */
public class MailListAdap extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int MAIL = 1;
    public static final int PROGRESS = 2;

    public static OnRecyclerViewItemClickListener itemClickListener;
    private Context ctxt;
    public ArrayList<MailB> mailBArrayList;
    private boolean isProgress = true;
    private static MailB MailB;

    public MailListAdap(Context ctxt, ArrayList<MailB> mailBArrayList) {
        this.ctxt = ctxt;
        this.mailBArrayList = mailBArrayList;
    }

    public boolean isProgress() {
        return isProgress;
    }

    public void setIsProgress(boolean isProgress) {
        this.isProgress = isProgress;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh = null;
        if (viewType == MAIL) {
            View view = LayoutInflater.from(ctxt).inflate(R.layout.mail_listitem, parent, false);
            vh = new CardViewHolder(view);
        } else /*if (viewType == PROGRESS) */ {
            View view = LayoutInflater.from(ctxt).inflate(R.layout.app_progressbar, parent, false);
            vh = new ProgressViewHolder(view);
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final MailB mailB = mailBArrayList.get(position);
        if (holder instanceof ProgressViewHolder) {
            if (isProgress) {
                ((ProgressViewHolder) holder).progressBar.setVisibility(View.VISIBLE);
            } else {
                ((ProgressViewHolder) holder).progressBar.setVisibility(View.GONE);
            }
        } else if (holder instanceof CardViewHolder) {
            final CardViewHolder cardHold = ((CardViewHolder) holder);

            ArrayList<String> participantsList = RestResponse.getAllParticipants(mailB.participants);
            if (participantsList.size() != 0) {
                for (int i = 0; i < participantsList.size(); i++) {
                    if (i == 0)
                        cardHold.tv_participants.setText(participantsList.get(i));
                    else
                        cardHold.tv_participants.append(" , " + participantsList.get(i));
                }
            }

            cardHold.tv_preview.setText(mailB.preview);
            cardHold.tv_subject.setText(mailB.subject);
            cardHold.tv_timestamp.setText(DateTimeUtils.timestampToHumanDate(mailB.timestamp));
            if (mailB.isStarred.equals("true"))
                cardHold.imv_starred.setImageResource(R.drawable.ic_star_black_18dp);
            else
                cardHold.imv_starred.setImageResource(R.drawable.ic_star_border_black_18dp);

            if (mailB.isRead.equals("true"))
                cardHold.rl_item.setBackgroundColor(ctxt.getResources().getColor(R.color.preview));
            else
                cardHold.rl_item.setBackgroundColor(ctxt.getResources().getColor(R.color.mail_item_bg));


            cardHold.imv_starred.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ViewUtils.showToast(ctxt, "Mark Starred");
                }
            });
            cardHold.rl_item.setTag(mailB);
        }
    }


    @Override
    public int getItemCount() {
        return mailBArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        MailB mailB = mailBArrayList.get(position);
        if (position == mailBArrayList.size()) /* to show progress bar at bottom if (position == mailBArrayList.size() - 1)*/ {
            return PROGRESS;
        } else {
            return MAIL;
        }
    }


    public interface OnRecyclerViewItemClickListener {
        void onRecyclerViewItemClickListener(int pos, View view);
    }

    public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener onRecyclerViewItemClickListener) {
        itemClickListener = onRecyclerViewItemClickListener;
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tv_preview,
                tv_subject,
                tv_participants,
                tv_timestamp;
        private ImageView imv_avatar,
                imv_starred;
        private View rl_item;

        public CardViewHolder(View itemView) {
            super(itemView);
            tv_preview = (TextView) itemView.findViewById(R.id.tv_preview);
            tv_subject = (TextView) itemView.findViewById(R.id.tv_subject);
            tv_participants = (TextView) itemView.findViewById(R.id.tv_particpants);
            tv_timestamp = (TextView) itemView.findViewById(R.id.tv_timestamp);
            tv_subject = (TextView) itemView.findViewById(R.id.tv_subject);
            imv_avatar = (ImageView) itemView.findViewById(R.id.imv_avatar);
            imv_starred = (ImageView) itemView.findViewById(R.id.imv_starred);
            rl_item = itemView.findViewById(R.id.rl_item);
            rl_item.setOnClickListener(this);
            FontUtils.setRobotoRegular(tv_preview, tv_subject, tv_participants, tv_timestamp, tv_subject);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onRecyclerViewItemClickListener(getPosition(), v);
        }
    }

    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public ProgressViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
        }
    }
}
