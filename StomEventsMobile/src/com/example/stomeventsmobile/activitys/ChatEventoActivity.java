package com.example.stomeventsmobile.activitys;



import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.stomeventsmobile.R;


import com.example.stomeventsmobile.adapters.ChatAdapter;
import com.example.stomeventsmobile.basicas.Evento;
import com.example.stomeventsmobile.basicas.Chat;
import com.example.stomeventsmobile.utils.Config;
import com.example.stomeventsmobile.utils.JSONParser;


import android.app.ListActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;



public class ChatEventoActivity extends ListActivity {
	
	Evento evento;
	List<Chat> mensagens;
	ChatAdapter adapter;
	Timer myTimer = new Timer();	
	
	// PEGA A STRING DO CAMINHO DO SERVIDOR 
	Config fachadaServidor  = new Config();
	
	// JSON parser class
	JSONParser jsonParserEnviar = new JSONParser();
	JSONParser jsonParserReceber = new JSONParser();
	
	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	
	int i=0;	   
	private Handler handler = new Handler();
	public ListView msgView;
	public ArrayAdapter<String> msgList;
	
	
	EditText textChat;
	Button btnEnviar;
	String tipoConsulta;
	String usuarioLogado;
	String usuarioDestino;
	String fotoUsu;
	String fotoOutroUsu;
	String mensagemEscrita;
	String mensagemRecebida;
	
			
	 private void UpdateGUI() {
	      i++;
	      handler.post(myRunnable);
	   }
	  
	  
	  final Runnable myRunnable = new Runnable() {
	      public void run() {	    	  	    	
	    	tipoConsulta = "consulta_chat";	    				    		
			new RecebeMensagem().execute();			
	      }
	   };	
	

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chat);		
		
		evento = (Evento)getIntent().getSerializableExtra("evento");		
		mensagens = new ArrayList<Chat>();
				
		adapter = new ChatAdapter(this, mensagens);
		setListAdapter(adapter);
	
		textChat = (EditText) findViewById(R.id.txt_chat);
		usuarioLogado = getIntent().getStringExtra("usuarioLogado");				
		fotoUsu = getIntent().getStringExtra("fotoUsu");		
				
		msgView = (ListView) findViewById(android.R.id.list);
		btnEnviar = (Button) findViewById(R.id.btn_enviar);	
		  
		 //TIMER QUE ATUALIZA AS MENSAGENS
	      myTimer.schedule(new TimerTask() {
	         @Override
	         public void run() {UpdateGUI();}
	      }, 0, 15000);			
				
		btnEnviar.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {						
				tipoConsulta = "incluir_chat";
				mensagemEscrita = textChat.getText().toString();
				new EnviaMensagem().execute();				
			}			
		}
		
		
		);
		
	}
	
	@Override
	public void onBackPressed() {
	    myTimer.cancel();
	    finish();
	    return;
	}   
	

	class RecebeMensagem extends AsyncTask<String, String, String> {	
		@Override
		protected void onPreExecute() {
			super.onPreExecute();			
		}

		protected String doInBackground(String... params) {	
			// ABRINDO THREAD NO BACKGROUND
			runOnUiThread(new Runnable() {
				public void run() {
					int success;
					try {
						// INSERINDO PARAMETROS
						List<NameValuePair> params = new ArrayList<NameValuePair>();
						params.add(new BasicNameValuePair("consulta", tipoConsulta));
						params.add(new BasicNameValuePair("login_remetente", usuarioLogado));
						params.add(new BasicNameValuePair("login_destinatario", ""));
						params.add(new BasicNameValuePair("evento", evento.titulo));
						
						Log.d("paramentrosChatEvento: ", params.toString());

						// PEGANDO AS INFORMAÇÕES DO WEB SERVICE
						JSONObject json = jsonParserReceber.makeHttpRequest(
								fachadaServidor.retornaFachada(), "GET", params);

						// TESTE PARA SABER O QUE O WEB SERVICE RESPONDEU						
						//	Toast.makeText(ChatEventoActivity.this,json.toString(),Toast.LENGTH_LONG).show();
						
						//VERIFICA A TAG (TAG_SUCESSS) QUE RETORNA DO WEB SERVICE 						
						success = json.getInt(TAG_SUCCESS);
						if (success == 1) {							
							JSONArray jsonEntries = json.getJSONArray("tab_chat");							
							for (int i = 0; i < jsonEntries.length(); i++) {							
								JSONObject jsonEntry = jsonEntries.getJSONObject(i);
								mensagemRecebida = jsonEntry.getString("mensagem");
								fotoOutroUsu = jsonEntry.getString("foto_usuario");								
								mensagens.add(new Chat(usuarioLogado, usuarioDestino,mensagemRecebida,fotoOutroUsu, false));
															
							}				
							adapter.notifyDataSetChanged();
						}						
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			});

			return null;
		}

		protected void onPostExecute(String file_url) {			
			
		}
	}    
	
	
	
	class EnviaMensagem extends AsyncTask<String, String, String> {	
		@Override
		protected void onPreExecute() {
			super.onPreExecute();			
		}

		protected String doInBackground(String... params) {	
			// ABRINDO THREAD NO BACKGROUND
			runOnUiThread(new Runnable() {
				public void run() {
					int success;
					try {
						// INSERINDO PARAMETROS
						List<NameValuePair> params = new ArrayList<NameValuePair>();
						params.add(new BasicNameValuePair("consulta", tipoConsulta));
						params.add(new BasicNameValuePair("login_remetente", usuarioLogado));
						params.add(new BasicNameValuePair("login_destinatario", ""));
						params.add(new BasicNameValuePair("foto_usuario", fotoUsu));
						params.add(new BasicNameValuePair("evento", evento.titulo));
						params.add(new BasicNameValuePair("mensagem", mensagemEscrita));
						
						Log.d("paramentrosChatEvento: ", params.toString());

						// PEGANDO AS INFORMAÇÕES DO WEB SERVICE
						JSONObject json = jsonParserEnviar.makeHttpRequest(
								fachadaServidor.retornaFachada(), "GET", params);

						// TESTE PARA SABER O QUE O WEB SERVICE RESPONDEU						
						// Toast.makeText(ChatActivity.this,json.toString(),Toast.LENGTH_LONG).show();
						
						//VERIFICA A TAG (TAG_SUCESSS) QUE RETORNA DO WEB SERVICE 						
						success = json.getInt(TAG_SUCCESS);
						if (success == 1) {							
							mensagens.add(new Chat(usuarioLogado, usuarioDestino,mensagemEscrita,fotoUsu, true));				
							adapter.notifyDataSetChanged();
							textChat.setText("");
						}						
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			});

			return null;
		}

		protected void onPostExecute(String file_url) {			
			
		}
	}	
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if (resultCode == RESULT_OK && requestCode == 0){
			Intent it = new Intent();		
			setResult(RESULT_OK, it);
			finish();
		}
	}

}