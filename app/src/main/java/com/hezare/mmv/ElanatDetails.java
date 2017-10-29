package com.hezare.mmv;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.hezare.mmv.Adapter.ElanatListAdapter;
import com.hezare.mmv.Models.ElanatListModel;

import java.util.ArrayList;
import java.util.List;

public class ElanatDetails extends AppCompatActivity{




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.elanattlist_details);
        Utli.changeFont(getWindow().getDecorView());




        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ((TextView)findViewById(R.id.elan_details_title)).setText(getIntent().getStringExtra("title"));
        ((TextView)findViewById(R.id.elan_details_matn)).setText(getIntent().getStringExtra("matn"));
        ((TextView)findViewById(R.id.elan_details_date)).setText(getIntent().getStringExtra("date"));
    }

}

