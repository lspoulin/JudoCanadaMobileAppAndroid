package org.judocanada.judocanadamobileappandroid;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import org.judocanada.judocanadamobileappandroid.Api.ApiHelper;
import org.judocanada.judocanadamobileappandroid.Api.Callback;
import org.judocanada.judocanadamobileappandroid.Model.Event;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class EventFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    private ListView mainListView;
    private EventList eventList;
    private EventFragment.CustomAdapter customAdapter;
    private ApiHelper apiHelper;
    private Spinner spinnerMonth, spinnerYear;
    private ArrayAdapter<String> adapterMonth;
    private ArrayAdapter<Integer> adapterYear;

    public EventFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_event, container, false);

        mainListView = (ListView) view.findViewById(R.id.mainListView);
        mainListView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Uri webpage = Uri.parse(eventList.getDisplay().get(position).getUrl());
                Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                startActivity(intent);
            }
        });

        apiHelper = new ApiHelper(getActivity().getApplicationContext());
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
                eventList.setMain(tempsEvents);
                int index = eventList.years.indexOf(Calendar.getInstance().get(Calendar.YEAR));
                if(index!=-1)
                    spinnerYear.setSelection(index);

            }
        });

        spinnerMonth = (Spinner)view.findViewById(R.id.spinnerMonth);
        spinnerYear = (Spinner)view.findViewById(R.id.spinnerYear);

        adapterMonth= new ArrayAdapter<String>(view.getContext(),
                android.R.layout.simple_spinner_item, getMonthlist());

        adapterMonth.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        adapterYear= new ArrayAdapter<Integer>(view.getContext(),
                android.R.layout.simple_spinner_item,new ArrayList<Integer>(Arrays.asList(new Integer[]{Calendar.getInstance().get(Calendar.YEAR)})));

        adapterYear.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerMonth.setAdapter(adapterMonth);
        spinnerMonth.setOnItemSelectedListener(this);

        spinnerYear.setAdapter(adapterYear);
        spinnerYear.setOnItemSelectedListener(this);

        spinnerMonth.setSelection(Calendar.getInstance().get(Calendar.MONTH));
        spinnerYear.setSelection(0);
        eventList = new EventList();

        return view;
    }

    private ArrayList<String> getMonthlist() {
        Calendar c = Calendar.getInstance(Locale.getDefault());
        Map<String, Integer> map = c.getDisplayNames(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
        ArrayList<String> monthlist = new ArrayList<String>();
        Map<Integer, String> reversemap = new HashMap<Integer, String>();

        for(Map.Entry<String, Integer> pair : map.entrySet())
        {
            reversemap.put(pair.getValue(), pair.getKey());
        }
        for (int i = 0 ; i < reversemap.size() ; i ++){
            monthlist.add(reversemap.get(i));
        }
        return monthlist;
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (spinnerYear.getSelectedItem()!=null) {
            int year = Integer.parseInt(spinnerYear.getSelectedItem().toString());
            int month = (int) spinnerMonth.getSelectedItemId();
            eventList.dateChanged(year, month);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if(eventList!= null)
            return eventList.getDisplay().size();
            else
                return 0;
        }

        @Override
        public Object getItem(int i) {
            return eventList.getDisplay().get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.event_row, null);
            TextView title = (TextView) view.findViewById(R.id.txtTitle);
            TextView exerpt = (TextView) view.findViewById(R.id.txtExerpt);

            Event post = eventList.getDisplay().get(i);
            title.setText(post.getSummary());
            String dateRange = "";
            Calendar c = Calendar.getInstance();
            c.setTime( post.getDateStart());
            dateRange = c.get(Calendar.DAY_OF_MONTH) + " " + c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
            if (!post.getDateStart().equals(post.getDateEnd())) {
                c.setTime(post.getDateEnd());
                dateRange += " - " + c.get(Calendar.DAY_OF_MONTH) + " " + c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
            }
            exerpt.setText(dateRange);
            return view;
        }
    }

    private class EventList{
        private ArrayList<Event> display;
        private ArrayList<Integer> years;


        public ArrayList<Integer> getYears() {
            return years;
        }

        public void setYears(ArrayList<Integer> years) {
            this.years = years;
            adapterYear.clear();
            adapterYear.addAll(years);
            adapterYear.notifyDataSetChanged();
        }

        public ArrayList<Event> getDisplay() {
            return display;
        }

        public void setDisplay(ArrayList<Event> display) {
            this.display = display;
            customAdapter.notifyDataSetChanged();
        }

        public ArrayList<Event> getMain() {
            return main;
        }

        public void setMain(ArrayList<Event> main) {
            this.main = main;
            Calendar c = Calendar.getInstance();
            setDisplay(currentSelection(main, c.get(Calendar.YEAR), c.get(Calendar.MONTH)));
        }

        private ArrayList<Event> main;
        public EventList(){
            setMain(new ArrayList<Event>());
            setYears(new ArrayList<Integer>());
        }

        private ArrayList<Event> currentSelection(ArrayList<Event> events, int year, int month) {
            Calendar c = Calendar.getInstance();
            c.set(year, month, 1);
            Date d = c.getTime();
            c.add(Calendar.MONTH, 1);
            Date d2 = c.getTime();
            ArrayList<Event> filteredList = new ArrayList<Event>();
            ArrayList<Integer> newYear = new ArrayList<Integer>();
            for (Event e : events){
                c.setTime(e.getDateStart());
                if (newYear.indexOf(c.get(Calendar.YEAR))==-1)
                    newYear.add(c.get(Calendar.YEAR));

                if (e.getDateStart().after(d) && e.getDateStart().before(d2)){
                    filteredList.add(e);
                }
            }

            Collections.sort(filteredList);
            Collections.sort(newYear);
            setYears(newYear);
            return filteredList;
        }

        public void dateChanged(int selectedItem, long selectedItemId) {
            setDisplay(currentSelection(main, selectedItem,(int)selectedItemId));
        }
    }
}
