package br.com.alura.loja.modelo;

import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;

public class Projeto {

	private Long id;
	private String nome;
	private int anoDeInicio;
	
	public Projeto() {};
	
	public Projeto(Long id, String nome, int anoDeInicio) {
		this.id = id;
		this.nome = nome;
		this.anoDeInicio = anoDeInicio;
	}
	
	public Long getId() {
		return this.id;
	}
	
	public String getNome() {
		return this.nome;
	}
	
	public int getAnoDeInicio() {
		return this.anoDeInicio;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public void setAnoDeInicio(int anoDeInicio) {
		this.anoDeInicio = anoDeInicio;
	}

	public String toXML() {
		XStream xstream = new XStream();
		return xstream.toXML(this);
	}
	
	public String toJson() {
		return new Gson().toJson(this);
	}
}
