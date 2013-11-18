package tut1.login;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import tut1.login.R;

public class EventsFragment extends Fragment {

    private static List<EventData> data;
    private ExpandableListView expandableListView;
    private EventAdapter adapter;
	
    public EventsFragment() {
        // Empty constructor required for fragment subclasses
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_events, container, false);
        
        LoadData();
        expandableListView = (ExpandableListView) rootView.findViewById(R.id.fragmentEvents);
        adapter = new EventAdapter(getActivity(), data);
        expandableListView.setAdapter(adapter);
    	
        //int i = getArguments().getInt(ARG_PLANET_NUMBER);
        //String planet = getResources().getStringArray(R.array.planets_array)[i];

        /*
        int imageId = getResources().getIdentifier(planet.toLowerCase(Locale.getDefault()),
                        "drawable", getActivity().getPackageName());
         */
        
        return rootView;
    }
    
    private void LoadData() {
        data = new ArrayList<EventData>();
        
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("E", Locale.US);
        format.format( date );
        
        data.add( new EventData("Soup Kitchen",
        		"Seeking kitchen help from those with a warm smile.",
        		"123 Main St, Orlando, FL",
        		date,
        		date ) );
        
        data.add( new EventData("Humane Society",
        		"We need volunteers to pick up dog poop.",
        		"Somewhere, Orlando, FL",
        		date,
        		date ) );
        
        data.add( new EventData("Nursing Home",
        		"Help our seniors play bingo.",
        		"1st St., Orlando, FL",
        		date,
        		date ) );
    }    
}