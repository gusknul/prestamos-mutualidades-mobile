package com.prestamosMutualidades.beans;


public class Pago {
	
	private int idPago;

	private int idSocio;
	private int idMutualidad;
	private String fecha;
	private double monto;
	private String estado;
	private int sorteo;
	private int atraso;
	private int numeroBloc;
	private double recargo;
	
	
	public Pago(){
		
	}
	
	public Pago(int idPago , int idSocio, int idMutualidad, String fecha, double monto,
			String estado, int sorteo, int atraso, int numeroBloc, double recargo) {
		this.idPago = idPago;
		this.idSocio = idSocio;
		this.idMutualidad = idMutualidad;
		this.fecha = fecha;
		this.monto = monto;
		this.estado = estado;
		this.sorteo = sorteo;
		this.atraso = atraso;
		this.numeroBloc = numeroBloc;
		this.recargo= recargo;
	}
	
	public Pago( int idSocio, int idMutualidad, String fecha, double monto,
			String estado, int sorteo, int atraso,int numeroBloc, double recargo) {
		this.idSocio = idSocio;
		this.idMutualidad = idMutualidad;
		this.fecha = fecha;
		this.monto = monto;
		this.estado = estado;
		this.sorteo = sorteo;
		this.atraso = atraso;
		this.numeroBloc = numeroBloc;
		this.recargo= recargo;
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
	
	public String getFecha() {
		return fecha;
	}
	
	public void setFecha(String fecha) {
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
	
	public int getIdPago() {
		return idPago;
	}

	public void setIdPago(int idPago) {
		this.idPago = idPago;
	}

	public int getNumeroBloc() {
		return numeroBloc;
	}

	public void setNumeroBloc(int numeroBloc) {
		this.numeroBloc = numeroBloc;
	}

	public double getRecargo() {
		return recargo;
	}

	public void setRecargo(double recargo) {
		this.recargo = recargo;
	}
	
}
