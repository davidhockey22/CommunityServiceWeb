package tut1.login;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;

//import java.util.List;

//import org.CommunityService.EntitiesMapped.Event;

public class EventAdapter extends BaseExpandableListAdapter {
	
    //private List<Event> events;
    private LayoutInflater inflater;
    private int kind;
    
    public static EventAdapter current = null;
    public static final int kindSignedUp = 1, kindFindByDistance = 2, kindFindByInterest = 3, kindFindByDate = 4, kindFindBySkill = 5;
	public static String findByInterestID = null, findBySkillID = null;
    public static int findByKind = 2;
    
    boolean busy = false;
    Button busyButton = null;
    TextView busyTextView = null;
    int busyGroupPos = 0;

    public EventAdapter(Context context, int Kind) {
    	current = this;
        inflater = LayoutInflater.from(context);
        kind = Kind;
    }

    @Override
    public int getGroupCount() {
    	
    	return EventData.GetFindListSize();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return EventData.childDataTotal + 1; //+ button
    }

    @Override
    public Object getGroup(int groupPosition) {
    	
   		return EventData.GetFindListLocation(groupPosition).getEventName();    		
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
    		
    	if( childPosition == 0 )
    		return EventData.GetFindListLocation(groupPosition).getBeginTime();
    	else if( childPosition == 1 )
    		return EventData.GetFindListLocation(groupPosition).getLocation();
    	else if( childPosition == 2 )
    		return EventData.GetFindListLocation(groupPosition).getDescription();
    	
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
    	
    	if(childPosition == 3) {
	        //if(convertView == null) {
	            convertView = inflater.inflate(R.layout.button_list_item, parent, false);
	            
	            Button button = (Button)convertView.findViewById(R.id.signUp);
	            TextView status = (TextView)convertView.findViewById(R.id.status);
	            
	            setStatus(button, status, groupPosition);
	            
	            //sign up or remove event listener
	            final int grpPos = groupPosition;
	            final View root = convertView;
	            button.setOnClickListener(new OnClickListener(){
	            	public void onClick(View v){
	            			            		
	            		if( busy )
	            			return;
	            		busy = true;
	            		
	            		//save data so when query done, status is updated
	            		busyButton = (Button)v;
	            		busyTextView = (TextView)root.findViewById(R.id.status);
	            		busyGroupPos = grpPos;

	                    EventData dat = EventData.GetEventInSignedUpForList( EventData.GetFindListLocation(grpPos).getEventID());	            	
	            		
	            		if(dat == null) {	
	            			
	                    	dat = EventData.GetEventInFindList( EventData.GetFindListLocation(grpPos).getEventID());
	                    	if(dat == null)
	                    		Obj.BreakPoint();
	                    	
	            			MySQLRequest.Create( MainActivity.current, Integer.toString(MySQLRequest.kindSignUpForEvent), dat.getEventID());	            			
	            		}
	            		else {
	            			MySQLRequest.Create( MainActivity.current, Integer.toString(MySQLRequest.kindEventRemove), dat.getEventID());	            			
	            		}
	            	}
	            });
	        //}
	        //((TextView)convertView).setText(getChild(groupPosition,childPosition).toString());
	        return convertView;    		
    	}
    	else {
	        //if(convertView == null) {
	            convertView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
	        //}
	        ((TextView)convertView).setText(getChild(groupPosition,childPosition).toString());
	        return convertView;
    	}
    }

    @Override
    public boolean isChildSelectable(int i, int i2) {
        return false;
    } 
    
    void onSignUpEvent(boolean succeeded) {
    	
    	busy = false;    	
    	setStatus(busyButton, busyTextView, busyGroupPos);
    }
    
    void OnRemoveEvent(boolean succeeded) {
    	
    	busy = false;    	
    	setStatus(busyButton, busyTextView, busyGroupPos);
    }
    
    void setStatus(Button button, TextView status, int groupPosition) {
    	
        EventData dat = EventData.GetEventInSignedUpForList( EventData.GetFindListLocation(groupPosition).getEventID());	            	
    	
        if(dat == null) {
        	button.setText("Sign Up");
        	status.setVisibility(View.INVISIBLE);
        }
        else {
            button.setText("Remove Event");
        	status.setVisibility(View.VISIBLE);
            if( dat.isApproved() ) {
            	status.setText("Approved to participate");
            	status.setTextColor(Color.GREEN);
            }
            else {
            	status.setText("Pending approval");
            	status.setTextColor(Color.RED);
            }
        }    	
    }
}
