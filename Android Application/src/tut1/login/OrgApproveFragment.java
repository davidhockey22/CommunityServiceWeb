package tut1.login;

import java.util.ArrayList;

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
import android.widget.Toast;

import tut1.login.R;

public class OrgApproveFragment extends Fragment {

	public static OrgApproveFragment current = null;
    private ExpandableListView expandableListView;
    private OrgApproveAdapter adapter;
    
    public static final int statusQuery = 1;
    public static int status = 0;    
    
    LayoutInflater inflater = null;
    View rootView = null;
    ViewGroup container = null;
    String eventId = null;
    boolean busy = false;
    boolean queriesSucceeded = true;
    int queryCount = 0;
    
    public OrgApproveFragment() {
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
    		rootView = inflater.inflate(R.layout.fragment_org_approve, container, false);
    	}
        
        if(status == 0) {
    		status = statusQuery;
    		
    		//query
    		VolunteerData.needsOrgApproval = new ArrayList<VolunteerData>(); 
    		
    		TextView mess = (TextView)rootView.findViewById(R.id.messageOrgApprove);
    		Button save = (Button)rootView.findViewById(R.id.saveButtonApprove);

    		mess.setText("Loading");
    		mess.setVisibility(View.VISIBLE);
    		save.setVisibility(View.GONE);
    		
    		eventId = getArguments().getString("eventId", "");
    		MySQLRequest.Create( MainActivity.current, Integer.toString(MySQLRequest.kindVolunteersByEvent), eventId);    		
    		
    		//test
//    		VolunteerData o = new VolunteerData();
//    		o.initNonUser("10", "Tom", "386-631-1085", "email@mail.org", "I like to volunteer", "harry", "jones", "10", "11", "12");
//    		VolunteerData.needsOrgApproval.add(o);
//    		o = new VolunteerData();
//    		o.initNonUser("11", "Sally", "386-631-1085", "email@mail.org", "I like to volunteer", "sally", "castle", "4", "5", "6");
//    		VolunteerData.needsOrgApproval.add(o);
//    		onQueryDone(0, true);    		
    		
    		return;
		}
        else if(status == statusQuery) {
        	status = 0;
        	
    		TextView mess = (TextView)rootView.findViewById(R.id.messageOrgApprove);
    		Button save = (Button)rootView.findViewById(R.id.saveButtonApprove);
            if(VolunteerData.needsOrgApproval.size() == 0) {
            	
        		mess.setText("No volunteers to approve");
        		mess.setVisibility(View.VISIBLE);
        		save.setVisibility(View.GONE);
            	return;
            }
            mess.setVisibility(View.GONE);
            save.setVisibility(View.VISIBLE);
            
            //approve all by default
            for(VolunteerData vol : VolunteerData.needsOrgApproval)
            	vol.setApproved(true);
            
            save.setOnClickListener(new OnClickListener(){
            	public void onClick(View v){
            		
            		if(busy) return;
            		busy = true;
            		
            		queryCount = 0;
            		queriesSucceeded = true;
            		
            		//mysql query approve EventVolunteer
            		MySQLRequest.Create( MainActivity.current, Integer.toString(MySQLRequest.kindOrgApprove), eventId);    		
            		
            		//mysql query remove EventVolunteer
            		MySQLRequest.Create( MainActivity.current, Integer.toString(MySQLRequest.kindOrgRemove), eventId);            		
            	}
            });            
            
        	expandableListView = (ExpandableListView) rootView.findViewById(R.id.fragmentOrgApprove);
        	adapter = new OrgApproveAdapter(getActivity());
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
 
                    return false;
                }
            });    	
            
            return;            
        }    	
    }
    
    void onQueryDone(int queryKind, boolean succeeded) {
    	
    	if(queryKind == MySQLRequest.kindVolunteersByEvent) {
    		
    		if(VolunteerData.needsOrgApproval.size() == 0) {
    			onQueryDone(MySQLRequest.kindEventVolunteerOrg, true);
    			return;
    		}
    		
    		queryCount = 0;
    		for(VolunteerData v : VolunteerData.needsOrgApproval) {
    			MySQLRequest.Create( MainActivity.current, Integer.toString(MySQLRequest.kindEventVolunteerOrg), v.getVolunteerID());
    		}
    	}
    	else if(queryKind == MySQLRequest.kindEventVolunteerOrg) {
    	
    		//all queries done
    		queryCount++;
    		if(queryCount >= VolunteerData.needsOrgApproval.size()) {
    			queryCount = 0;
    			
    	    	refresh();
    	    	return;
    		}
    	}
    	else if(queryKind == MySQLRequest.kindOrgApprove ||
    			queryKind == MySQLRequest.kindOrgRemove ) {
    		
    		if(succeeded == false)
    			queriesSucceeded = false;
    		queryCount++;
    		if(queryCount > 1) {
    			queryCount = 0;
        		busy = false;    			
    			Toast.makeText(getActivity(),queriesSucceeded ? "Succeeded" : "Error", Toast.LENGTH_LONG).show();
    		}    		
    	} 	
    }    
}