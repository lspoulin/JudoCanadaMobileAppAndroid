package org.judocanada.judocanadamobileappandroid;


import android.content.Context;
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
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class StatsFragment extends Fragment {
    private ListView mainListView;
    private ApiHelper apiHelper;
    private ArrayList<User> users;
    private CustomAdapter customAdapter;

    public StatsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_video, container, false);

        mainListView = (ListView) view.findViewById(R.id.mainListView);
        mainListView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
                intent  = new Intent(getActivity(), VideoActivity.class);

                intent.putExtra(VideoActivity.VIDEO, users.get(position));
                startActivity(intent);
            }
        });
        users = new ArrayList<User>();
        apiHelper = new ApiHelper(getActivity().getApplicationContext());
        mainListView = (ListView) view.findViewById(R.id.mainListView);
        customAdapter = new CustomAdapter();
        mainListView.setAdapter(customAdapter);

        MainActivity.showProgressBar(true);
        apiHelper.getUser(getActivity(), new Callback() {
            @Override
            public void methodToCallBack(Object object) {
                MainActivity.showProgressBar(false);
                if(object == null) return;
                ArrayList<User> tempsUsers = (ArrayList<User>) object;
                if (users == null) return;

                users = tempsUsers;
                customAdapter.notifyDataSetChanged();
            }
        });

        return view;
    }


    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return users.size();
        }

        @Override
        public Object getItem(int i) {
            return users.get(i);
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

            User user = users.get(i);
            title.setText(user.getFirstname() + " " + user.getName());
            exerpt.setText(user.getEmail());
            return view;
        }
    }

}