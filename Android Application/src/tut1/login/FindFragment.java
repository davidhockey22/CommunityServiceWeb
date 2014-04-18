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
	
	public static FindFragment current = null;

    private ExpandableListView expandableListView;
    private EventAdapter adapter;
    private LayoutInflater inflater;
    private View rootView = null;
    private ViewGroup container;
    
    public static final int statusQuery = 1;
    public static int status = 0;
    
    public FindFragment() {
        // Empty constructor required for fragment subclasses
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
    	current = this;
    	this.container = container;
    	this.inflater = inflater;
        
    	return refresh();
    }
    
    View refresh() {
    	
        EventData.SetFindList(EventAdapter.findByKind);
        
        if(EventData.GetFindListSize() == 0) {
        	
        	if(rootView == null)
        		rootView = inflater.inflate(R.layout.fragment_status, container, false);
            
            if(status == statusQuery) {      	
            	status = 0;
            	
            	final TextView view =(TextView)rootView.findViewById(R.id.statusText);
            	view.setText("Loading");            	
            }
            else if(EventData.status == EventData.statusLoaded) {
            	
            	final TextView view =(TextView)rootView.findViewById(R.id.statusText);
            	view.setText("No events found");
            }
        }
        else {
        	
        	container.removeAllViewsInLayout();

            rootView = inflater.inflate(R.layout.fragment_find, container);

        	expandableListView = (ExpandableListView) rootView.findViewById(R.id.fragmentFind);
        	adapter = new EventAdapter(getActivity(), EventAdapter.findByKind);
        	expandableListView.setAdapter(adapter);   	
        }

        return rootView;    	
    }
}