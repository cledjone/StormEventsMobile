package com.example.stomeventsmobile.activitys;

import com.example.stomeventsmobile.basicas.Amigo;
import com.example.stomeventsmobile.basicas.Evento;
import com.example.stomeventsmobile.fragments.ListAmigosFragment;
import com.example.stomeventsmobile.fragments.ListEventosFragment;
import com.example.stomeventsmobile.utils.ClicouNoItem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;


public class TodosEventosActivity extends ActionBarActivity implements ClicouNoItem{
	
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
		id_usu = getIntent().getStringExtra("idi_usu");
		eventoOuUsuario = getIntent().getStringExtra("eventoOuUsuario");
		
		if(eventoOuUsuario.equals("0")){
			this.setTitle("Participar de um Evento");
			fragmentEventos = new ListEventosFragment();			
			getSupportFragmentManager()
				.beginTransaction()
				.replace(android.R.id.content, fragmentEventos)
				.commit();
		} else {
			this.setTitle("Adicionar Um Amigo");
			fragmentAmigos = new ListAmigosFragment();			
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
		it.putExtra("id_usu", id_usu);
		it.putExtra("fotoUsu", fotoUsu);
		startActivity(it);		
		
	}



	@Override
	public void amigoFoiClicado(Amigo amigo) {
		Intent it = new Intent(this, ChatActivity.class);
		it.putExtra("usuarioLogado", usuarioLogado);
		it.putExtra("amigo", amigo);
		it.putExtra("fotoUsu", fotoUsu);	
		startActivity(it);		
		
	}
		
	
	
}
