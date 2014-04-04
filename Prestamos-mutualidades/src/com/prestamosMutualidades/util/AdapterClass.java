package com.prestamosMutualidades.util;

import java.util.ArrayList;

import com.prestamosMutualidades.beans.Socio;

import android.app.Application;
import android.content.Context;
import android.util.SparseArray;

public class AdapterClass extends Application{
	
	private AdapterDAO adapter;
	private BaseDatos baseDatos;
	private Context context;
	private ArrayList<Object> datos;
	private SparseArray<Socio> socios;
	
	public AdapterClass(Context context){
		this.context = context;
	}
	
	public AdapterClass(){
	}
	
	public void abrirConexionSinRed(){
		adapter.abrirConexion();
	}
	
	public void abrirConexion(){
		adapter.cargarDatos(datos);
		adapter.abrirConexion();
	}
	
	public AdapterDAO getAdapter() {
		return adapter;
	}

	public void setAdapter(AdapterDAO adapter) {
		this.adapter = adapter;
	}

	public BaseDatos getBaseDatos() {
		return baseDatos;
	}

	public void setBaseDatos(BaseDatos baseDatos) {
		this.baseDatos = baseDatos;
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}
	
	public ArrayList<Object> getDatos(){
		return datos;
	}
	
	public void setDatos(ArrayList<Object> datos){
		this.datos = datos;
	}

	public SparseArray<Socio> getSocios() {
		return socios;
	}

	public void setSocios(SparseArray<Socio> socios) {
		this.socios = socios;
	}
	
}