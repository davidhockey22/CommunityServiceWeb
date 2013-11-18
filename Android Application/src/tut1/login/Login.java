package tut1.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends Activity {
	
	//ImageView image;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        
        final Button btn_save = (Button)findViewById(R.id.button1);
        btn_save.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		final TextView username =(TextView)findViewById(R.id.editText1);
        		final TextView password =(TextView)findViewById(R.id.editText2);
        		String uname = username.getText().toString();
        		String pass =  password.getText().toString();
        		
        		if(uname.equals("") && pass.equals(""))
        			startActivity(new Intent(Login.this,MainActivity.class).putExtra("usr",(CharSequence)uname));
        		 else 
        			Toast.makeText(Login.this,"Invalid UserName or Password", Toast.LENGTH_LONG).show();
        		
        	}
        });
        
        
        //example
        //image = (ImageView) findViewById(R.id.imageView1);
    }

}