package com.example.stomeventsmobile.basicas;

import java.io.Serializable;

public class Amigo implements Serializable {

	private static final long serialVersionUID = 1L;
	public long id;
	public String email;
	public String telefone;
	public String foto;
	public String login;
	public String nome;
	public String rua;
	public String bairro;
	public String cidade;
	public String numero;
	public String usuarioLogado;
	public String fotoUsuarioLogado;
	public boolean favorito;
	
	
	public Amigo(String email, String telefone,  String foto, String login, String nome, String rua , String bairro, String cidade, String numero) {
		super();
		this.email = email;
		this.telefone = telefone;
		this.foto = foto;
		this.login = login;
		this.nome = nome;
		this.rua = rua;
		this.bairro = bairro;
		this.cidade = cidade;
		this.numero = numero;
	}
	
	public Amigo(long id, String email, String telefone,  String foto, String login, String nome, String rua , String bairro, String cidade, String numero) {
		super();
		this.id = id;
		this.email = email;
		this.telefone = telefone;
		this.foto = foto;
		this.login = login;
		this.nome = nome;
		this.rua = rua;
		this.bairro = bairro;
		this.cidade = cidade;
		this.numero = numero;
	}
	
	@Override
	public String toString() {
		return email;
	}
}
