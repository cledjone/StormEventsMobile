package com.example.stomeventsmobile.adapters;

import java.util.List;

import com.example.stomeventsmobile.R;
import com.example.stomeventsmobile.activitys.DetalheAmigoActivity;
import com.example.stomeventsmobile.basicas.Chat;
import com.example.stomeventsmobile.utils.Config;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ChatAdapter extends BaseAdapter {

	List<Chat> mensagens;
	ImageView exibeFotoUsu;
	Context ctx;
	// PEGA A STRING DO CAMINHO DO SERVIDOR 
	Config caminhoFotoUsuario  = new Config();
	
	
	public ChatAdapter(Context ctx, List<Chat> c){
		this.mensagens = c;
		this.ctx = ctx;
	}
	
	@Override
	public int getCount() {
		return mensagens.size();
	}

	@Override
	public Object getItem(int position) {
		return mensagens.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// 1 passo
		Chat chat = mensagens.get(position);
		// 2 passo
		if (convertView == null){
			convertView = LayoutInflater.from(ctx).
					inflate(R.layout.item_chat, null);
		}
		// 3 passo		
		TextView txtModelo = (TextView)convertView.findViewById(R.id.txtModelo);
		exibeFotoUsu = (ImageView) convertView.findViewById(R.id.imgLogo);		
	//	TextView txtAno = (TextView)convertView.findViewById(R.id.txtAno);		
				
		txtModelo.setText(chat.mensagem);
		
		Picasso.with(this.ctx)
		.load(caminhoFotoUsuario.retornaFotoUsuario()+chat.fotoUsuario)
		.noFade()
		.into(exibeFotoUsu);
		
		//txtAno.setText(String.valueOf());
		
		// 4 passo
		return convertView;
	}

}
