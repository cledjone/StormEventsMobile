package activity;

import com.example.androidhive.R;
import com.example.androidhive.R.layout;
import com.example.androidhive.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class UsuarioCIndexActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_usuario_cindex);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.usuario_cindex, menu);
		return true;
	}

}
