package org.judocanada.judocanadamobileappandroid;

import android.content.Intent;
import android.net.Uri;
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

import java.util.ArrayList;
import java.util.Collections;

public class EventFragment extends Fragment {
    private ListView mainListView;
    private ArrayList<Event> events;
    private EventFragment.CustomAdapter customAdapter;
    private ApiHelper apiHelper;
    private ProgressBar progressBar;

    public EventFragment() {
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
                Uri webpage = Uri.parse(events.get(position).getUrl());
                Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                startActivity(intent);
            }
        });
        events = new ArrayList<Event>();
        apiHelper = new ApiHelper(getActivity().getApplicationContext());
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        mainListView = (ListView) view.findViewById(R.id.mainListView);
        customAdapter = new EventFragment.CustomAdapter();
        mainListView.setAdapter(customAdapter);

        MainActivity.showProgressBar(true);
        apiHelper.getEvents(getActivity(), new Callback() {
            @Override
            public void methodToCallBack(Object object) {
                MainActivity.showProgressBar(false);
                if(object == null) return;
                ArrayList<Event> tempsEvents = ((ArrayList<Event>) object);
                if (tempsEvents == null) return;
                Collections.sort(tempsEvents);
                events = tempsEvents;
                customAdapter.notifyDataSetChanged();
            }
        });

        return view;
    }


    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {

            return events.size();
        }

        @Override
        public Object getItem(int i) {
            return events.get(i);
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
            ImageView image = (ImageView) view.findViewById(R.id.imgPost);


            Event post = events.get(i);
            title.setText(post.getSummary());
            exerpt.setText(post.getDateStart().toString());
            //exerpt.setText((Html.fromHtml(post.getExcerpt())));
            //if(post.getImageList()!=null && post.getImageList().size()>0)
            //    Picasso.get().load(post.getImageList().get(0)).into(image);
            //else
            //    image.setVisibility(View.GONE);
            return view;
        }
    }

}
