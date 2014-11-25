package com.example.stomeventsmobile.activitys;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.stomeventsmobile.basicas.Amigo;
import com.example.stomeventsmobile.basicas.Evento;
import com.example.stomeventsmobile.fragments.DetalheAmigoFragment;
import com.example.stomeventsmobile.utils.ClicouNoItem;
import com.example.stomeventsmobile.utils.Config;
import com.example.stomeventsmobile.utils.JSONParser;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;


public class DetalheAmigoActivity extends ActionBarActivity implements ClicouNoItem{

	String usuarioLogado;
	String usuarioDestino;
	String fotoUsu;	
	String amizade;
	String id_usu;
	String id_usuAdicionado;
	
	// PEGA A STRING DO CAMINHO DO SERVIDOR 
	Config fachadaServidor  = new Config();	

		
	// Progress Dialog
	private ProgressDialog pDialog;

	// JSON parser class
	JSONParser jsonParser = new JSONParser();
		
	Amigo amigo;
		
	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		amizade = getIntent().getStringExtra("amizade");		
		Amigo amigo = (Amigo)getIntent().getSerializableExtra("amigo");		
		DetalheAmigoFragment d = DetalheAmigoFragment.novaInstancia(amigo, amizade);
		id_usu = getIntent().getStringExtra("id_usu");
		id_usuAdicionado = String.valueOf(amigo.id);
		
		
		getSupportFragmentManager()
			.beginTransaction()
			.replace(android.R.id.content, d)
			.commit();			
	
	}


	@Override
	public void amigoFoiClicado(Amigo amigo) {
	
		
	}


	@Override
	public void eventoFoiClicado(Evento evento) {
	
		
	}


	@Override
	public void ParticipouEvento() {
		new FazerAmizade().execute();		
		Intent it = new Intent();		
		setResult(RESULT_OK, it);
		finish();
		
	}
	
	class FazerAmizade extends AsyncTask<String, String, String> {	
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(DetalheAmigoActivity.this);
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
						params.add(new BasicNameValuePair("id_usuario_logado", id_usu));
						params.add(new BasicNameValuePair("id_usuario_adicionado", id_usuAdicionado));
						params.add(new BasicNameValuePair("consulta", "fazer_amizade"));

						// PEGANDO AS INFORMAÇÕES DO WEB SERVICE
						JSONObject json = jsonParser.makeHttpRequest(
								fachadaServidor.retornaFachada(), "GET", params);						

						// TESTE PARA SABER O QUE O WEB SERVICE RESPONDEU						
						//Toast.makeText(LoginActivity.this,json.toString(),Toast.LENGTH_LONG).show();
						
						//VERIFICA A TAG (TAG_SUCESSS) QUE RETORNA DO WEB SERVICE 						
						success = json.getInt(TAG_SUCCESS);
						if (success == 1) {							
							String mensagemResultado;
							if (amizade.equals("Desfazer Amizade")){								
								mensagemResultado = "Amizade Desfeita!";
							}else {
								mensagemResultado = "Amigo Adicionado Com Sucesso!";
							}
							Toast.makeText(DetalheAmigoActivity.this,mensagemResultado,Toast.LENGTH_LONG).show();	
											
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
