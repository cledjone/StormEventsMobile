package com.example.stomeventsmobile;

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


public class ListaEventosActivity extends ActionBarActivity implements TabListener, ClicouNoItem{
	
	ListEventosFragment fragment1;
	ListMeusEventosFragment fragment2;
	ListAmigosFragment fragment3;
	ViewPager pager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lista_eventos_activity);
		
		fragment1 = new ListEventosFragment();
		fragment2 = new ListMeusEventosFragment();
		fragment3 = new ListAmigosFragment();
				
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
		aba1.setText("Eventos");
		aba1.setTabListener(this);
		
		Tab aba2 = actionBar.newTab();
		aba2.setText("Participando");
		aba2.setTabListener(this);
		
		Tab aba3 = actionBar.newTab();
		aba3.setText("Amigos");
		aba3.setTabListener(this);
		
		actionBar.addTab(aba1);
		actionBar.addTab(aba2);		
		actionBar.addTab(aba3);
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
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}
	
	
	class MeuAdapter extends FragmentPagerAdapter {

		public MeuAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			if (position == 0){
				return fragment1;
				
			} else {
				if (position == 1){
					return fragment2;
				} else {
					return fragment3;
				}
			}
			
		}

		@Override
		public int getCount() {
			return 3;
		}
	}


	@Override
	public void eventoFoiClicado(Evento evento) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void amigoFoiClicado(Amigo amigo) {
		Intent it = new Intent(this, DetalheAmigoActivity.class);
		it.putExtra("amigo", amigo);
		it.putExtra("tablet", "celular");
		startActivity(it);
		
	}

}
