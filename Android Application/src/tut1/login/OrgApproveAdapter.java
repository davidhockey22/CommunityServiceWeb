package tut1.login;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

public class OrgApproveAdapter extends BaseExpandableListAdapter {
	
    private LayoutInflater inflater;

    public OrgApproveAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getGroupCount() {
        return VolunteerData.needsOrgApproval.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
    	        
        return 6;
    }

    @Override
    public Object getGroup(int groupPosition) {
    	
        return VolunteerData.needsOrgApproval.get(groupPosition).getName();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
    	
        if(childPosition == 0) {
        	return VolunteerData.needsOrgApproval.get(groupPosition).getFirstName() + " " +
        		VolunteerData.needsOrgApproval.get(groupPosition).getLastName();        	
        }
        else if(childPosition == 1) {
        	return VolunteerData.needsOrgApproval.get(groupPosition).getBio();
        }
        else if(childPosition == 2) {
        	return "Hours Worked: " + VolunteerData.needsOrgApproval.get(groupPosition).getHoursWorked();
        }
        else if(childPosition == 3) {
        	return "Points: " + VolunteerData.needsOrgApproval.get(groupPosition).getPoints();
        }
        else if(childPosition == 4) {
        	return "Rating: " + VolunteerData.needsOrgApproval.get(groupPosition).getRating();
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
    	
        if(childPosition == 5) {
            //if(convertView == null) {
                convertView = inflater.inflate(R.layout.togglebutton_list_item, parent, false);
                ToggleButton but = (ToggleButton)convertView.findViewById(R.id.togglebutton);
                but.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    public void onCheckedChanged(CompoundButton v, boolean isChecked) {
                    	
                    	ToggleButton but = (ToggleButton)v;
                    	int groupPos = (Integer)but.getTag();
                        if (isChecked) {
                            // The toggle is enabled
                        	VolunteerData.needsOrgApproval.get(groupPos).setApproved(true);
                        } else {
                            // The toggle is disabled
                        	VolunteerData.needsOrgApproval.get(groupPos).setApproved(false);
                        }
                    }
                });
            //}
            
            //ToggleButton but = (ToggleButton)convertView.findViewById(R.id.togglebutton);
            but.setTag(groupPosition);
            if( VolunteerData.needsOrgApproval.get(groupPosition).isApproved() )
            	but.setChecked(true);
            else
            	but.setChecked(false);
            
            return convertView;
        }
    	
        //if(convertView == null) {
            convertView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
        //}

        ((TextView)convertView).setText(getChild(groupPosition,childPosition).toString());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int i, int i2) {
        return true;
    }
}
