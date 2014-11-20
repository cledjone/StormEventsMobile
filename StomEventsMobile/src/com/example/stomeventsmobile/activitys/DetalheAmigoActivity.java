package com.example.stomeventsmobile.activitys;

import com.example.stomeventsmobile.basicas.Amigo;
import com.example.stomeventsmobile.fragments.DetalheAmigoFragment;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;


public class DetalheAmigoActivity extends ActionBarActivity{

	String usuarioLogado;
	String usuarioDestino;
	String fotoUsu;	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Amigo amigo = (Amigo)getIntent().getSerializableExtra("amigo");		
		DetalheAmigoFragment d = DetalheAmigoFragment.novaInstancia(amigo);		
		
		
		getSupportFragmentManager()
			.beginTransaction()
			.replace(android.R.id.content, d)
			.commit(); 			
	}
	
	
}
