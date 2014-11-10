package com.example.stomeventsmobile.activitys;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.Socket;
import java.net.UnknownHostException;
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
import com.example.stomeventsmobile.R.id;
import com.example.stomeventsmobile.R.layout;
import com.example.stomeventsmobile.adapters.ChatAdapter;
import com.example.stomeventsmobile.basicas.Amigo;
import com.example.stomeventsmobile.basicas.Chat;
import com.example.stomeventsmobile.utils.Config;
import com.example.stomeventsmobile.utils.JSONParser;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
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
import android.widget.Toast;

public class ChatActivity extends ListActivity {
	
	
	List<Chat> mensagens;
	ChatAdapter adapter;
	Timer myTimer = new Timer();
	boolean esperandoEnviar = false;
	boolean esperandoReceber = false;
	
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
	
	
	
	//public ArrayAdapter<String> msgList=new ArrayAdapter<String>(this,
		//	android.R.layout.simple_list_item_1);;
	
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
	    		esperandoReceber = true;		    		
					new RecebeMensagem().execute();								
					adapter.notifyDataSetChanged();	    		
				esperandoReceber = false;
	      }
	   };	
	

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chat);
		
		if (savedInstanceState == null){
			mensagens = new ArrayList<Chat>();
		} else {
			mensagens = (ArrayList<Chat>)savedInstanceState.getSerializable("lista");
		}
		
		adapter = new ChatAdapter(this, mensagens);
		setListAdapter(adapter);
	
		textChat = (EditText) findViewById(R.id.txt_chat);
		usuarioLogado = getIntent().getStringExtra("usuarioLogado");
		usuarioDestino = getIntent().getStringExtra("usuarioDestino");		
		fotoUsu = getIntent().getStringExtra("fotoUsu");
		fotoOutroUsu = getIntent().getStringExtra("fotoOutroUsu");			
		  
	      myTimer.schedule(new TimerTask() {
	         @Override
	         public void run() {UpdateGUI();}
	      }, 0, 30000);
	

	//	msgView = (ListView) findViewById(R.id.list);

	//	msgList = new ArrayAdapter<String>(this,
		//		android.R.layout.simple_list_item_1);
	//	msgView.setAdapter(msgList);

//		msgView.smoothScrollToPosition(msgList.getCount() - 1);

		//Button btnSend = (Button) findViewById(R.id.btn_Send);
		 btnEnviar = (Button) findViewById(R.id.btn_enviar);
		
		//receiveMsg();
				
		btnEnviar.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				esperandoEnviar = true;						
					tipoConsulta = "incluir_chat";
					mensagemEscrita =textChat.getText().toString();
					new EnviaMensagem().execute();				
					mensagens.add(new Chat(usuarioLogado, usuarioDestino,mensagemEscrita,fotoUsu));				
					adapter.notifyDataSetChanged();
					textChat.setText("");	
				esperandoEnviar = false;
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
	
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putSerializable("lista", (Serializable)mensagens);
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
						params.add(new BasicNameValuePair("login_destinatario", usuarioDestino));					

						// PEGANDO AS INFORMAÇÕES DO WEB SERVICE
						JSONObject json = jsonParserReceber.makeHttpRequest(
								fachadaServidor.retornaFachada(), "GET", params);

						// TESTE PARA SABER O QUE O WEB SERVICE RESPONDEU						
						//	Toast.makeText(ChatActivity.this,json.toString(),Toast.LENGTH_LONG).show();
						
						//VERIFICA A TAG (TAG_SUCESSS) QUE RETORNA DO WEB SERVICE 						
						success = json.getInt(TAG_SUCCESS);
						if (success == 1) {								
							
							JSONArray jsonEntries = json.getJSONArray("tab_chat");							
							for (int i = 0; i < jsonEntries.length(); i++) {							
								JSONObject jsonEntry = jsonEntries.getJSONObject(i);
								mensagemRecebida = jsonEntry.getString("mensagem");
								mensagens.add(new Chat(usuarioLogado, usuarioDestino,mensagemRecebida,fotoOutroUsu));
															
							}				
							
							
							//Toast.makeText(LoginActivity.this,"Usuario Logado Com Sucesso!",Toast.LENGTH_LONG).show();							
						//	Intent i = new Intent(getApplicationContext(), ListaEventosActivity.class);
						//	i.putExtra("usuarioLogado", usuLogin.toString());
						//	startActivity(i);							
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
						params.add(new BasicNameValuePair("login_destinatario", usuarioDestino));
						params.add(new BasicNameValuePair("mensagem", mensagemEscrita));
						

						// PEGANDO AS INFORMAÇÕES DO WEB SERVICE
						JSONObject json = jsonParserEnviar.makeHttpRequest(
								fachadaServidor.retornaFachada(), "GET", params);

						// TESTE PARA SABER O QUE O WEB SERVICE RESPONDEU						
							Toast.makeText(ChatActivity.this,json.toString(),Toast.LENGTH_LONG).show();
						
						//VERIFICA A TAG (TAG_SUCESSS) QUE RETORNA DO WEB SERVICE 						
						success = json.getInt(TAG_SUCCESS);
						if (success == 1) {							
							//Toast.makeText(LoginActivity.this,"Usuario Logado Com Sucesso!",Toast.LENGTH_LONG).show();							
						//	Intent i = new Intent(getApplicationContext(), ListaEventosActivity.class);
						//	i.putExtra("usuarioLogado", usuLogin.toString());
						//	startActivity(i);							
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

		protected void onPostExecute(String file_url) {			
			
		}
	}    
	
	
	
	public void displayMsg(String msg)
	{ 
		final String mssg=msg;
	    handler.post(new Runnable() {
			
			@Override
			public void run() {			
				msgList.add(mssg);
				msgView.setAdapter(msgList);
				msgView.smoothScrollToPosition(msgList.getCount() - 1);
				Log.d("","hi");
			}
		});
		
	}

}