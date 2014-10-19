package modelo.fachada;

import java.util.Date;
import java.util.List;

import modelo.basicas.*;
import modelo.controlador.*;
import modelo.seguranca.*;

public class Fachada implements IFachada {

	private static IFachada fachada;
	private UsuarioControlador usuarioControlador;
	
	private Fachada() {
		this.usuarioControlador = new UsuarioControlador();	
	}

	public static IFachada getInstancia() {
		if (fachada == null) {
			fachada = new Fachada();
		}
		return fachada;
	}

	// USUARIO

	/*
	@Override
	public void inserirUsuario(Usuario usuario) throws Exception {
		//usuarioControlador.inserirUsuario(usuario);
	}

	@Override
	public List<Usuario> consultarTodosUsuarios() {
		return usuarioControlador.consultarTodos();
	}

	@Override
	public boolean validarLogin(String login) {
		return usuarioControlador.validarLogin(login);
	}

	@Override
	public void removerUsuario(Usuario usuario) {
		usuarioControlador.removerUsuario(usuario);
	}

	@Override
	public void alterarUsuario(Usuario usuario) throws Exception {
		usuarioControlador.alterarUsuario(usuario);
	}

	@Override
	public Usuario logarUsuario(Usuario usuario) throws LoginInvalidoException {
		return usuarioControlador.logarUsuario(usuario);
	}	
*/
}
