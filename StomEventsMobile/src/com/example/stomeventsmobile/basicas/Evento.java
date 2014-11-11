package com.example.stomeventsmobile.basicas;

import java.io.Serializable;

public class Evento implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	long id;
	public String titulo;
	public String autor;
	public String capa;
	public String editora;
	public String descricao;
	public String usuarioLogado;
	public String fotoUsuarioLogado;
	public boolean favorito;
	
	
	public Evento(String titulo, String autor, String capa,  String editora, String descricao) {
		super();
		this.titulo = titulo;
		this.autor = autor;
		this.capa = capa;
		this.editora = editora;
		this.descricao = descricao;
	}
	
	public Evento(long id, String titulo, String autor, String capa,  String editora, String descricao) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.autor = autor;
		this.capa = capa;
		this.editora = editora;
		this.descricao = descricao;
	}
	
	@Override
	public String toString() {
		return titulo;
	}
}
