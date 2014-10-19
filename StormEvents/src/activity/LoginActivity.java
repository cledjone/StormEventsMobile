package activity;

import com.example.androidhive.R;
import com.example.stormEvents.AllProductsActivity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends Activity {
	
	Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
     // Buttons
     	btnLogin = (Button) findViewById(R.id.btn_logar);     	
     		
	  // view products click event
     	btnLogin.setOnClickListener(new View.OnClickListener() {
     		
	     	@Override
	     	public void onClick(View view) {
	     		// Launching All products Activity
	     		Intent i = new Intent(getApplicationContext(), AllProductsActivity.class);
	     		startActivity(i);
	     			
	     	}
     	});
    };
    
    
}
