package com.example.stomeventsmobile.activitys;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.stomeventsmobile.R;
import com.example.stomeventsmobile.basicas.Amigo;
import com.example.stomeventsmobile.basicas.Evento;
import com.example.stomeventsmobile.fragments.DetalheEventoFragment;
import com.example.stomeventsmobile.fragments.ListAmigosFragment;
import com.example.stomeventsmobile.utils.ClicouNoItem;
import com.example.stomeventsmobile.utils.Config;
import com.example.stomeventsmobile.utils.JSONParser;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.SimpleOnPageChangeListener;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar.TabListener;
import android.util.Log;
import android.widget.Toast;

public class DetalheEventoActivity extends ActionBarActivity implements TabListener, ClicouNoItem{

	String usuarioLogado;
	String usuarioDestino;
	String sairParticipar;
	String fotoUsu;
	String id_usu;	
	DetalheEventoFragment fragment1;
	ListAmigosFragment fragment2;	
	ViewPager pager;

	// PEGA A STRING DO CAMINHO DO SERVIDOR 
	Config fachadaServidor  = new Config();	

	
	// Progress Dialog
	private ProgressDialog pDialog;

	// JSON parser class
	JSONParser jsonParser = new JSONParser();
	
	Evento evento;
	
	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_activity);
		
		evento = (Evento)getIntent().getSerializableExtra("evento");		
		usuarioLogado = getIntent().getStringExtra("usuarioLogado");
		sairParticipar = getIntent().getStringExtra("sairParticipar");
		fotoUsu = getIntent().getStringExtra("fotoUsu");	
		id_usu = getIntent().getStringExtra("id_usu");

		//JOGAR DADOS DO USUARIO LOGADO
		evento.usuarioLogado = usuarioLogado;
		evento.fotoUsuarioLogado = fotoUsu;		
		
		fragment1 = DetalheEventoFragment.novaInstancia(evento, sairParticipar);		
		fragment2 = new ListAmigosFragment(id_usu, true);
		
		final ActionBar actionBar = getSupportActionBar();
		
		pager = (ViewPager)findViewById(R.id.viewPager);
		FragmentManager fm = getSupportFragmentManager();
		pager.setAdapter(new MeuAdapter(fm));
		pager.setOnPageChangeListener(new SimpleOnPageChangeListener(){
			@Override
			public void onPageSelected(int position) {
				super.onPageSelected(position);
				actionBar.setSelectedNavigationItem(position);
			}
		});		
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		Tab aba1 = actionBar.newTab();
		aba1.setText("Evento Selecionado");
		aba1.setTabListener(this);
		
		Tab aba2 = actionBar.newTab();
		aba2.setText("Participantes");
		aba2.setTabListener(this);		
		
		actionBar.addTab(aba1);
		actionBar.addTab(aba2);
	 			
	}
	
	class MeuAdapter extends FragmentPagerAdapter {

		public MeuAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			if (position == 0){
				return fragment1;
			}
			return fragment2;						
		}

		@Override
		public int getCount() {
			return 2;
		}
	}

	
	@Override
	public void eventoFoiClicado(Evento evento) {
		
		
	}

	@Override
	public void amigoFoiClicado(Amigo amigo) {
		
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		pager.setCurrentItem(tab.getPosition());
		
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		pager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
		
		
	}

	@Override
	public void ParticipouEvento() {
		new ParticiparEvento().execute();	
		Intent it = new Intent();		
		setResult(RESULT_OK, it);
		finish();
		
	}
	
	class ParticiparEvento extends AsyncTask<String, String, String> {	
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(DetalheEventoActivity.this);
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
						params.add(new BasicNameValuePair("id_usuario", id_usu));
						params.add(new BasicNameValuePair("id_evento", evento.titulo));
						params.add(new BasicNameValuePair("consulta", "participar"));

						// PEGANDO AS INFORMAÇÕES DO WEB SERVICE
						JSONObject json = jsonParser.makeHttpRequest(
								fachadaServidor.retornaFachada(), "GET", params);						

						// TESTE PARA SABER O QUE O WEB SERVICE RESPONDEU
						Log.d("participar: ", json.toString());
						Log.d("paramentros9: ", params.toString());
						//Toast.makeText(LoginActivity.this,json.toString(),Toast.LENGTH_LONG).show();
						
						//VERIFICA A TAG (TAG_SUCESSS) QUE RETORNA DO WEB SERVICE 						
						success = json.getInt(TAG_SUCCESS);
						if (success == 1) {
							String mensagemResultado;
							if (sairParticipar.equals("Sair")){								
								mensagemResultado = "Você saiu deste Evento!";
							}else {
								mensagemResultado = "Participando Com Sucesso!";
							}
							Toast.makeText(DetalheEventoActivity.this,mensagemResultado,Toast.LENGTH_LONG).show();																		
						}else{
							Toast.makeText(DetalheEventoActivity.this,"Erro ao Participar!",Toast.LENGTH_LONG).show();							
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
