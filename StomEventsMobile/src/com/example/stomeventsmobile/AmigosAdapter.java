package com.example.stomeventsmobile;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

public class AmigosAdapter extends ArrayAdapter<Amigo> {

	public AmigosAdapter(Context context, List<Amigo> objects) {
		super(context, 0, 0, objects);
		
	}	

	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;

		Amigo amigo = getItem(position);

		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(
					R.layout.linha_amigo, null);		

			holder = new ViewHolder();
			holder.titulo = (TextView) convertView
					.findViewById(R.id.txtTitulo);
			holder.autor = (TextView) convertView
					.findViewById(R.id.txtAutor);
			holder.editora = (TextView) convertView
					.findViewById(R.id.txtEditora);			
			//holder.capaLivro = (ImageView) convertView
				//	.findViewById(R.id.imageView1);			
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.titulo.setText(amigo.titulo);
		holder.editora.setText(amigo.editora);
		holder.autor.setText(amigo.autor);	

		
	/*	Picasso.with(getContext())
		.load("http://192.168.56.102:8080/StormEvents/fotosUsuarios/"+amigo.capa)
		.noFade()
		.into(holder.capaLivro); */

		return convertView;
	}

	static class ViewHolder {
		//ImageView capaLivro;
		TextView titulo;
		TextView autor;
		TextView editora;		
	}
}