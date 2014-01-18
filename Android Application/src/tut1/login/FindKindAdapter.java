package tut1.login;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

//import java.util.List;

//import org.CommunityService.EntitiesMapped.Event;

public class FindKindAdapter extends BaseExpandableListAdapter {
	
    //private List<Event> events;
    private LayoutInflater inflater;

    public FindKindAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getGroupCount() {
        return 2;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
    	
        if(groupPosition == 0) {
        	
        	return 0;
        }
        else if(groupPosition == 1) {
        	
        	int size = InterestData.GetListSize();
        	return size;
        }
        
        return 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        //return events.get(groupPosition).getEventName();
        if(groupPosition == 0) {
        	
        	return "Find by distance";
        }
        else if(groupPosition == 1) {
        	
        	return "Find by interest";
        }
        
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
    	
        if(groupPosition == 1) {
        	
        	return InterestData.GetListLocation(childPosition).getName();
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
            convertView = inflater.inflate(R.layout.expandable_list_item, parent, false);
        }

        ((TextView)convertView).setText(getChild(groupPosition,childPosition).toString());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int i, int i2) {
        return true;
    }
}
