package com.prestamosMutualidades.beans;

import java.util.Date;

public class Cobro {
	
	private int idCobro;
	private int idSocio;
	private int idMutualidad;
	private Date date;
	private double monto;
	private String estado;
	private int folio;
	private double recargo;
	
	Cobro(){
		
	}
	
	public Cobro(int idCobro, int idSocio, int idMutualidad, Date date,
			double monto, String estado, int folio, double recargo) {
		this.idCobro = idCobro;
		this.idSocio = idSocio;
		this.idMutualidad = idMutualidad;
		this.date = date;
		this.monto = monto;
		this.estado = estado;
		this.folio = folio;
		this.recargo = recargo;
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
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
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
	
}
