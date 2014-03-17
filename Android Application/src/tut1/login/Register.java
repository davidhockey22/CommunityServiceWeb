package tut1.login;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Register extends Activity {
	
	public static Register current = null;
		
	String user, password, first, last, phone, email;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        current = this;
        
        final Button but = (Button)findViewById(R.id.registerButton);
        but.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		
        		user = ( (EditText)findViewById(R.id.userEdit) ).getText().toString();
        		password = ( (EditText)findViewById(R.id.passwordEdit) ).getText().toString();
        		String confirm = ( (EditText)findViewById(R.id.confirmPasswordEdit) ).getText().toString();
        		first = ( (EditText)findViewById(R.id.firstNameEdit) ).getText().toString();
        		last = ( (EditText)findViewById(R.id.lastNameEdit) ).getText().toString();
        		phone = ( (EditText)findViewById(R.id.phoneEdit) ).getText().toString();
        		email = ( (EditText)findViewById(R.id.emailEdit) ).getText().toString();
        		
        		String error = FormValidation.checkPassword(password);
        		error += FormValidation.confirmPassword(password, confirm);
        		error += FormValidation.checkPhoneNumber(phone);
        		error += FormValidation.checkEmail(email);
        		
        		if(error.equals("") == false) {
        			
        			( (TextView)findViewById(R.id.error) ).setText(error);
        		}
        		else {
        			MySQLRequest.Create(Register.this, Integer.toString(MySQLRequest.kindRegister), "");
        		}
        	}
        });        
    }
    
    @Override
    protected void onDestroy() {
        Log.v("Login", "onDestroy");
        
        current = null;      
        super.onDestroy();
    }
    
    void onRegistration(boolean successful) {
    	
    	if(successful) {
    		
    		Toast.makeText(Register.this,"Registration successful", Toast.LENGTH_LONG).show();
    		
    		//go back to login screen
    		finish(); //close current activity
    	}
    	else {
    		
    		Toast.makeText(Register.this,"Registration failed", Toast.LENGTH_LONG).show();
    	}
    }
}