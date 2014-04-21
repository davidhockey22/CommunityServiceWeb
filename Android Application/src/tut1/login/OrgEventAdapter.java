package tut1.login;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class OrgEventAdapter extends BaseExpandableListAdapter {
	
    private LayoutInflater inflater;

    public OrgEventAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getGroupCount() {
        return EventData.orgEvent.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
    	        
        return 2;
    }

    @Override
    public Object getGroup(int groupPosition) {
    	
        return EventData.orgEvent.get(groupPosition).getEventName();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
    	
        if(childPosition == 0) {
        	
        	return "Approve Volunteers";
        }
        else if(childPosition == 1) {
        	
        	return "Assign Hours";
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
        return true;
    }
}
