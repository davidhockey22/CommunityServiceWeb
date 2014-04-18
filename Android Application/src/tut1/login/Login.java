package tut1.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends Activity {
	
	//ImageView image;
	
	public static Login current = null;
	String password = null;
	String username = null;
	
    private RequestReceiver receiver;	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        current = this;

        //Register your receiver so that the Activity can be notified
        IntentFilter filter = new IntentFilter(RequestReceiver.PROCESS_RESPONSE);
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        receiver = new RequestReceiver();
        registerReceiver(receiver, filter);
        
        final Button btn_save = (Button)findViewById(R.id.button1);
        btn_save.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		
        		final TextView uname =(TextView)findViewById(R.id.editText1);
        		final TextView pword =(TextView)findViewById(R.id.editText2);
        		
        		username = uname.getText().toString();
        		password =  pword.getText().toString();
        		
        		if(username == null || username.equals("") ||
        				password == null || password.equals("")) {
        			
        			Toast.makeText(Login.this,"Please enter a username and password", Toast.LENGTH_LONG).show();        			
        			return;
        		}
        		
            	MySQLRequest.Create(Login.this, Integer.toString(MySQLRequest.kindVolQuery), username);        			
        	}
        });
        final Button reg = (Button)findViewById(R.id.register);
        reg.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		
        		//launch main activity
    			startActivity(new Intent(Login.this,Register.class).putExtra("test",""));
        	}
        });
        
		//load token
		SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
		String str = sharedPref.getString( "token", "" );
		if(str.equals("")) {}
		else {
			
    		findViewById(R.id.editText1).setVisibility(View.INVISIBLE);
    		findViewById(R.id.editText2).setVisibility(View.INVISIBLE);
    		findViewById(R.id.button1).setVisibility(View.INVISIBLE);
    		findViewById(R.id.register).setVisibility(View.INVISIBLE);
			
        	MySQLRequest.Create(Login.this, Integer.toString(MySQLRequest.kindToken), str);        			
		}
    }
    
    @Override
    protected void onDestroy() {
        Log.v("Login", "onDestroy");
        
        current = null;
        
        unregisterReceiver(receiver);
        super.onDestroy();
    }    
    
    void OnMySQLRequest(int kindQuery) { //CreateMySQLRequest() is done loading VolunteerData
    	
       	if(VolunteerData.current != null && VolunteerData.current.getVolunteerID() != null ) {
    		
       		//save token
    		SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
    		SharedPreferences.Editor editor = sharedPref.edit();
    		editor.putString( "token", VolunteerData.current.getToken() );
    		editor.commit();
       		
    		//launch main activity
			startActivity(new Intent(Login.this,MainActivity.class).putExtra("usr",(CharSequence)username));    		
    	}
		 else {
			 			 
    		findViewById(R.id.editText1).setVisibility(View.VISIBLE);
    		findViewById(R.id.editText2).setVisibility(View.VISIBLE);
    		findViewById(R.id.button1).setVisibility(View.VISIBLE);
    		findViewById(R.id.register).setVisibility(View.VISIBLE);		 
			 
			 if(kindQuery == MySQLRequest.kindToken) //token failed
				 return;
    		
			Toast.makeText(Login.this,"Invalid UserName or Password", Toast.LENGTH_LONG).show();
		 }
    }
}