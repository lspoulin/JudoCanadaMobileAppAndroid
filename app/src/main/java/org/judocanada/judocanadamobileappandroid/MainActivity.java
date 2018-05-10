package org.judocanada.judocanadamobileappandroid;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static ProgressBar progressBar;
    private List<Fragment> fragments;
    private ImageButton btnVideo, btnStats, btnNews;
    private TextView txtNews, txtVideo, txtStats;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragments = new ArrayList<Fragment>();
        fragments.add(new PostsFragment());
        fragments.add(new VideoFragment());
        fragments.add(new StatsFragment());

        final int WHITE = ContextCompat.getColor(MainActivity.this, R.color.white);
        final int GRAY = ContextCompat.getColor(MainActivity.this, R.color.darker_gray);


        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnVideo = (ImageButton) findViewById(R.id.btnVideo);
        btnStats = (ImageButton) findViewById(R.id.btnStats);
        btnNews = (ImageButton) findViewById(R.id.btnNews);

        txtNews = (TextView) findViewById(R.id.txtNews);
        txtStats = (TextView) findViewById(R.id.txtStats);
        txtVideo = (TextView) findViewById(R.id.txtVideo);

        btnVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.mainFragment, fragments.get(1));
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.commit();
                btnNews.setImageResource(R.drawable.news_grey);
                btnStats.setImageResource(R.drawable.stats_grey);
                btnVideo.setImageResource(R.drawable.video);
                txtNews.setTextColor(GRAY);
                txtStats.setTextColor(GRAY);
                txtVideo.setTextColor(WHITE);
            }
        });

        btnStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.mainFragment, fragments.get(2));
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.commit();
                btnNews.setImageResource(R.drawable.news_grey);
                btnStats.setImageResource(R.drawable.stats);
                btnVideo.setImageResource(R.drawable.video_grey);
                txtNews.setTextColor(GRAY);
                txtStats.setTextColor(WHITE);
                txtVideo.setTextColor(GRAY);
            }
        });

        btnNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.mainFragment, fragments.get(0));
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.commit();
                btnNews.setImageResource(R.drawable.news);
                btnStats.setImageResource(R.drawable.stats_grey);
                btnVideo.setImageResource(R.drawable.video_grey);
                txtNews.setTextColor(WHITE);
                txtStats.setTextColor(GRAY);
                txtVideo.setTextColor(GRAY);

            }
        });
    }

    public static void showProgressBar(boolean visible){
        if(progressBar == null) return;
        if(visible){
            progressBar.setVisibility(View.VISIBLE);
        }
        else{
            progressBar.setVisibility(View.GONE);
        }

    }


}
