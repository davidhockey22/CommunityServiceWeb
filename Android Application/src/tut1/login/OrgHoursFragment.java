package tut1.login;

import java.util.ArrayList;
import java.util.Iterator;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;

import tut1.login.R;

public class OrgHoursFragment extends Fragment {

	public static OrgHoursFragment current = null;
    private ExpandableListView expandableListView;
    private OrgHoursAdapter adapter;
    
    public static final int statusQuery = 1;
    public static int status = 0;    
    
    LayoutInflater inflater = null;
    View rootView = null;
    ViewGroup container = null;
    String eventId = null;
    int queryCount = 0;
    
    public OrgHoursFragment() {
        // Empty constructor required for fragment subclasses
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
    	current = this;
    	this.inflater = inflater;
    	this.container = container;
    	
    	refresh();
    	
    	return rootView;
    }
    
    void refresh() {
    	
    	if(rootView == null) {
    		container.removeAllViewsInLayout();
    		rootView = inflater.inflate(R.layout.fragment_org_hours, container, false);
    	}
        
        if(status == 0) {
    		status = statusQuery;
    		
    		//query
    		VolunteerData.assignHours = new ArrayList<VolunteerData>(); 
    		
    		TextView mess = (TextView)rootView.findViewById(R.id.messageOrgHours);

    		mess.setText("Loading");
    		mess.setVisibility(View.VISIBLE);
    		
    		eventId = getArguments().getString("eventId", "");
    		MySQLRequest.Create( MainActivity.current, Integer.toString(MySQLRequest.kindApprovedVolunteersByEvent), eventId);    		    		
    		
    		//test
//    		VolunteerData o = new VolunteerData();
//    		o.initNonUser("10", "Jimmy", "386-631-1085", "email@mail.org", "I like to volunteer", "James", "jones", "10", "11", "12");
//    		VolunteerData.assignHours.add(o);
//    		o = new VolunteerData();
//    		o.initNonUser("11", "Dereck", "386-631-1085", "email@mail.org", "I like to volunteer", "Dereck", "castle", "4", "5", "6");
//    		VolunteerData.assignHours.add(o);
//    		onQueryDone(true);    		
    		
    		return;
		}
        else if(status == statusQuery) {
        	status = 0;
        	
    		TextView mess = (TextView)rootView.findViewById(R.id.messageOrgHours);
            if(VolunteerData.assignHours.size() == 0) {
            	
        		mess.setText("No volunteers");
        		mess.setVisibility(View.VISIBLE);
            	return;
            }
            mess.setVisibility(View.GONE);
            
            //set defaults
            for(VolunteerData dat : VolunteerData.assignHours) {
            	dat.setAddHours(4);
            	dat.setAddRating(10);
            }
            
        	expandableListView = (ExpandableListView) rootView.findViewById(R.id.fragmentOrgHours);
        	adapter = new OrgHoursAdapter(getActivity());
        	expandableListView.setAdapter(adapter);
        	
        	expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {	
    			@Override
    			public boolean onGroupClick(ExpandableListView parent, View v,
    					int groupPosition, long id) {
    				
    				return false;
    			}
            });  

        	expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
 
					//replace fragment
//					Fragment fragment = new FindFragment();
//					
//			        FragmentManager fragmentManager = getFragmentManager();
//			        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
                	
                    return false;
                }
            });    	
            
            return;            
        }    	
    }
    
    void onQueryDone(int queryKind, boolean succeeded) {
    	
    	if(queryKind == MySQLRequest.kindApprovedVolunteersByEvent) {
    		
    		if(VolunteerData.assignHours.size() == 0) {
    			onQueryDone(MySQLRequest.kindApprovedEventVolunteerOrg, true);
    			return;
    		}
    		
    		queryCount = 0;
    		for(VolunteerData v : VolunteerData.assignHours) {
    			MySQLRequest.Create( MainActivity.current, Integer.toString(MySQLRequest.kindApprovedEventVolunteerOrg), v.getVolunteerID());
    		}
    	}
    	else if(queryKind == MySQLRequest.kindApprovedEventVolunteerOrg) {
    	
    		//all queries done
    		queryCount++;
    		if(queryCount >= VolunteerData.assignHours.size()) {
    			queryCount = 0;
    			
    			//only assign hours to approved volunteers
    			for(Iterator<VolunteerData> i = VolunteerData.assignHours.iterator(); i.hasNext();) {
    				VolunteerData v = (VolunteerData) i.next();
    				if(v.isApproved() == false) {
    					i.remove();
    				}
    			}
    			
    	    	refresh();
    	    	return;
    		}
    	}
    }
}