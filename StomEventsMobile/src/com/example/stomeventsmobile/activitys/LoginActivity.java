package com.example.stomeventsmobile.activitys;

import android.widget.Toast;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.stomeventsmobile.R;
import com.example.stomeventsmobile.R.id;
import com.example.stomeventsmobile.R.layout;
import com.example.stomeventsmobile.basicas.Evento;
import com.example.stomeventsmobile.utils.Config;
import com.example.stomeventsmobile.utils.JSONParser;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.EditText; 

public class LoginActivity extends Activity {	
	
	//ELEMENTOS DA TELA
	Button btnLogin;
	EditText textLogin;
	EditText textSenha;
	
	// PEGA A STRING DO CAMINHO DO SERVIDOR 
	Config fachadaServidor  = new Config();	

	//PARÂMETROS DA CONSULTA
	String usuLogin;
	String usuSenha;	
	String tipoConsulta;
	String fotoUsu;

	// Progress Dialog
	private ProgressDialog pDialog;

	// JSON parser class
	JSONParser jsonParser = new JSONParser();
	
	// JSON Node names
	private static final String TAG_SUCCESS = "success";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity);		
	 
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
								fachadaServidor.retornaFachada(), "GET", params);						

						// TESTE PARA SABER O QUE O WEB SERVICE RESPONDEU						
						//Toast.makeText(LoginActivity.this,json.toString(),Toast.LENGTH_LONG).show();
						
						//VERIFICA A TAG (TAG_SUCESSS) QUE RETORNA DO WEB SERVICE 						
						success = json.getInt(TAG_SUCCESS);
						if (success == 1) {							
							Toast.makeText(LoginActivity.this,"Usuario Logado Com Sucesso!",Toast.LENGTH_LONG).show();
							//PEGA A FOTO DO USUARIO NO RETORNO
							JSONArray jsonEntries = json.getJSONArray("TAB_USU");							
							JSONObject jsonEntry = jsonEntries.getJSONObject(0);			
							fotoUsu = jsonEntry.getString("FOTO_USU");							
							Intent i = new Intent(getApplicationContext(), ListaEventosActivity.class);
							i.putExtra("usuarioLogado", usuLogin.toString());
							i.putExtra("fotoUsu", fotoUsu);
							startActivity(i);							
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
