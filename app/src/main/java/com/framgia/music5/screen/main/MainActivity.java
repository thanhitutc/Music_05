package com.framgia.music5.screen.main;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import com.framgia.music5.R;

/**
 * main
 **/
public class MainActivity extends AppCompatActivity {
    public static final int PAGE_LIMIT = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolbar();
        initComponents();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        setTitle(getBaseContext().getResources().getString(R.string.app_name));
    }

    private void initComponents() {
        ViewPager pager = (ViewPager) findViewById(R.id.view_pager);
        pager.setAdapter(new MainPagerAdapter(getBaseContext(), getSupportFragmentManager()));
        pager.setOffscreenPageLimit(PAGE_LIMIT);
    }
}

