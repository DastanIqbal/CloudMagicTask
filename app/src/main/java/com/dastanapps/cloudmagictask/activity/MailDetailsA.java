package com.dastanapps.cloudmagictask.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dastanapps.cloudmagictask.R;
import com.dastanapps.cloudmagictask.bean.MailB;
import com.dastanapps.cloudmagictask.bean.MailDetailsB;
import com.dastanapps.cloudmagictask.bean.MailParticipantB;
import com.dastanapps.dastanLib.networks.DastanRest;
import com.dastanapps.dastanLib.networks.IRestRequest;
import com.dastanapps.dastanLib.networks.RestAPI;
import com.dastanapps.dastanLib.networks.RestResponse;
import com.dastanapps.dastanLib.utils.DateTimeUtils;
import com.dastanapps.dastanLib.utils.ViewUtils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.dastanapps.dastanLib.networks.RestAPI.ID_DELETE_MAIL;

public class MailDetailsA extends AppCompatActivity implements IRestRequest, View.OnClickListener {

    @Bind(R.id.tv_subject)
    public TextView tv_subject;
    @Bind(R.id.tv_sender)
    public TextView tv_sender;

    @Bind(R.id.tv_particpants)
    public TextView tv_participants;
    @Bind(R.id.tv_timestamp)
    public TextView tv_timestamp;
    @Bind(R.id.tv_body)
    public TextView tv_body;


    @Bind(R.id.imv_starred)
    public ImageView imv_starred;
    @Bind(R.id.imv_reply)
    public ImageView imv_reply;
    @Bind(R.id.imv_overflow)
    public ImageView imv_overflow;
    private ArrayList<MailParticipantB> mailParticipantBs;
    private MailDetailsB mailDetailsB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail_details);

        ButterKnife.bind(this);

        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imv_overflow.setOnClickListener(this);
        imv_reply.setOnClickListener(this);
        imv_starred.setOnClickListener(this);
        if (getIntent() != null) {
            MailB mailb = (MailB) getIntent().getParcelableExtra("mail");

            tv_subject.setText(mailb.subject);
            if (mailb.isStarred.equals("true")) {
                imv_starred.setImageResource(R.drawable.ic_star_black_24dp);
            } else {
                imv_starred.setImageResource(R.drawable.ic_star_border_black_24dp);
            }

            ArrayList<String> participantsList = RestResponse.getAllParticipants(mailb.participants);
            if (participantsList.size() != 0) {
                for (int i = 0; i < participantsList.size(); i++) {
                    if (i == 0)
                        tv_participants.setText(participantsList.get(i));
                    else
                        tv_participants.append(" , " + participantsList.get(i));
                }
            }

            String api = String.format(RestAPI.GET_MAIL, mailb.mailId);
            DastanRest.sentGetRequestObj(api, RestAPI.ID_GET_MAIL, this);
        } else {
            finish();
            ViewUtils.showToast(MailDetailsA.this, "Something went wrong");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_mail_details_top, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.mnu_archive:
                ViewUtils.showToast(MailDetailsA.this, "Archived");
                break;
            case R.id.mnu_delete:
                String api = String.format(RestAPI.DELETE_MAIL, mailDetailsB.mailId);
                DastanRest.deleteRequestObj(api, ID_DELETE_MAIL, this);
                break;
            case R.id.mnu_moveto:
                ViewUtils.showToast(MailDetailsA.this, "Move to");
                break;
            case R.id.mnu_mails:
                finish();
                break;
            case R.id.mnu_markspam:
                ViewUtils.showToast(MailDetailsA.this, "Mark as a Spam");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResponse(int reqID, String resp) {
        if (reqID == RestAPI.ID_GET_MAIL) {
            mailDetailsB = RestResponse.getMail(resp);
            setView();
        } else if (reqID == RestAPI.ID_DELETE_MAIL) {
            ViewUtils.showToast(MailDetailsA.this, RestResponse.getDeleteMsg(resp));
            Intent intent=new Intent();
            intent.putExtra("delete","1");
            setResult(2001,intent);
            finish();
        } else {
            ViewUtils.showToast(MailDetailsA.this, "No Response");
        }
    }

    @Override
    public void onError(String error) {
        ViewUtils.showToast(MailDetailsA.this, "Got Error:" + error);
    }

    public void setView() {
        tv_body.setText(mailDetailsB.body);
        mailParticipantBs = RestResponse.getAllParticipantsB(mailDetailsB.participants);
        if (mailParticipantBs.size() != 0)
            tv_sender.setText(mailParticipantBs.get(0).name);
        tv_timestamp.setText(DateTimeUtils.timestampToHumanDate(mailDetailsB.timestamp));
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.imv_starred) {
            ViewUtils.showToast(MailDetailsA.this, "Mark Starred");
        } else if (view.getId() == R.id.imv_overflow) {
            PopupMenu popup = new PopupMenu(MailDetailsA.this, view);
            MenuInflater inflater = popup.getMenuInflater();
            inflater.inflate(R.menu.menu_mail_details, popup.getMenu());
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.mnu_reply:
                            ViewUtils.showToast(MailDetailsA.this, "Reply Mail");
                            break;
                        case R.id.mnu_forward:
                            ViewUtils.showToast(MailDetailsA.this, "Forward");
                            break;
                        case R.id.mnu_create_cal_event:
                            ViewUtils.showToast(MailDetailsA.this, "Event Created");
                            break;
                        case R.id.mnu_print:
                            ViewUtils.showToast(MailDetailsA.this, "Printing");
                            break;
                        case R.id.mnu_save_emailto:
                            ViewUtils.showToast(MailDetailsA.this, "Email Saved");
                            break;
                        case R.id.mnu_show_details:
                            ViewUtils.showToast(MailDetailsA.this, "Show Details");
                            break;

                    }
                    return false;
                }
            });
            popup.show();
        } else if (view.getId() == R.id.imv_reply) {
            ViewUtils.showToast(MailDetailsA.this, "Reply Mail");
        }
    }
}
