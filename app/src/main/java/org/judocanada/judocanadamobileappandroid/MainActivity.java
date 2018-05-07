package org.judocanada.judocanadamobileappandroid;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements CallBack{
    private ListView mainListView;
    private ArrayList<Post> posts;
    private CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        posts = new ArrayList<Post>();
        ApiManager api = new ApiManager();
        api.getPosts(this, this);

        mainListView = (ListView) findViewById(R.id.listPosts);
        customAdapter = new CustomAdapter();
        mainListView.setAdapter(customAdapter);
        mainListView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent;
                intent  = new Intent(MainActivity.this, PostActivity.class);
                intent.putExtra(PostActivity.POST, posts.get((int)id));
                startActivity(intent);
            }
        });
    }

    @Override
    public void methodToCallBack(Object object) {
        ArrayList<Post> tempsPosts = ((ArrayList<Post>) object);
        if (posts == null) return;

        posts = tempsPosts;
        customAdapter.notifyDataSetChanged();
    }

    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {

            return posts.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
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

            Post p = posts.get(i);
            title.setText(p.getTitle());
            exerpt.setText(p.getExerpt());
            return view;
        }
    }

}
