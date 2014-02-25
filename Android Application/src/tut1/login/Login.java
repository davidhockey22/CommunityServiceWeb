package tut1.login;

import java.util.Date;

import org.CommunityService.EntitesUnmapped.Event;

import testPackage.TestClass;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
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
        		
//        		if(MainActivity.test == true) {
        		
//        			if(username.equals("") && password.equals(""))
        		
//        				VolunteerData vol = new VolunteerData();
//        				vol.InitTestVolunteer();
        		
//        				startActivity(new Intent(Login.this,MainActivity.class).putExtra("usr",(CharSequence)username));
//        			else 
//        				Toast.makeText(Login.this,"Invalid UserName or Password", Toast.LENGTH_LONG).show();
//        		}
//        		else
            		MySQLRequest.Create(Login.this, Integer.toString(MySQLRequest.kindVolQuery), username);        			
        	}
        });
        
        //example
        //image = (ImageView) findViewById(R.id.imageView1);
    }
    
    @Override
    protected void onDestroy() {
        Log.v("Login", "onDestroy");
        
        current = null;
        
        unregisterReceiver(receiver);
        super.onDestroy();
    }    
    
    void OnMySQLRequest() { //CreateMySQLRequest() is done loading VolunteerData
    	
       	if(VolunteerData.current != null && VolunteerData.current.getVolunteerID() != null ) {
    		
    		//launch main activity
			startActivity(new Intent(Login.this,MainActivity.class).putExtra("usr",(CharSequence)username));    		
    	}
		 else 
			Toast.makeText(Login.this,"Invalid UserName or Password", Toast.LENGTH_LONG).show();    	
    }
}