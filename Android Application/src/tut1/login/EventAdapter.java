package tut1.login;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

//import java.util.List;

//import org.CommunityService.EntitiesMapped.Event;

public class EventAdapter extends BaseExpandableListAdapter {
	
    //private List<Event> events;
    private LayoutInflater inflater;
    private int kind;
    
    public static final int kindEvent = 1, kindFindByDistance = 2, kindFindByInterest = 3, kindFindByDate = 4;
	public static String findByInterestID = null;
    public static int findByKind = 2;

    public EventAdapter(Context context, int Kind) {
        inflater = LayoutInflater.from(context);
        kind = Kind;
    }

    @Override
    public int getGroupCount() {
    	
    	if(kind == kindEvent)
    		return EventData.GetEventsSignedUpForSize();
    	else
    		return EventData.GetFindListSize();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return EventData.childDataTotal;
    }

    @Override
    public Object getGroup(int groupPosition) {
    	
    	if(kind == kindEvent)
    		return EventData.GetEventsSignedUpForLocation(groupPosition).getEventName();
    	else
    		return EventData.GetFindListLocation(groupPosition).getEventName();    		
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
    	
    	if(kind == kindEvent) {
    		
        	if( childPosition == 0 )
        		return EventData.GetEventsSignedUpForLocation(groupPosition).getBeginTime();
        	else if( childPosition == 1 )
        		return EventData.GetEventsSignedUpForLocation(groupPosition).getLocation();
        	else if( childPosition == 2 )
        		return EventData.GetEventsSignedUpForLocation(groupPosition).getDescription();
    	}
    	else {
    		
        	if( childPosition == 0 )
        		return EventData.GetFindListLocation(groupPosition).getBeginTime();
        	else if( childPosition == 1 )
        		return EventData.GetFindListLocation(groupPosition).getLocation();
        	else if( childPosition == 2 )
        		return EventData.GetFindListLocation(groupPosition).getDescription();
    	}
    	
    	return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(android.R.layout.simple_expandable_list_item_1, parent, false);
        }

        ((TextView) convertView).setText(getGroup(groupPosition).toString());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
        }

        ((TextView)convertView).setText(getChild(groupPosition,childPosition).toString());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int i, int i2) {
        return false;
    }
}
