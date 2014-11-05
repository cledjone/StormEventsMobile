package com.example.stomeventsmobile;

import java.io.Serializable;

public class Amigo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	long id;
	String titulo;
	String autor;
	String capa;
	String editora;
	String descricao;
	public boolean favorito;
	
	
	public Amigo(String titulo, String autor, String capa,  String editora, String descricao) {
		super();
		this.titulo = titulo;
		this.autor = autor;
		this.capa = capa;
		this.editora = editora;
		this.descricao = descricao;
	}
	
	public Amigo(long id, String titulo, String autor, String capa,  String editora, String descricao) {
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
