package com.example.stomeventsmobile.activitys;


import com.example.stomeventsmobile.R;
import com.example.stomeventsmobile.basicas.Amigo;
import com.example.stomeventsmobile.basicas.Evento;
import com.example.stomeventsmobile.fragments.ListAmigosFragment;
import com.example.stomeventsmobile.fragments.ListEventosFragment;
import com.example.stomeventsmobile.utils.ClicouNoItem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.SimpleOnPageChangeListener;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBar.TabListener;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;


public class HomeActivity extends ActionBarActivity implements TabListener, ClicouNoItem{
	
	String usuarioLogado;
	String fotoUsu;	
	String id_usu;
	ListEventosFragment fragment1;
	ListAmigosFragment fragment2;
	ViewPager pager;
	int paginaSelecionada = 0;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.home_activity);
		
				
		usuarioLogado = getIntent().getStringExtra("usuarioLogado");
		fotoUsu = getIntent().getStringExtra("fotoUsu");
		id_usu = getIntent().getStringExtra("id_usu");			
		
		fragment1 = new ListEventosFragment(id_usu);
		fragment2 = new ListAmigosFragment(id_usu);
				
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
		aba1.setText("Meus Eventos");
		aba1.setTabListener(this);
		
		Tab aba2 = actionBar.newTab();
		aba2.setText("Meus Amigos");
		aba2.setTabListener(this);		
		
		actionBar.addTab(aba1);
		actionBar.addTab(aba2);		
	}
	

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		pager.setCurrentItem(tab.getPosition());	
		paginaSelecionada = tab.getPosition();				
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		pager.setCurrentItem(tab.getPosition());	
		paginaSelecionada = tab.getPosition();		
	}
	
	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		
		
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
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {		
		Intent i = new Intent(this, TodosEventosActivity.class);
		i.putExtra("usuarioLogado", usuarioLogado);		
		i.putExtra("id_usu", id_usu);
		i.putExtra("eventoOuUsuario", String.valueOf(paginaSelecionada));
		i.putExtra("fotoUsu", fotoUsu);
		startActivity(i);		
		return true;		
	}


	@Override
	public void ParticipouEvento() {
		// TODO Auto-generated method stub
		
	}	
	
}
