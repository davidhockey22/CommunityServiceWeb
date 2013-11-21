package tut1.login;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.CommunityService.EntitiesMapped.Event;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import tut1.login.R;

public class EventsFragment extends Fragment {

    private static List<Event> data;
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
        data = new ArrayList<Event>();
        
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("E", Locale.US);
        format.format( date );
        
        org.CommunityService.EntitiesMapped.Event ev;
        
        //the constructor is throwing "invocation target exception".
        /*
        ev = new Event("Soup Kitchen",
        		date, date,
        		0 );
        */
        
        try{
        	ev = new org.CommunityService.EntitiesMapped.Event();
        	
            ev.setEventName("Soup Kitchen");
            ev.setDescription("Seeking kitchen help from those with a warm smile.");
            ev.setBeginTime( date );
            ev.setEndTime( date );
            ev.setLocation("123 Main St, Orlando, FL");
            data.add( ev );        	
        }
        catch (Exception e) {

        	Throwable throwable = e.getCause();
        	e.printStackTrace();
        }        
        
        ev = new Event("Humane Society",
        		date, date,
        		0 );
        ev.setDescription("We need volunteers to pick up dog poop.");
        ev.setLocation("Somewhere, Orlando, FL");
        data.add( ev );

        
        ev = new Event("Nursing Home",
        		date, date,
        		0 );
        ev.setDescription("Help our seniors play bingo.");
        ev.setLocation("1st St., Orlando, FL");
        data.add( ev );
    }    
}