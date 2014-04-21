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

public class OrgFragment extends Fragment {

    private ExpandableListView expandableListView;
    
    public static OrgFragment current = null;
    public static final int statusQuery = 1;
    public static int status = 0;    
    
    LayoutInflater inflater = null;
    View rootView = null;
    ViewGroup container = null;
    
    public OrgFragment() {
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
	        rootView = inflater.inflate(R.layout.fragment_org, container, false);
    	}
    	
        if(status == 0) {
    		status = statusQuery;
    		
    		//query
    		OrgData.all = new ArrayList<OrgData>();
    		
    		TextView mess = (TextView)rootView.findViewById(R.id.messageOrg);
    		mess.setText("Loading");
    		mess.setVisibility(View.VISIBLE);
    		
    		MySQLRequest.Create( MainActivity.current, Integer.toString(MySQLRequest.kindOrg), null);
    		
    		//test
//    		OrgData o = new OrgData();
//    		o.init("10", "Test Org0");
//    		o = new OrgData();
//    		o.init("11", "Test Org1");
//    		onQueryDone();
    		
    		return;
		}
        else if(status == statusQuery) {
        	status = 0;
        	
        	TextView mess = (TextView)rootView.findViewById(R.id.messageOrg);
            if(OrgData.all.size() == 0) {
            	
        		mess.setText("Not authorized in any organizations");
        		mess.setVisibility(View.VISIBLE);
            	return;
            }
            else {
            	mess.setVisibility(View.GONE);
            }
         
        	expandableListView = (ExpandableListView) rootView.findViewById(R.id.fragmentOrg);
        	OrgAdapter adapter = new OrgAdapter(getActivity());
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
 
                	Bundle args = new Bundle();
                	args.putString("orgId", OrgData.all.get(groupPosition).getOrgID());                	
                	
					//replace fragment
					Fragment fragment = new OrgEventFragment();
					fragment.setArguments(args);
					
			        FragmentManager fragmentManager = getFragmentManager();
			        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
                	
                    return true;
                }
            });    	
            
            return;            
        }    	
    }
    
    void onQueryDone() {
    	
    	refresh();
    }
}