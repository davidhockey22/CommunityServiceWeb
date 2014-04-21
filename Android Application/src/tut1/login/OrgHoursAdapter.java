package tut1.login;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class OrgHoursAdapter extends BaseExpandableListAdapter {
	
	public static OrgHoursAdapter current = null;
    private LayoutInflater inflater;
    boolean busy = false;

    public OrgHoursAdapter(Context context) {
    	current = this;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getGroupCount() {
        return VolunteerData.assignHours.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
    	        
        return 3;
    }

    @Override
    public Object getGroup(int groupPosition) {
    	
        return VolunteerData.assignHours.get(groupPosition).getName();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
    	
        if(childPosition == 0) {
        	return VolunteerData.assignHours.get(groupPosition).getFirstName() + " " +
        		VolunteerData.assignHours.get(groupPosition).getLastName();        	
        }
        else if(childPosition == 1) {
        	return VolunteerData.assignHours.get(groupPosition).getBio();
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
    	
        if(childPosition == 2) {
            //if(convertView == null) {
                convertView = inflater.inflate(R.layout.hours_list_item, parent, false);
            //}
                
            final int groupPos = groupPosition;
                
            NumberPicker np = (NumberPicker) convertView.findViewById(R.id.hourPicker);
            np.setMaxValue(40);
            np.setMinValue(0);
            np.setValue(VolunteerData.assignHours.get(groupPosition).getAddHours());
            
            np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                	VolunteerData.assignHours.get(groupPos).setAddHours(newVal);
                }
            });            
            
            //.... ....

            String[] nums = new String[11];
            for(int i=0; i<nums.length; i++)
               nums[i] = Integer.toString(i*10);
            
            np = (NumberPicker) convertView.findViewById(R.id.ratingPicker);
            np.setMaxValue(nums.length - 1);
            np.setMinValue(0);
            //np.setWrapSelectorWheel(false);
            np.setDisplayedValues(nums);
            np.setValue(VolunteerData.assignHours.get(groupPosition).getAddRating());

            np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                	VolunteerData.assignHours.get(groupPos).setAddRating(newVal);
                }
            });            
            
            //.... ....
            
            Button save = (Button)convertView.findViewById(R.id.saveHours);
            save.setOnClickListener(new OnClickListener(){
            	public void onClick(View v){
            		
            		if(busy)
            			return;
            		busy = true;
            		
            		//mysql query add hours, rating, bonus
            		VolunteerData.saveVolunteer = VolunteerData.assignHours.get(groupPos);
            		MySQLRequest.Create( MainActivity.current, Integer.toString(MySQLRequest.kindOrgAddHours), null);
            	}
            });            
                        
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
    
    void onQueryDone(boolean succeeded) {
    	
    	busy = false;
    	Toast.makeText(MainActivity.current,succeeded ? "Succeeded" : "Error", Toast.LENGTH_LONG).show();    	
    }    
}
