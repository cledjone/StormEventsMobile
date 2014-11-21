package com.example.stomeventsmobile.fragments;


import com.example.stomeventsmobile.R;
import com.example.stomeventsmobile.activitys.ChatActivity;
import com.example.stomeventsmobile.basicas.Amigo;
import com.example.stomeventsmobile.utils.Config;
import com.squareup.picasso.Picasso;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class DetalheAmigoFragment extends Fragment {
	// PEGA A STRING DO CAMINHO DO SERVIDOR 
    Config fotoAmigo  = new Config();

	Amigo amigo;
	
	String fotoUsu;
	String fotoOutroUsu;
	String usuarioLogado;
	String usuarioDestino;
	ImageView imgCapa;
	TextView txtTitulo;	
	TextView txtAutor;
	TextView txtEditora;
	TextView txtDescricao;	
	MenuItem btnConversar;	
	MenuItem acrescentarEvento;
	
	String tablet;
	
	public static DetalheAmigoFragment novaInstancia(Amigo amigo){
		Bundle args = new Bundle();
		args.putSerializable("amigo", amigo);
		
		DetalheAmigoFragment f = new DetalheAmigoFragment();
		f.setArguments(args);
		return f;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);	
		
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		setHasOptionsMenu(true);
		
		
		View layout = inflater.inflate(R.layout.fragment_detalhe, null);
				
		imgCapa = (ImageView)layout.findViewById(R.id.imageViewUsu);
		txtTitulo = (TextView)layout.findViewById(R.id.txtTitulo);
		txtAutor = (TextView)layout.findViewById(R.id.txtAutor);
		txtEditora = (TextView)layout.findViewById(R.id.txtEditora);	
		txtDescricao = (TextView)layout.findViewById(R.id.txtDescricao);
		
		amigo = (Amigo)getArguments().getSerializable("amigo");
		txtTitulo.setText(amigo.titulo);
		txtAutor.setText(amigo.autor);
		txtEditora.setText(amigo.editora);
		txtDescricao.setText(amigo.descricao);
		
		usuarioLogado = amigo.usuarioLogado;		
		fotoUsu = amigo.fotoUsuarioLogado;
		fotoOutroUsu = amigo.capa;
		
		Picasso.with(this.getActivity())
		.load(fotoAmigo.retornaFotoUsuario()+amigo.capa)
		.noFade()
		.into(imgCapa);
		
		
		return layout;
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);	
		inflater.inflate(R.menu.detalhe, menu);	
		
	}
	

	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent i = new Intent(this.getActivity(), ChatActivity.class);
		i.putExtra("usuarioLogado", usuarioLogado);
		i.putExtra("usuarioDestino", usuarioDestino);
		i.putExtra("fotoUsu", fotoUsu);
		i.putExtra("fotoOutroUsu", fotoOutroUsu);
		startActivity(i);		
	
		return true;
	}
	
	interface AmigoNosFavoritos {
		void amigoAdicionadoAoFavorito(Amigo amigo);
	}
	
}

