package com.example.stomeventsmobile.activitys;

import com.example.stomeventsmobile.basicas.Evento;
import com.example.stomeventsmobile.fragments.DetalheEventoFragment;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;


public class DetalheEventoActivity extends ActionBarActivity{

	String usuarioLogado;
	String usuarioDestino;
	String fotoUsu;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Evento evento = (Evento)getIntent().getSerializableExtra("evento");
		usuarioLogado = getIntent().getStringExtra("usuarioLogado");
		fotoUsu = getIntent().getStringExtra("fotoUsu");
		

		//JOGAR DADOS DO USUARIO LOGADO
		evento.usuarioLogado = usuarioLogado;
		evento.fotoUsuarioLogado = fotoUsu;
		
		DetalheEventoFragment d = DetalheEventoFragment.novaInstancia(evento);		
				
		
		getSupportFragmentManager()
			.beginTransaction()
			.replace(android.R.id.content, d)
			.commit(); 			
	}
	
	
}
