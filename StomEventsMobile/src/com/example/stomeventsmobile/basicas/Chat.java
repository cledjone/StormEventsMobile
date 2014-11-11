package com.example.stomeventsmobile.basicas;

import java.io.Serializable;

public class Chat implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String loginOrigem;
	public String loginDestino;
	public String mensagem;
	public String fotoUsuario;
	public boolean remetente ;
		
	public Chat(String loginOrigem, String loginDestino, String mensagem, String fotoUsuario, boolean remetente) {
		this.loginOrigem = loginOrigem;
		this.loginDestino = loginDestino;
		this.mensagem = mensagem;
		this.fotoUsuario = fotoUsuario;		
		this.remetente = remetente;
	}
}
