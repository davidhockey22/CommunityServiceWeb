package tut1.login;

import java.util.ArrayList;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import tut1.login.R;

public class OrgEventFragment extends Fragment {

    private ExpandableListView expandableListView;
    private OrgEventAdapter adapter;
    
    public static OrgEventFragment current = null;
    public static final int statusQuery = 1;
    public static int status = 0;    
    
    LayoutInflater inflater = null;
    View rootView = null;
    ViewGroup container = null;
    
    public OrgEventFragment() {
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
    		rootView = inflater.inflate(R.layout.fragment_org_event, container, false);
    	}
    	
        if(status == 0) {
    		status = statusQuery;
    		
    		//query
    		EventData.orgEvent = new ArrayList<EventData>(); 
    		
    		TextView mess = (TextView)rootView.findViewById(R.id.messageOrgEvent);
    		mess.setText("Loading");
    		mess.setVisibility(View.VISIBLE);
    		
    		String orgId = getArguments().getString("orgId", "");
    		MySQLRequest.Create( MainActivity.current, Integer.toString(MySQLRequest.kindOrgEvents), orgId);    		
    		
    		//test
//    		EventData o = new EventData();
//    		o.init("10", "Name0", "Desc", "Loc", "BeginTime", "EndTime");
//    		EventData.orgEvent.add(o);
//    		o = new EventData();
//    		o.init("11", "Name1", "Desc", "Loc", "BeginTime", "EndTime");
//    		EventData.orgEvent.add(o);
//    		onQueryDone();    		
    		
    		return;
		}
        else if(status == statusQuery) {
        	status = 0;
        	
        	TextView mess = (TextView)rootView.findViewById(R.id.messageOrgEvent);
            if(EventData.orgEvent.size() == 0) {
            	
        		mess.setText("No events hosted by this organization");
        		mess.setVisibility(View.VISIBLE);
            	return;
            }
            else {
            	mess.setVisibility(View.GONE);
            }
         
        	expandableListView = (ExpandableListView) rootView.findViewById(R.id.fragmentOrgEvent);
        	adapter = new OrgEventAdapter(getActivity());
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
 
                	if(childPosition == 0) {
                	
                    	Bundle args = new Bundle();
                    	args.putString("eventId", EventData.orgEvent.get(groupPosition).getEventID());                	
                		
						//replace fragment
						Fragment fragment = new OrgApproveFragment();
						fragment.setArguments(args);
						
				        FragmentManager fragmentManager = getFragmentManager();
				        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
				        
				        return true;
                	}
                	else if(childPosition == 1) {
                    	
                    	Bundle args = new Bundle();
                    	args.putString("eventId", EventData.orgEvent.get(groupPosition).getEventID());                	
                		
						//replace fragment
						Fragment fragment = new OrgHoursFragment();
						fragment.setArguments(args);
						
				        FragmentManager fragmentManager = getFragmentManager();
				        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
				        
				        return true;
                	}
                	
                    return false;
                }
            });    	
            
            return;            
        }    	
    }
    
    void onQueryDone() {
    	
    	refresh();
    }    
}