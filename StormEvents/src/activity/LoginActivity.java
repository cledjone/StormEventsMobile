package activity;

import com.example.androidhive.R;
import com.example.stormEvents.JSONParser;
import com.example.stormEvents.NewProductActivity;

import android.widget.Toast;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import android.widget.EditText;

public class LoginActivity extends Activity {
	
	
	Button btnLogin;
	EditText textLogin;
	EditText textSenha;
	
	EditText txtName;
	EditText txtPrice;
	EditText txtDesc;
	EditText txtCreatedAt;
	Button btnSave;
	

	String pid;
	String nomeProd;

	// Progress Dialog
	private ProgressDialog pDialog;

	// JSON parser class
	JSONParser jsonParser = new JSONParser();

	// single product url
	private static final String url_product_detials = "http://192.168.56.102/android/get_product_details.php";

	// url to update product
	private static final String url_update_product = "http://192.168.56.102/android/update_product.php";
	
	// url to delete product
	private static final String url_delete_product = "http://192.168.56.102/android/delete_product.php";

	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_PRODUCT = "product";
	private static final String TAG_PID = "pid";
	private static final String TAG_NAME = "name";
	private static final String TAG_PRICE = "price";
	private static final String TAG_DESCRIPTION = "description";

	
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);		
	 
		btnLogin = (Button) findViewById(R.id.btn_logar);
		textLogin = (EditText) findViewById(R.id.editLogin);
		textSenha = (EditText) findViewById(R.id.editSenha);
		
		btnLogin.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				
			    // ESCREVER DADOS
				pid = textLogin.getText().toString();
				nomeProd = textSenha.getText().toString();
				// VALIDAR DADOS
				new GetProductDetails().execute();
				// Launching All products Activity
			//	Intent i = new Intent(getApplicationContext(), UsuarioLISTActivity.class);
			//	startActivity(i);
				
			}
		});		
		
	}    
    
    /**
	 * Background Async Task to Get complete product details
	 * */
	class GetProductDetails extends AsyncTask<String, String, String> {		

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(LoginActivity.this);
			pDialog.setMessage("Loading product details. Please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		/**
		 * Getting product details in background thread
		 * */
		protected String doInBackground(String... params) {

	
			// updating UI from Background Thread
			runOnUiThread(new Runnable() {
				public void run() {
					// Check for success tag
					int success;
					try {
						// Building Parameters
						List<NameValuePair> params = new ArrayList<NameValuePair>();
						params.add(new BasicNameValuePair("pid", pid));
						params.add(new BasicNameValuePair("name", nomeProd));

						// getting product details by making HTTP request
						// Note that product details url will use GET request
						JSONObject json = jsonParser.makeHttpRequest(
								url_product_detials, "GET", params);

						// check your log for json response
						Log.d("Single Product Details", json.toString());
						//Toast.makeText(LoginActivity.this,pid,Toast.LENGTH_LONG).show();
						Toast.makeText(LoginActivity.this,json.toString(),Toast.LENGTH_LONG).show();
						
						// json success tag
						success = json.getInt(TAG_SUCCESS);
						if (success == 1) {
							// successfully received product details
							JSONArray productObj = json
									.getJSONArray(TAG_PRODUCT); // JSON Array
							
							// get first product object from JSON Array
							JSONObject product = productObj.getJSONObject(0);

							// product with this pid found
							// Edit Text
						//	txtName = (EditText) findViewById(R.id.inputName);
							txtPrice = (EditText) findViewById(R.id.inputPrice);
							txtDesc = (EditText) findViewById(R.id.inputDesc);

							// display product data in EditText
						//	txtName.setText(product.getString(TAG_NAME));
						//	txtPrice.setText(product.getString(TAG_PRICE));
						//	txtDesc.setText(product.getString(TAG_DESCRIPTION));
						//	Toast.makeText(LoginActivity.this,"Achei!",Toast.LENGTH_LONG).show();

						}else{
							//Toast.makeText(LoginActivity.this,"Usuario Não Encontrado!",Toast.LENGTH_LONG).show();
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			});

			return null;
		}


		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {
			// dismiss the dialog once got all details
			pDialog.dismiss();
		}
	}

	/**
	 * Background Async Task to  Save product Details
	 * */
	class SaveProductDetails extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(LoginActivity.this);
			pDialog.setMessage("Saving product ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		/**
		 * Saving product
		 * */
		protected String doInBackground(String... args) {

			
			Toast.makeText(LoginActivity.this,"mensagem",Toast.LENGTH_LONG).show();
			// getting updated data from EditTexts
			String name = "mudei";  //txtName.getText().toString();
			String price = "6.00";//txtPrice.getText().toString();
			String description = "consegui";//txtDesc.getText().toString();

			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair(TAG_PID, "2"));
			params.add(new BasicNameValuePair(TAG_NAME, name));
			params.add(new BasicNameValuePair(TAG_PRICE, price));
			params.add(new BasicNameValuePair(TAG_DESCRIPTION, description));

			// sending modified data through http request
			// Notice that update product url accepts POST method
			JSONObject json = jsonParser.makeHttpRequest(url_update_product,
					"POST", params);

			// check json success tag
			try {
				int success = json.getInt(TAG_SUCCESS);
				
				if (success == 1) {
					// successfully updated
					Intent i = getIntent();
					// send result code 100 to notify about product update
					setResult(100, i);
					finish();
				} else {
					// failed to update product
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}


		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {
			// dismiss the dialog once product uupdated
			pDialog.dismiss();
		}
	}    
    
}
