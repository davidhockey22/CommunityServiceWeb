package tut1.login;

//import java.util.ArrayList;
//import java.util.List;

import java.util.Date;

//import org.CommunityService.EntitiesMapped.Event;

import tut1.login.R;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	public static MainActivity current = null;
	public static boolean test = false;
	
	private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private String[] mDrawerStrings;
    
    //private RequestReceiver receiver;
    
    /** Called when the activity is first created. */
    
    //code execution order
    //MainActivity.LoadEventData()
    //MySQLRequest.onHandleIntent()
    //MySQLRequest.contactWebService()
    //RequestReceiver.parseResponse()
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        
	        current = this;
	        
	        //setContentView(R.layout.second);
	        setContentView(R.layout.drawer);
	        
	        //THIS RECEIVER IS NOT NEEDED BECAUSE ITS CREATED IN LOGIN.JAVA
	        
	        //Register your receiver so that the Activity can be notified
//	        IntentFilter filter = new IntentFilter(RequestReceiver.PROCESS_RESPONSE);
//	        filter.addCategory(Intent.CATEGORY_DEFAULT);
//	        receiver = new RequestReceiver();
//	        registerReceiver(receiver, filter);
	        
	        //test
	        //Event ev = new Event("EventName", new Date(1000), new Date(1000), 1.0f);
	        	        
	        mDrawerStrings = getResources().getStringArray(R.array.drawer_strings);	        
	        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
	        mDrawerList = (ListView) findViewById(R.id.left_drawer);
	        
	        // set a custom shadow that overlays the main content when the drawer opens
	        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
	        
	        // set up the drawer's list view with items and click listener
	        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
	                R.layout.drawer_list_item, mDrawerStrings));
	        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

	        //change title so action bar shows user name
			setTitle(VolunteerData.current.getName());
			
			//ActionBar ab = getActionBar();
			//ab.setDisplayShowTitleEnabled( false );
				        
	        if (savedInstanceState == null) {
	            selectItem(2);
	        }
    }
    
    //creates the search icon on the action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }
    
    //event handler for touching the search icon on the action bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_search:
            	
            	if(mDrawerLayout.isDrawerOpen(mDrawerList)) {
            		
            		mDrawerLayout.closeDrawer(mDrawerList);
            	}
            	else
            		mDrawerLayout.openDrawer(mDrawerList);
            	
            	return true;
            case R.id.action_settings:

            	return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }    
	
    @Override
    protected void onDestroy() {
        Log.v("MainActivity", "onDestroy");
        
        current = null;
        
        //unregisterReceiver(receiver);
        super.onDestroy();
    }
    
    /* The click listener for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }
    
    //check if you have internet connection
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    Log.w("INTERNET:",String.valueOf(i));
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        Log.w("INTERNET:", "connected!");
                        return true;
                    }
                }
            }
        }
        
		Toast.makeText(context, "No internet connection", Toast.LENGTH_LONG).show();        
        
        return false;
    }  
    
    private void selectItem(int position) {
    	
        // update the main content by replacing fragments
    	Fragment fragment = null;
    	if( position == 0 ) {
    		
    		fragment = new EventFragment();
    	}
    	else if( position == 1 ) {
    		
    		fragment = new FindKindFragment();
    	}
    	else if( position == 2 ) {
    		
    		fragment = new MainFragment();
    	}
    	else if( position == 3 ) {
    		
       		//save empty token
    		SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
    		SharedPreferences.Editor editor = sharedPref.edit();
    		editor.putString( "token", "" );
    		editor.commit();
    		
    		Intent intent = new Intent(MainActivity.this, Login.class);
    		intent.putExtra("param", "");
    		
    		//this flag will return to the Login instance if it already exists
    		//or create a new instance
    		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
    		MainActivity.this.startActivity(intent);
    		return;
    	}
    	
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

        // update selected item and title, then close the drawer
        mDrawerList.setItemChecked(position, true);
        mDrawerLayout.closeDrawer(mDrawerList);
    }
}