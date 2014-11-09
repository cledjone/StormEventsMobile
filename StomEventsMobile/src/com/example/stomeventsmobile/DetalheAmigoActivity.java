package com.example.stomeventsmobile;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;


public class DetalheAmigoActivity extends ActionBarActivity{

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
