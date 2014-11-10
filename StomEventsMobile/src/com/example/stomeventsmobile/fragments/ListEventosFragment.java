package com.example.stomeventsmobile.fragments;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import com.example.stomeventsmobile.R;
import com.example.stomeventsmobile.R.id;
import com.example.stomeventsmobile.R.layout;
import com.example.stomeventsmobile.adapters.EventosAdapter;
import com.example.stomeventsmobile.basicas.Evento;
import com.example.stomeventsmobile.utils.Config;
import com.example.stomeventsmobile.utils.JSONParser;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ListEventosFragment extends ListFragment {
	
	// Creating JSON Parser object
	JSONParser jParser = new JSONParser();	

	// PEGA A STRING DO CAMINHO DO SERVIDOR 
	Config fachadaServidor  = new Config();	

	List<Evento> eventos;
	String tipoConsulta;
	ReadEventosAsyncTask task;
	ProgressBar progress;
	TextView txtMensagem;
	
	

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		tipoConsulta = "listar_eventos";
		setRetainInstance(true);	
		if (eventos != null){
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

			task = new ReadEventosAsyncTask();
			task.execute();

		} else {
			progress.setVisibility(View.GONE);
			txtMensagem.setVisibility(View.VISIBLE);
			txtMensagem.setText("Sem conex‹o com a Internet");
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
		//eventosRepositorio db = new eventosRepositorio(getActivity());
		/*int idLivro = db.verificaFavorito(eventos.get(position));
		 if (idLivro>-1){			 
			 eventos.get(position).favorito=true;
		 }else {
			 eventos.get(position).favorito=false;
		 }	
		 eventos.get(position).id = idLivro;
		if (getActivity() instanceof ClicouNoEvento){
			((ClicouNoEvento)getActivity()).livroFoiClicado(eventos.get(position));
		}*/
	}

	private void refreshList() {			
		EventosAdapter adapter = new EventosAdapter(getActivity(), eventos);
		setListAdapter(adapter);
	}
	
	private void mostrarProgress() {
		progress.setVisibility(View.VISIBLE);
		txtMensagem.setVisibility(View.VISIBLE);
		txtMensagem.setText("Carregando...");
	}
	
	class ReadEventosAsyncTask extends AsyncTask<Void, Void, List<Evento>> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mostrarProgress();
		}

		@Override
		protected List<Evento> doInBackground(Void... parames) {
			
			List<Evento> eventos = new ArrayList<Evento>();
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("consulta", tipoConsulta));
			// getting JSON string from URL
			JSONObject json = jParser.makeHttpRequest(fachadaServidor.retornaFachada(), "GET", params);
			
			// CRIA UM LOG NO LOGCAT COM O RESULTADO DO JSON
			Log.d("Todos Eventos: ", json.toString());
			Log.d("params1: ", params.toString());
			
			
			try{
				JSONArray jsonEntries = json.getJSONArray("eventos");
				for (int i = 0; i < jsonEntries.length(); i++) {		
					
				JSONObject jsonEntry = jsonEntries.getJSONObject(i);			
						
				Evento evento = new Evento(
					jsonEntry.getString("ID_EVE"),
					jsonEntry.getString("Data_Evento"),
					jsonEntry.getString("FOTO"),
					jsonEntry.getString("END_RUA"),
					jsonEntry.getString("END_CIDADE"));			
					eventos.add(evento);
				}	
				return eventos;								
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;			
		}

		@Override
		protected void onPostExecute(List<Evento> result) {
			super.onPostExecute(result);
			if (result != null) {
				eventos = result;
				refreshList();
				txtMensagem.setVisibility(View.GONE);
			} else {
				txtMensagem.setText("Deu erro eventos!");
			}
			progress.setVisibility(View.GONE);
		}
	}
}
