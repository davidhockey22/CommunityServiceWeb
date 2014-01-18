package tut1.login;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import tut1.login.R;

public class FindFragment extends Fragment {

    private ExpandableListView expandableListView;
    private EventAdapter adapter;
    
    public FindFragment() {
        // Empty constructor required for fragment subclasses
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
        //int i = getArguments().getInt(ARG_PLANET_NUMBER);
        //String planet = getResources().getStringArray(R.array.planets_array)[i];

        /*
        int imageId = getResources().getIdentifier(planet.toLowerCase(Locale.getDefault()),
                        "drawable", getActivity().getPackageName());
         */
        
        EventData.SetFindList(EventAdapter.findByKind);
        
        View rootView = null;
        if(EventData.GetFindListSize() == 0) {
        	
            rootView = inflater.inflate(R.layout.fragment_status, container, false);
            
            if(EventVolunteerData.status == EventVolunteerData.statusLoaded) {
            	
            	final TextView view =(TextView)rootView.findViewById(R.id.statusText);
            	view.setText("No events found");
            }
        }
        else {        
        
            rootView = inflater.inflate(R.layout.fragment_find, container, false);
        	
        	expandableListView = (ExpandableListView) rootView.findViewById(R.id.fragmentFind);
        	adapter = new EventAdapter(getActivity(), EventAdapter.findByKind);
        	expandableListView.setAdapter(adapter);   	
        }

        return rootView;
    }
}