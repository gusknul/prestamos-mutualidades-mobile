package com.prestamosMutualidades.beans;

import java.util.ArrayList;
import java.util.List;

public class Socio {
	
	private Integer idSocio;
	private String nombreCompleto;
	private String direccion;
	private String telefono;
	
	private ArrayList<Pago> listaPagos;
	private ArrayList<Cobro> listaCobros;
	
	
	public Socio(){
		listaPagos = new ArrayList<Pago>();
		listaCobros = new ArrayList<Cobro>();
		
	}
	

	public Socio(int idSocio, String nombreCompleto, String direccion, String telefono){
		this.idSocio = idSocio;
		this.nombreCompleto = nombreCompleto;
		this.direccion = direccion;
		this.telefono = telefono;
		listaPagos = new ArrayList<Pago>();
		listaCobros = new ArrayList<Cobro>();
	}
	
	public List<Pago> getListaPagos() {
		return listaPagos;
	}

	public void setListaPagos(ArrayList<Pago> listaPagos) {
		this.listaPagos = listaPagos;
	}

	public ArrayList<Cobro> getListaCobros() {
		return listaCobros;
	}

	public void setListaCobros(ArrayList<Cobro> listaCobros) {
		this.listaCobros = listaCobros;
	}
	
	public Integer getIdSocio() {
		return idSocio;
	}
	
	public void setIdSocio(Integer idSocio) {
		this.idSocio = idSocio;
	}
	
	public String getNombreCompleto() {
		return nombreCompleto;
	}
	
	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}
	
	public String getDireccion() {
		return direccion;
	}
	
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	
	public String getTelefono() {
		return telefono;
	}
	
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	@Override
	public String toString() {
		return    idSocio + "\n"
				+ nombreCompleto + "\n"
				+ direccion + "\n"
				+ telefono;
	}
	
	

}
