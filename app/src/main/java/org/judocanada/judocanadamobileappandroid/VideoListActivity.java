package org.judocanada.judocanadamobileappandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class VideoListActivity extends AppCompatActivity {
    private ListView videoList;
    private ProgressBar progressBar;
    private ApiHelper apiHelper;
    private ArrayList<Video> videoArrayList;
    private CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);

        videoList = (ListView) findViewById(R.id.mainListView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        videoArrayList = new ArrayList<Video>();


        customAdapter = new CustomAdapter();
        videoList.setAdapter(customAdapter);

        apiHelper = new ApiHelper();
        progressBar.setVisibility(View.VISIBLE);
        apiHelper.getVideos(this, new Callback() {
            @Override
            public void methodToCallBack(Object object) {
                progressBar.setVisibility(View.GONE);
                if (object == null) return;
                VideoList tempList = (VideoList) object;
                if(tempList== null) return;
                videoArrayList = tempList.getVideos();
                customAdapter.notifyDataSetChanged();
            }
        });

        videoList.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
                intent  = new Intent(VideoListActivity.this, VideoActivity.class);

                intent.putExtra(VideoActivity.VIDEO, videoArrayList.get(position));
                startActivity(intent);
            }
        });
    }

    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return videoArrayList.size();
        }

        @Override
        public Object getItem(int i) {
            return videoArrayList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.post_row, null);
            TextView title = (TextView) view.findViewById(R.id.txtTitle);
            TextView exerpt = (TextView) view.findViewById(R.id.txtExerpt);

            Video v = videoArrayList.get(i);
            title.setText(v.getTitle());
            exerpt.setVisibility(View.GONE);
            return view;
        }
    }
}
