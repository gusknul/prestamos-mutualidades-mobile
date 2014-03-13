package com.prestamosMutualidades.beans;

import java.util.Date;

public class Pago {
	
	private int idSocio;
	private int idMutualidad;
	private Date fecha;
	private double monto;
	private String estado;
	private int sorteo;
	private int atraso;
	
	
	public Pago(){
		
	}
	
	public Pago(int idSocio, int idMutualidad, Date fecha, double monto,
			String estado, int sorteo, int atraso) {
		this.idSocio = idSocio;
		this.idMutualidad = idMutualidad;
		this.fecha = fecha;
		this.monto = monto;
		this.estado = estado;
		this.sorteo = sorteo;
		this.atraso = atraso;
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
	
	public Date getFecha() {
		return fecha;
	}
	
	public void setFecha(Date fecha) {
		this.fecha = fecha;
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
	
	public int getSorteo() {
		return sorteo;
	}
	
	public void setSorteo(int sorteo) {
		this.sorteo = sorteo;
	}
	
	public int getAtraso() {
		return atraso;
	}
	
	public void setAtraso(int atraso) {
		this.atraso = atraso;
	}
	
}
