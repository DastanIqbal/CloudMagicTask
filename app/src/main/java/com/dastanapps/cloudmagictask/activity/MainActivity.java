package com.dastanapps.cloudmagictask.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dastanapps.cloudmagictask.R;
import com.dastanapps.cloudmagictask.adapter.MailListAdap;
import com.dastanapps.cloudmagictask.bean.MailB;
import com.dastanapps.dastanLib.networks.DastanRest;
import com.dastanapps.dastanLib.networks.IRestRequest;
import com.dastanapps.dastanLib.networks.RestAPI;
import com.dastanapps.dastanLib.networks.RestResponse;
import com.dastanapps.dastanLib.utils.ViewUtils;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements IRestRequest, MailListAdap.OnRecyclerViewItemClickListener {

    private RecyclerView recyclerView;
    private MailListAdap mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.rv_list);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        DastanRest.sentGetRequestArray(RestAPI.GET_ALL_MAIL, RestAPI.ID_ALL_MAIL, this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewUtils.showToast(MainActivity.this, "Write Mail");
            }
        });
    }


    @Override
    public void onResponse(int reqID, String resp) {
        if (reqID == RestAPI.ID_ALL_MAIL) {
            ArrayList<MailB> mailBArrayList = RestResponse.getAllMails(resp);
            mAdapter = new MailListAdap(MainActivity.this, mailBArrayList);
            mAdapter.setOnRecyclerViewItemClickListener(MainActivity.this);
            recyclerView.setAdapter(mAdapter);
        } else {

            ViewUtils.showToast(MainActivity.this, "No Response");
        }
    }

    @Override
    public void onError(String error) {
        ViewUtils.showToast(MainActivity.this, "Got Error:" + error);
    }

    @Override
    public void onRecyclerViewItemClickListener(int pos, View view) {
        MailB mailB = (MailB) view.getTag();
        Intent intent = new Intent(MainActivity.this, MailDetailsA.class);
        intent.putExtra("mail", mailB);
        startActivityForResult(intent, 2000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 2001) {
            if (data != null && data.getStringExtra("delete").equals("1"))
                DastanRest.sentGetRequestArray(RestAPI.GET_ALL_MAIL, RestAPI.ID_ALL_MAIL, this);
        }
    }
}
