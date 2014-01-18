package tut1.login;

//import java.util.ArrayList;
//import java.util.List;

import tut1.login.R;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
	            selectItem(2);
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
    	
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

        // update selected item and title, then close the drawer
        mDrawerList.setItemChecked(position, true);
        mDrawerLayout.closeDrawer(mDrawerList);
    }
}