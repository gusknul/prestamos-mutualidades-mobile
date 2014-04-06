package com.prestamosMutualidades.beans;


public class Cobro {
	
	private int idCobro;
	private int idSocio;
	private int idMutualidad;
	private String fecha;
	private double monto;
	private String estado;
	private int folio;
	private double recargo;
	private int atraso;
	private int sorteo;
	private int adelanto;
	private boolean aplicaAtrasosRecargos; 
	
	public Cobro(){
		
	}
	
	public Cobro(int idCobro, int idSocio, int idMutualidad, String date,
			double monto, String estado, int folio, double recargo,int sorteo, int atraso) {
		this.idCobro = idCobro;
		this.idSocio = idSocio;
		this.idMutualidad = idMutualidad;
		this.fecha = date;
		this.monto = monto;
		this.estado = estado;
		this.folio = folio;
		this.recargo = recargo;
		this.sorteo = sorteo;
		this.atraso = atraso;
		
	}
	
	public Cobro(int idCobro, int idSocio, int idMutualidad, String date,
			double monto, String estado, int folio, double recargo,int sorteo, int atraso , int adelanto, boolean aplicaAtrasosRecargos) {
		this.idCobro = idCobro;
		this.idSocio = idSocio;
		this.idMutualidad = idMutualidad;
		this.fecha = date;
		this.monto = monto;
		this.estado = estado;
		this.folio = folio;
		this.recargo = recargo;
		this.sorteo = sorteo;
		this.atraso = atraso;
		this.adelanto = adelanto;
		this.setAplicaAtrasosRecargos(aplicaAtrasosRecargos);
	}
	
	public Cobro(int idSocio, int idMutualidad, String date,
			double monto, String estado, int folio, double recargo,int sorteo, int atraso) {
		this.idSocio = idSocio;
		this.idMutualidad = idMutualidad;
		this.fecha = date;
		this.monto = monto;
		this.estado = estado;
		this.folio = folio;
		this.recargo = recargo;
		this.sorteo = sorteo;
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

	public int getSorteo() {
		return sorteo;
	}

	public void setSorteo(int sorteo) {
		this.sorteo = sorteo;
	}
	
	public int getAdelanto() {
		return adelanto;
	}

	public void setAdelanto(int adelanto) {
		this.adelanto = adelanto;
	}
	
	
	public boolean isAplicaAtrasosRecargos() {
		return aplicaAtrasosRecargos;
	}

	public void setAplicaAtrasosRecargos(boolean aplicaAtrasosRecargos) {
		this.aplicaAtrasosRecargos = aplicaAtrasosRecargos;
	}

	@Override
	public String toString() {
		return    idCobro + "\n"
				+ idSocio + "\n"
				+ idMutualidad + "\n"
				+ fecha + "\n"
				+ monto + "\n"
				+ estado + "\n"
				+ folio + "\n"
				+ recargo + "\n"
				+ atraso + "\n"
				+ sorteo;
	}

}
