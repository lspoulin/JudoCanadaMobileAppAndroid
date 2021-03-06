package org.judocanada.judocanadamobileappandroid;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.judocanada.judocanadamobileappandroid.Api.ApiHelper;
import org.judocanada.judocanadamobileappandroid.Api.Callback;
import org.judocanada.judocanadamobileappandroid.Model.Post;

import java.util.ArrayList;

public class PostsFragment extends Fragment {
    private ListView mainListView;
    private ArrayList<Post> posts;
    private CustomAdapter customAdapter;
    private ApiHelper apiHelper;
    private ProgressBar progressBar;

    public PostsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_post, container, false);

        mainListView = (ListView) view.findViewById(R.id.mainListView);
        mainListView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
                intent  = new Intent(getActivity(), PostActivity.class);

                intent.putExtra(PostActivity.POST, posts.get(position));
                startActivity(intent);
            }
        });
        posts = new ArrayList<Post>();
        apiHelper = new ApiHelper(getActivity().getApplicationContext());
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        mainListView = (ListView) view.findViewById(R.id.mainListView);
        customAdapter = new  CustomAdapter();
        mainListView.setAdapter(customAdapter);

        MainActivity.showProgressBar(true);
        apiHelper.getPosts(getActivity(), new Callback() {
            @Override
            public void methodToCallBack(Object object) {
                MainActivity.showProgressBar(false);
                if(object == null) return;
                ArrayList<Post> tempsPosts = ((ArrayList<Post>) object);
                if (posts == null) return;

                posts = tempsPosts;
                customAdapter.notifyDataSetChanged();
            }
        });

        return view;
    }


    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {

            return posts.size();
        }

        @Override
        public Object getItem(int i) {
            return posts.get(i);
        }

        @Override
        public long getItemId(int i) {
            return posts.get(i).getId();
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.post_row, null);
            TextView title = (TextView) view.findViewById(R.id.txtTitle);
            TextView exerpt = (TextView) view.findViewById(R.id.txtExerpt);
            ImageView image = (ImageView) view.findViewById(R.id.imgPost);


            Post post = posts.get(i);
            title.setText(post.getTitle());
            exerpt.setText((Html.fromHtml(post.getExcerpt())));
            if(post.getImageList()!=null && post.getImageList().size()>0)
                Picasso.get().load(post.getImageList().get(0)).into(image);
            else
                image.setVisibility(View.GONE);
            return view;
        }
    }

}