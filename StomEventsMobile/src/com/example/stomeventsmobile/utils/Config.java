package com.example.stomeventsmobile.utils;

public class Config {
	public String  retornaFachada() {
		return "http://192.168.56.102/android/fachada.php";
	}
	
	public String  retornaFotoUsuario() {
		return "http://192.168.56.102:8080/StormEvents/resources/images/fotosUsuarios/";
	}
	
	public String  retornaFotoEvento() {
		return "http://192.168.56.102:8080/StormEvents/resources/images/fotosEventos/";
	}
}
