package com.example.stomeventsmobile;



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


public class ListaEventosActivity extends ActionBarActivity implements TabListener, ClicouNoEvento{
	
	ListMeusEventosFragment fragment1;
	ListEventosFragment fragment2;
	ViewPager pager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lista_eventos_activity);
		
		fragment1 = new ListMeusEventosFragment();
		fragment2 = new ListEventosFragment();
				
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
		aba2.setText("Todos Eventos");
		aba2.setTabListener(this);
		
		actionBar.addTab(aba1);
		actionBar.addTab(aba2);		
	}
	

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		pager.setCurrentItem(tab.getPosition());
		
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
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

}
