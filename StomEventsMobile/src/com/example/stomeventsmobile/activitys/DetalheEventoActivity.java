package com.example.stomeventsmobile.activitys;

import com.example.stomeventsmobile.R;
import com.example.stomeventsmobile.basicas.Amigo;
import com.example.stomeventsmobile.basicas.Evento;
import com.example.stomeventsmobile.fragments.DetalheEventoFragment;
import com.example.stomeventsmobile.fragments.ListAmigosFragment;
import com.example.stomeventsmobile.utils.ClicouNoItem;

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

public class DetalheEventoActivity extends ActionBarActivity implements TabListener, ClicouNoItem{

	String usuarioLogado;
	String usuarioDestino;
	String fotoUsu;
	DetalheEventoFragment fragment1;
	ListAmigosFragment fragment2;
	ViewPager pager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_activity);
		
		Evento evento = (Evento)getIntent().getSerializableExtra("evento");		
		usuarioLogado = getIntent().getStringExtra("usuarioLogado");
		fotoUsu = getIntent().getStringExtra("fotoUsu");		

		//JOGAR DADOS DO USUARIO LOGADO
		evento.usuarioLogado = usuarioLogado;
		evento.fotoUsuarioLogado = fotoUsu;		
		
		fragment1 = DetalheEventoFragment.novaInstancia(evento);		
		fragment2 = new ListAmigosFragment();
		
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void amigoFoiClicado(Amigo amigo) {
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		
	}
	
	
}
