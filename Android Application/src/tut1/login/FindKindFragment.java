package tut1.login;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import tut1.login.R;

public class FindKindFragment extends Fragment {

    private ExpandableListView expandableListView;
    private FindKindAdapter adapter;
    
    public FindKindFragment() {
        // Empty constructor required for fragment subclasses
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
        View rootView = inflater.inflate(R.layout.fragment_findkind, container, false);
        //int i = getArguments().getInt(ARG_PLANET_NUMBER);
        //String planet = getResources().getStringArray(R.array.planets_array)[i];

        /*
        int imageId = getResources().getIdentifier(planet.toLowerCase(Locale.getDefault()),
                        "drawable", getActivity().getPackageName());
         */
        
    	expandableListView = (ExpandableListView) rootView.findViewById(R.id.fragmentFindKind);
    	adapter = new FindKindAdapter(getActivity());
    	expandableListView.setAdapter(adapter);
    	
    	expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
			
			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {
				
				if(groupPosition == 0) { //if "Find by distance" clicked on
					
					EventAdapter.findByKind = EventAdapter.kindFindByDistance;
					
					//replace fragment
					Fragment fragment = new FindFragment();
					
			        FragmentManager fragmentManager = getFragmentManager();
			        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
					
					return true;
				}
				else if(groupPosition == 2) { //if "Find by date" clicked on
					
					EventAdapter.findByKind = EventAdapter.kindFindByDate;
					
					//replace fragment
					Fragment fragment = new FindFragment();
					
			        FragmentManager fragmentManager = getFragmentManager();
			        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
					
					return true;
				}				
				
				return false;
			}
        });  

    	expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
            	
            	//save find by data
            	EventAdapter.findByKind = EventAdapter.kindFindByInterest;
            	EventAdapter.findByInterestID = InterestData.GetListLocation(childPosition).getInterestID();
            	
				//replace fragment
				Fragment fragment = new FindFragment();
				
		        FragmentManager fragmentManager = getFragmentManager();
		        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

                return false;
            }
        });    	
        
        return rootView;
    }
}