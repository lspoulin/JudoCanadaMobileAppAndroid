package org.judocanada.judocanadamobileappandroid;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import com.dailymotion.android.player.sdk.PlayerWebView;

import java.util.HashMap;
import java.util.Map;


public class VideoActivity extends AppCompatActivity {
    public static final String VIDEO = "org.judocanada.judocanadamobileappandroid.VideoActivity.VIDEO";
    private PlayerWebView videoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        Intent intent = getIntent();
        Video video = (Video)intent.getParcelableExtra(this.VIDEO);

        videoView = (PlayerWebView) findViewById(R.id.dm_player_web_view);
        Map<String, String> playerParams = new HashMap<>();

        videoView.load(video.getId(), playerParams);
    }

    @Override
    protected void onPause() {
        super.onPause();

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
            videoView.onPause();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
            videoView.onResume();
        }
    }
}
