package com.example.stomeventsmobile.activitys;

import com.example.stomeventsmobile.basicas.Amigo;
import com.example.stomeventsmobile.basicas.Evento;
import com.example.stomeventsmobile.fragments.ListAmigosFragment;
import com.example.stomeventsmobile.fragments.ListEventosFragment;
import com.example.stomeventsmobile.utils.ClicouNoItem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;


public class TodosEventosAmigosActivity extends ActionBarActivity implements ClicouNoItem{
	
	ListEventosFragment fragmentEventos;
	ListAmigosFragment fragmentAmigos;	
	String usuarioLogado;	
	String fotoUsu;
	String id_usu;
	String eventoOuUsuario;

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		
		usuarioLogado = getIntent().getStringExtra("usuarioLogado");
		fotoUsu = getIntent().getStringExtra("fotoUsu");
		id_usu = getIntent().getStringExtra("id_usu");
		eventoOuUsuario = getIntent().getStringExtra("eventoOuUsuario");
		
		if(eventoOuUsuario.equals("0")){
			this.setTitle("Participar de um Evento");
			fragmentEventos = new ListEventosFragment(id_usu, false);			
			getSupportFragmentManager()
				.beginTransaction()
				.replace(android.R.id.content, fragmentEventos)
				.commit();
		} else {
			this.setTitle("Adicionar Um Amigo");
			fragmentAmigos = new ListAmigosFragment(id_usu, false);			
			getSupportFragmentManager()
				.beginTransaction()
				.replace(android.R.id.content, fragmentAmigos)
				.commit();
		}
		
		
	}



	@Override
	public void eventoFoiClicado(Evento evento) {
		Intent it = new Intent(this, DetalheEventoActivity.class);
		it.putExtra("usuarioLogado", usuarioLogado);
		it.putExtra("evento", evento);
		it.putExtra("sairParticipar", "Participar");
		it.putExtra("id_usu", id_usu);
		it.putExtra("fotoUsu", fotoUsu);
		startActivityForResult(it, 0);		
	}



	@Override
	public void amigoFoiClicado(Amigo amigo) {
		Intent it = new Intent(this, DetalheAmigoActivity.class);
		it.putExtra("usuarioLogado", usuarioLogado);
		it.putExtra("amigo", amigo);
		it.putExtra("amizade", "Adicionar Amigo");		
		it.putExtra("id_usu", id_usu);
		it.putExtra("fotoUsu", fotoUsu);	
		startActivityForResult(it, 0);		
		
	}


	@Override
	public void ParticipouEvento() {
		Intent it = new Intent();		
		setResult(RESULT_OK, it);
		finish();		
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
