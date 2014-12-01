package com.example.stomeventsmobile.fragments;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import com.example.stomeventsmobile.R;
import com.example.stomeventsmobile.adapters.AmigosAdapter;
import com.example.stomeventsmobile.basicas.Amigo;
import com.example.stomeventsmobile.utils.ClicouNoItem;
import com.example.stomeventsmobile.utils.Config;
import com.example.stomeventsmobile.utils.JSONParser;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

@SuppressLint("ValidFragment")
public class ListAmigosFragment extends ListFragment {
	
	// Creating JSON Parser object
	JSONParser jParser = new JSONParser();	

	// PEGA A STRING DO CAMINHO DO SERVIDOR 
	Config fachadaServidor  = new Config();	

	List<Amigo> amigos;
	String tipoConsulta;
	ReadAmigosAsyncTask task;
	ProgressBar progress;
	TextView txtMensagem;
	String meusAmigos;
	Boolean amizade;
	String id_usuario = "todos";
	MenuItem acrescentarAmigo;
	
	
	public ListAmigosFragment() {	
		
	}	
	
	@SuppressLint("ValidFragment")
	public ListAmigosFragment(String id_usu, Boolean amigos) {
		id_usuario = id_usu;
		amizade = amigos;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if (amizade){
			setHasOptionsMenu(true);
		}		
		tipoConsulta = "listar_usuarios";
		meusAmigos = "meus_amigos";	
		
		
		setRetainInstance(true);	
		if (amigos != null){
			txtMensagem.setVisibility(View.GONE);
			progress.setVisibility(View.GONE);
			refreshList();
			
		} else {
			if (task != null && task.getStatus() == Status.RUNNING){
				mostrarProgress();
				
			} else {
				iniciarDownload();
			}
		}
	}

	private void iniciarDownload() {
		
		ConnectivityManager cm = (ConnectivityManager) getActivity()
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		if (cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected()) {

			task = new ReadAmigosAsyncTask();
			task.execute();

		} else {
			progress.setVisibility(View.GONE);
			txtMensagem.setVisibility(View.VISIBLE);
			txtMensagem.setText("Sem conexao com a Internet");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View layout = inflater.inflate(R.layout.fragment_lista,
				container, false);

		progress = (ProgressBar) layout.findViewById(R.id.progressBar1);
		txtMensagem = (TextView) layout.findViewById(R.id.textMensagem);

		return layout;
	}
	
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);		
		if (getActivity() instanceof ClicouNoItem){
			((ClicouNoItem)getActivity()).amigoFoiClicado((amigos.get(position)));
		}
	}

	
	private void refreshList() {			
		AmigosAdapter adapter = new AmigosAdapter(getActivity(), amigos);
		setListAdapter(adapter);
	}
	
	private void mostrarProgress() {
		progress.setVisibility(View.VISIBLE);
		txtMensagem.setVisibility(View.VISIBLE);
		txtMensagem.setText("Carregando...");
	}
	
	class ReadAmigosAsyncTask extends AsyncTask<Void, Void, List<Amigo>> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mostrarProgress();
		}

		@Override
		protected List<Amigo> doInBackground(Void... parames) {
			
			List<Amigo> amigos = new ArrayList<Amigo>();
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("consulta", tipoConsulta));
			params.add(new BasicNameValuePair("id_usuario", id_usuario));
						
			if (amizade){				
				params.add(new BasicNameValuePair("argumento", meusAmigos));
				
			}
			
			// getting JSON string from URL
			JSONObject json = jParser.makeHttpRequest(fachadaServidor.retornaFachada(), "GET", params);
			
			// CRIA UM LOG NO LOGCAT COM O RESULTADO DO JSON
			Log.d("Todos Amigos: ", json.toString());
			Log.d("paramentros3: ", params.toString());
			
			
			try{
				JSONArray jsonEntries = json.getJSONArray("usuarios");				
				
				for (int i = 0; i < jsonEntries.length(); i++) {	
				
				JSONObject jsonEntry = jsonEntries.getJSONObject(i);		
						
				Amigo amigo = new Amigo(
					Integer.parseInt(jsonEntry.getString("ID_USU")),
					jsonEntry.getString("EMAIL_USU"),
					jsonEntry.getString("TEL_USU"),					
					jsonEntry.getString("FOTO_USU"),
					jsonEntry.getString("LOGIN_USU"),
					jsonEntry.getString("NOME_USU"),
					jsonEntry.getString("END_RUA"),								
					jsonEntry.getString("END_BAIRRO"),
					jsonEntry.getString("END_CIDADE"),
					jsonEntry.getString("END_NUMERO"));
					amigos.add(amigo);
				}		
				
				return amigos;								
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;			
		}

		@Override
		protected void onPostExecute(List<Amigo> result) {
			super.onPostExecute(result);
			if (result != null) {
				amigos = result;
				refreshList();
				txtMensagem.setVisibility(View.GONE);
			} else {
				txtMensagem.setText("Deu erro amigos!");
			}
			progress.setVisibility(View.GONE);
		}
	}
	

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.detalhe, menu);			
		acrescentarAmigo = menu.findItem(R.id.action_btn_add);		
	}
}
