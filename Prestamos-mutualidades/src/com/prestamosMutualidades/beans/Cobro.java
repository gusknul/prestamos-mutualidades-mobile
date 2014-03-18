package com.prestamosMutualidades.beans;


public class Cobro {
	
	private int idCobro;
	private int idSocio;
	private int idMutualidad;
	private String date;
	private double monto;
	private String estado;
	private int folio;
	private double recargo;
	private int atraso;
	private double numeroSorteo;
	public Cobro(){
		
	}
	
	public Cobro(int idCobro, int idSocio, int idMutualidad, String date,
			double monto, String estado, int folio, double recargo,int numeroSorteo, int atraso) {
		this.idCobro = idCobro;
		this.idSocio = idSocio;
		this.idMutualidad = idMutualidad;
		this.date = date;
		this.monto = monto;
		this.estado = estado;
		this.folio = folio;
		this.recargo = recargo;
		this.numeroSorteo = numeroSorteo;
		this.atraso = atraso;
	}
	
	public Cobro(int idSocio, int idMutualidad, String date,
			double monto, String estado, int folio, double recargo,int numeroSorteo, int atraso) {
		this.idSocio = idSocio;
		this.idMutualidad = idMutualidad;
		this.date = date;
		this.monto = monto;
		this.estado = estado;
		this.folio = folio;
		this.recargo = recargo;
		this.numeroSorteo = numeroSorteo;
		this.atraso = atraso;
	}
	

	public int getIdCobro() {
		return idCobro;
	}
	
	public void setIdCobro(int idCobro) {
		this.idCobro = idCobro;
	}
	
	public int getIdSocio() {
		return idSocio;
	}
	
	public void setIdSocio(int idSocio) {
		this.idSocio = idSocio;
	}
	
	public int getIdMutualidad() {
		return idMutualidad;
	}
	
	public void setIdMutualidad(int idMutualidad) {
		this.idMutualidad = idMutualidad;
	}
	
	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	public double getMonto() {
		return monto;
	}
	
	public void setMonto(double monto) {
		this.monto = monto;
	}
	
	public String getEstado() {
		return estado;
	}
	
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	public int getFolio() {
		return folio;
	}
	
	public void setFolio(int folio) {
		this.folio = folio;
	}
	
	public double getRecargo() {
		return recargo;
	}
	
	public void setRecargo(double recargo) {
		this.recargo = recargo;
	}

	public int getAtraso() {
		return atraso;
	}

	public void setAtraso(int atraso) {
		this.atraso = atraso;
	}

	public double getNumeroSorteo() {
		return numeroSorteo;
	}

	public void setNumeroSorteo(double numeroSorteo) {
		this.numeroSorteo = numeroSorteo;
	}

}
