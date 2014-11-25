package com.example.stomeventsmobile.basicas;

import java.io.Serializable;

public class Amigo implements Serializable {

	private static final long serialVersionUID = 1L;
	public long id;
	public String titulo;
	public String autor;
	public String capa;
	public String editora;
	public String descricao;
	public String login;
	public String usuarioLogado;
	public String fotoUsuarioLogado;
	public boolean favorito;
	
	
	public Amigo(String titulo, String autor, String capa,  String editora, String descricao, String login) {
		super();
		this.titulo = titulo;
		this.autor = autor;
		this.capa = capa;
		this.editora = editora;
		this.descricao = descricao;
		this.login = login;
	}
	
	public Amigo(long id, String titulo, String autor, String capa,  String editora, String descricao, String login) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.autor = autor;
		this.capa = capa;
		this.editora = editora;
		this.descricao = descricao;
		this.login = login;
	}
	
	@Override
	public String toString() {
		return titulo;
	}
}
