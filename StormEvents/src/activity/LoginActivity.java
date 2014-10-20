package activity;

import com.example.androidhive.R;
import modelo.basicas.JSONParser;
import android.widget.Toast;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.EditText;

public class LoginActivity extends Activity {
	
	//ELEMENTOS DA TELA
	Button btnLogin;
	EditText textLogin;
	EditText textSenha;	

	//PARÂMETROS DA CONSULTA
	String usuLogin;
	String usuSenha;	
	String tipoConsulta;

	// Progress Dialog
	private ProgressDialog pDialog;

	// JSON parser class
	JSONParser jsonParser = new JSONParser();

	// single product url
	private static final String url_consulta_login = "http://192.168.56.102/android/fachada.php";
	
	// JSON Node names
	private static final String TAG_SUCCESS = "success";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);		
	 
		//ATRIBUI OS INPUTS A VARIAVEIS
		btnLogin = (Button) findViewById(R.id.btn_logar);
		textLogin = (EditText) findViewById(R.id.editLogin);
		textSenha = (EditText) findViewById(R.id.editSenha);
		
		btnLogin.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View view) {				
			    // ESCREVE PARAMETROS DA CONSULTA
				usuLogin = textLogin.getText().toString();
				usuSenha = textSenha.getText().toString();
				tipoConsulta = "consulta_login";
				// VALIDA E EFETUA O LOGIN
				new VerificaLogin().execute();			
			}
		});		
		
	}    
    
	class VerificaLogin extends AsyncTask<String, String, String> {	
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(LoginActivity.this);
			pDialog.setMessage("Verificando,Por Favor Aguarde...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		protected String doInBackground(String... params) {	
			// ABRINDO THREAD NO BACKGROUND
			runOnUiThread(new Runnable() {
				public void run() {
					int success;
					try {
						// INSERINDO PARAMETROS
						List<NameValuePair> params = new ArrayList<NameValuePair>();
						params.add(new BasicNameValuePair("login", usuLogin));
						params.add(new BasicNameValuePair("senha", usuSenha));
						params.add(new BasicNameValuePair("consulta", tipoConsulta));

						// PEGANDO AS INFORMAÇÕES DO WEB SERVICE
						JSONObject json = jsonParser.makeHttpRequest(
								url_consulta_login, "GET", params);

						// TESTE PARA SABER O QUE O WEB SERVICE RESPONDEU						
							//Toast.makeText(LoginActivity.this,json.toString(),Toast.LENGTH_LONG).show();
						
						//VERIFICA A TAG (TAG_SUCESSS) QUE RETORNA DO WEB SERVICE 						
						success = json.getInt(TAG_SUCCESS);
						if (success == 1) {							
							Toast.makeText(LoginActivity.this,"Usuario Logado Com Sucesso!",Toast.LENGTH_LONG).show();
						}else{
							Toast.makeText(LoginActivity.this,"Usuario Não Encontrado!",Toast.LENGTH_LONG).show();
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			});

			return null;
		}

		protected void onPostExecute(String file_url) {			
			pDialog.dismiss();
		}
	}    
}
