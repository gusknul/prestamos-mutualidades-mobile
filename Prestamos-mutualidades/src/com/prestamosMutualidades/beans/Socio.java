package com.prestamosMutualidades.beans;


public class Socio {
	
	private int idSocio;
	private String nombreCompleto;
	private String direccion;
	private String telefono;

	
	
	public Socio(){
		
	}
	

	public Socio(int idSocio, String nombreCompleto, String direccion, String telefono){
		this.idSocio = idSocio;
		this.nombreCompleto = nombreCompleto;
		this.direccion = direccion;
		this.telefono = telefono;
	}
	
	public Socio(String nombreCompleto, String direccion, String telefono){
		this.nombreCompleto = nombreCompleto;
		this.direccion = direccion;
		this.telefono = telefono;
	}
	

	
	public int getIdSocio() {
		return idSocio;
	}
	
	public void setIdSocio(int idSocio) {
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
