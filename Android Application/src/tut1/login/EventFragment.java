package tut1.login;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import tut1.login.R;

public class EventFragment extends Fragment {

    private ExpandableListView expandableListView;
    private EventAdapter adapter;  
	
    public EventFragment() {
        // Empty constructor required for fragment subclasses
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        
        container.removeAllViewsInLayout();
        
        View rootView = null;
        if(EventData.GetSignedUpForSize() == 0) {
        	
            rootView = inflater.inflate(R.layout.fragment_status, container, false);
            
            if(EventData.status == EventData.statusLoaded) {
            	
            	final TextView view =(TextView)rootView.findViewById(R.id.statusText);
            	view.setText("You're not participating in any events");
            }
        }
        else {
        	rootView = inflater.inflate(R.layout.fragment_events, container, false);
        	
        	EventData.SetFindList(EventAdapter.kindSignedUp);

        	expandableListView = (ExpandableListView) rootView.findViewById(R.id.fragmentEvents);
        	adapter = new EventAdapter(getActivity(), EventAdapter.kindSignedUp);
        	expandableListView.setAdapter(adapter);
        }
        
        //int i = getArguments().getInt(ARG_PLANET_NUMBER);
        //String planet = getResources().getStringArray(R.array.planets_array)[i];

        /*
        int imageId = getResources().getIdentifier(planet.toLowerCase(Locale.getDefault()),
                        "drawable", getActivity().getPackageName());
         */
        
        return rootView;
    }
}