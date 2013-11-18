package tut1.login;

import tut1.login.R;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {
	
	private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private String[] mDrawerStrings;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        
	        //setContentView(R.layout.second);
	        setContentView(R.layout.drawer);
	        
	        /*
	        Intent in = getIntent();
	        if (in.getCharSequenceExtra("usr") != null) {
	        	final TextView setmsg = (TextView)findViewById(R.id.showmsg);
	        	setmsg.setText("Welcome \n "+in.getCharSequenceExtra("usr"));	        	
	        }
	        */
	        
	        mDrawerStrings = getResources().getStringArray(R.array.drawer_strings);	        
	        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
	        mDrawerList = (ListView) findViewById(R.id.left_drawer);
	        
	        // set a custom shadow that overlays the main content when the drawer opens
	        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
	        
	        // set up the drawer's list view with items and click listener
	        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
	                R.layout.drawer_list_item, mDrawerStrings));
	        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

	        if (savedInstanceState == null) {
	            selectItem(0);
	        }	        
    }
    
    /* The click listener for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }    
    
    private void selectItem(int position) {
    	
        // update the main content by replacing fragments
    	Fragment fragment = null;
    	if( position == 0 ) {
    		
    		fragment = new EventsFragment();
    	}
    	else if( position == 1 ) {
    		
    		fragment = new FindFragment();
    	}
        
        //Bundle args = new Bundle();
        //args.putInt(EventsFragment.ARG_PLANET_NUMBER, position);
        //fragment.setArguments(args);

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

        // update selected item and title, then close the drawer
        mDrawerList.setItemChecked(position, true);
        mDrawerLayout.closeDrawer(mDrawerList);
    }    
}