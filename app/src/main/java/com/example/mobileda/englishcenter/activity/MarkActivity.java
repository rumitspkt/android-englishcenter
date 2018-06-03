package com.example.mobileda.englishcenter.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.mobileda.englishcenter.R;
import com.example.mobileda.englishcenter.adapter.PaperMarkAdapter;


import butterknife.BindView;
import butterknife.ButterKnife;

public class MarkActivity extends AppCompatActivity {

    @BindView(R.id.view_pager)
    ViewPager pager;

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark);
        ButterKnife.bind(this);
        FragmentManager manager = getSupportFragmentManager();
        PaperMarkAdapter adapter = new PaperMarkAdapter(manager);
        pager.setAdapter(adapter);
        tabLayout.setupWithViewPager(pager);
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setTabsFromPagerAdapter(adapter);
    }
}
