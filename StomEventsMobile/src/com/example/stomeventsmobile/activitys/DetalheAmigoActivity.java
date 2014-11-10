package com.example.stomeventsmobile.activitys;

import com.example.stomeventsmobile.basicas.Amigo;
import com.example.stomeventsmobile.fragments.DetalheAmigoFragment;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;


public class DetalheAmigoActivity extends ActionBarActivity{

	String usuarioLogado;
	String usuarioDestino;
	String fotoUsu;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Amigo amigo = (Amigo)getIntent().getSerializableExtra("amigo");
		usuarioLogado = getIntent().getStringExtra("usuarioLogado");
		fotoUsu = getIntent().getStringExtra("fotoUsu");
		

		//JOGAR DADOS DO USUARIO LOGADO
		amigo.usuarioLogado = usuarioLogado;
		amigo.fotoUsuarioLogado = fotoUsu;
		
		DetalheAmigoFragment d = DetalheAmigoFragment.novaInstancia(amigo);
		
		
		usuarioDestino = amigo.login;
		
		
		getSupportFragmentManager()
			.beginTransaction()
			.replace(android.R.id.content, d)
			.commit(); 			
	}
	
	
}
