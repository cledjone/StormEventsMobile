package com.example.stomeventsmobile.fragments;


import com.example.stomeventsmobile.R;
import com.example.stomeventsmobile.activitys.ChatActivity;
import com.example.stomeventsmobile.basicas.Evento;
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

public class DetalheEventoFragment extends Fragment {

	// PEGA A STRING DO CAMINHO DO SERVIDOR 
    Config fotoEvento  = new Config();

	Evento evento;
	
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
	
	
	public static DetalheEventoFragment novaInstancia(Evento evento){
		Bundle args = new Bundle();
		args.putSerializable("evento", evento);
		
		DetalheEventoFragment f = new DetalheEventoFragment();
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
		
		evento = (Evento)getArguments().getSerializable("evento");
		txtTitulo.setText(evento.titulo);
		txtAutor.setText(evento.autor);
		txtEditora.setText(evento.editora);
		txtDescricao.setText(evento.descricao);
		
		usuarioLogado = evento.usuarioLogado;		
		fotoUsu = evento.fotoUsuarioLogado;
		fotoOutroUsu = evento.capa;
		
		Picasso.with(this.getActivity())
		.load(fotoEvento.retornaFotoEvento()+evento.capa)
		.noFade()
		.into(imgCapa);
		
		
		return layout;
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);				
		
	}
	

	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent i = new Intent(this.getActivity(), ChatActivity.class);
		i.putExtra("usuarioLogado", usuarioLogado);
		i.putExtra("usuarioDestino", usuarioDestino);
		i.putExtra("fotoUsu", fotoUsu);
		i.putExtra("fotoOutroUsu", fotoOutroUsu);
		startActivity(i);		
		
	//	Intent i = new Intent(this, ChatActivity.class);
	//	startActivity(i);			
		
	/*		if (getActivity() instanceof EventoNosFavoritos){
				((EventoNosFavoritos)getActivity()).eventoAdicionadoAoFavorito(evento);
			}
			
		}		
		
		Intent it = this.getActivity().getIntent();
		tablet = it.getStringExtra("tablet");		
		if (tablet!=null){
			this.getActivity().finish();			
		}else {
			btnSalvarExcluir.setVisible(false);			
		}
		
		
		return super.onOptionsItemSelected(item);
		*/
		return true;
	}
	
	interface EventoNosFavoritos {
		void eventoAdicionadoAoFavorito(Evento evento);
	}
	
}

