package com.example.stomeventsmobile;


import com.squareup.picasso.Picasso;

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
    Config fotoUsuario  = new Config();

	Amigo amigo;
	
	ImageView imgCapa;
	TextView txtTitulo;	
	TextView txtAutor;
	TextView txtEditora;
	TextView txtDescricao;	
	MenuItem btnSalvarExcluir;	
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
		
		
		View layout = inflater.inflate(R.layout.fragment_detalhe_amigo, null);
				
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
		
		Picasso.with(this.getActivity())
		.load(fotoUsuario.retornaFotoUsuario()+amigo.capa)
		.noFade()
		.into(imgCapa);
		
		
		return layout;
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.detalhe, menu);
		btnSalvarExcluir = menu.findItem(R.id.action_favoritos);
		if (amigo.favorito){
			btnSalvarExcluir.setIcon(android.R.drawable.ic_menu_delete);
		} else {
			btnSalvarExcluir.setIcon(android.R.drawable.ic_menu_save);
		}		
		
	}
	

	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {			
	/*		if (getActivity() instanceof AmigoNosFavoritos){
				((AmigoNosFavoritos)getActivity()).amigoAdicionadoAoFavorito(amigo);
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
	
	interface AmigoNosFavoritos {
		void amigoAdicionadoAoFavorito(Amigo amigo);
	}
	
}

